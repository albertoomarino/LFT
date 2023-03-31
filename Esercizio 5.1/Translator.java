import java.io.*;

public class Translator {
	private LexerDue lex;
	private BufferedReader pbr;
	private Token look;
	
	SymbolTable st = new SymbolTable();
	CodeGenerator code = new CodeGenerator();
	int count = 0;
	
	public Translator(LexerDue l, BufferedReader br) {
		lex = l;
		pbr = br;
		move();
	}
	
	void move() { 
		look = lex.lexical_scan(pbr);
		System.out.println("token = " + look);
	}
	
	void error(String s) { 
		throw new Error("near line " + lex.line + ": " + s);
	}
	
	void match(int t) {
		if (look.tag == t) {
			if (look.tag != Tag.EOF) move();
		} else error("syntax error");
	}
	
	public void prog() {	//P -> S' EOF
		switch (look.tag) {
	
			//GUIDA(P -> S' EOF) = { assign, print, read, while, if { }
			case Tag.ASSIGN:
			case Tag.PRINT:
			case Tag.READ:
			case Tag.WHILE:
			case Tag.IF:
			case '{':
			int lnext_prog = code.newLabel();
			statlist(lnext_prog);
			code.emitLabel(lnext_prog);
			match(Tag.EOF);
			try {
				code.toJasmin();
			}
			catch(java.io.IOException e) {
				System.out.println("IO error\n");
			};
			break;
			
			default:
			error("Errore in prog");
		}
	}
	
	private void statlist(int lnext_prog) {	//S' -> S S''
		switch (look.tag) {
	
			//GUIDA(S' -> S S'') = { assign, print, read, while, if { }
			case Tag.ASSIGN:
			case Tag.PRINT:
			case Tag.READ:
			case Tag.WHILE:
			case Tag.IF:
			case '{':
			stat(lnext_prog); // qui genera etichette da L1 in poi se c'è il bisogno
			int lnext_statlist = code.newLabel(); // qui prendo la successiva
			statlistp(lnext_statlist); // uso la successiva se statlistp!=NULL altrimenti ricorsivamente emetto L0
			break;
	
			default:
			error("Errore in statlist");
		}
	}
	
	private void statlistp(int lnext_ereditata) {	//S'' -> ; S S'' | eps
		switch (look.tag) { 
	
			//GUIDA(S'' -> S S'') = { ; }
			case ';':
			match(';');
			// ripeto la stessa cosa di statlist
			stat(lnext_ereditata);
			int lnext_statlistp = code.newLabel(); 
			statlistp(lnext_statlistp);
			break;
	
			//GUIDA(S'' -> eps)	= { }, EOF }
			case '}':
			case Tag.EOF:
			break;
			
			default:
			error("Errore in statlistp");
		}
	}
	
	/*
	 * S -> assign E to I
	 *	  | print (E')
	 *	  | read (I)
	 *	  | while (B) S
	 *	  | if (B) S V		
	 *	  | { S' }
	 *
	 * Rispetto alla grammatica originale proposta nell'esercizio 3.2, le due produzioni
	 * che iniziano con "if" sono state abbiamo fattorizzate in modo da eliminare ambiguità
	 */
	
	private void stat(int lnext_ered_stat) {
		switch (look.tag) {
	
			//GUIDA(S -> assign E to I) = { assign }
			case Tag.ASSIGN:
			match(Tag.ASSIGN);
			expr(); // può essere anche solo un numero
			match(Tag.TO);
			idlist(0);
			break;
	
			//GUIDA(S -> print (E')) = { print }
			case Tag.PRINT:
			// non emetto invokestatic perchè viene emessa alla fine
			match(Tag.PRINT);
			match('(');
			// exprlist gestisce il caso di una somma o moltiplicazione mentre expr quello di una sottrazione o divisione
			exprlist('p'); //p per indicare print
			code.emit(OpCode.invokestatic,1);
			match(')');
			break;
	
			//GUIDA(S -> read (I)) = { read }
			case Tag.READ:
			code.emit(OpCode.invokestatic,0); // la read viene stampata all'inizio a differenza della print
			match(Tag.READ);
			match('(');
			idlist(1); 
			match(')');
			break;
	
			//GUIDA(S -> while (B) S) = { while }
			case Tag.WHILE:
			int begin_while = code.newLabel();
			match(Tag.WHILE);
			match('(');
			int btrue = code.newLabel(); 
			int bfalse = lnext_ered_stat; 
			code.emitLabel(begin_while); // etichetta a cui verifico la condizione del while
			bexpr(btrue,bfalse);
			code.emitLabel(btrue); // etichetta a cui inizio a eseguire il corpo del while
			match(')');
			int nuova1 = code.newLabel();
			stat(nuova1);
			code.emit(OpCode.GOto,begin_while); // ritorno a verificare la condizione
			code.emitLabel(bfalse); // continuo con il codice
			break;
			
			//GUIDA(if (B) S V) = { if }
			case Tag.IF:
			match(Tag.IF);
			match('(');
			int case_if_true = code.newLabel();
			int case_if_false = code.newLabel();
			bexpr(case_if_true,case_if_false); // stampa: if_icmpR Ltrue; goto Lfalse
			match(')');
			code.emitLabel(case_if_true); // sono in Ltrue
			int nuova2 = code.newLabel();
			stat(nuova2);
			var(case_if_false); // eseguo else
			break;
	
			//GUIDA(S -> {S}) = { { }
			case '{':
			match('{');
			statlist(lnext_ered_stat);
			match('}');
			break;
	
			default:
			error("Errore in stat");
		}
	}
	
	private void var(int lnext_ered_var) {	//V -> end | else S end
		switch(look.tag){
	
			//GUIDA(V -> end) = { end }
			case Tag.END:
			match(Tag.END);
			code.emitLabel(lnext_ered_var);
			break;
	
			//GUIDA(V -> else S end) = { else }
			case Tag.ELSE:
			int to_go = code.newLabel(); // etichetta dopo else
			code.emit(OpCode.GOto,to_go); // dico all'if di saltare all'etichetta dopo else
			code.emitLabel(lnext_ered_var); // sono nell'else
			match(Tag.ELSE);
			int nuova3 = code.newLabel();
			stat(nuova3);
			match(Tag.END);
			code.emitLabel(to_go); //stampo l'etichetta dopo else: continuazione del codice
			break;
	
			default:
			error("Errore in var");
		}
	}
	
	private void idlist(int ered_idlist) {	//I -> ID I'
		// ered_idlist = attributo ereditato: 1 per read, 0 per assign
	
		switch (look.tag) {
	
			//GUIDA(I -> ID I') = { ID }
			case Tag.ID:
			int id_addr = st.lookupAddress(((Word)look).lexeme);
			if (id_addr == -1) {
				// se .lookupAddress ritorna -1 allora non c'è l'elemento quindi lo aggiungo con referenza count
				id_addr = count;
				st.insert(((Word)look).lexeme,count++); // aumento count++ perchè se non c'è lo inserisco
			}
			if (ered_idlist == 1) { // caso della read
				match(Tag.ID);
				code.emit(OpCode.istore, id_addr); 
				idlistp(ered_idlist);    // richiamo idlistp con ered_idlist 
			} else { // caso di assign
				match(Tag.ID);
				idlistp(ered_idlist);    // richiamo idlistp con ered_idlist 
				code.emit(OpCode.istore, id_addr);
			}
			break;
			
			default:
			error("Errore in idlist");
		}
	}
	
	private void idlistp(int lnext_ered) {	//I' -> , ID I' | eps
		switch(look.tag){
	
			//GUIDA(I' -> , ID I') = { , }
			case ',':
			if (lnext_ered == 1) 
				code.emit(OpCode.invokestatic,0);		// caso della read
			else 
				code.emit(OpCode.dup);		// caso dell'assign
			match(',');
			int id_addr = st.lookupAddress(((Word)look).lexeme);
			if (id_addr == -1) {
				//se .lookupAddress ritorna -1 allora non c'è l'elemento quindi lo aggiungo con referenza count
				id_addr = count;
				st.insert(((Word)look).lexeme,count++);	//aumento count++ perchè se non c'è lo inserisco
			}
			code.emit(OpCode.istore, id_addr); 
			match(Tag.ID);
			idlistp(lnext_ered);                                  
			break;
	
			//GUIDA(I' -> eps) = { end, else, ), }, ;, EOF}
			case ')':
			case ';':
			case Tag.END:
			case Tag.ELSE:
			case '}':
			case Tag.EOF:
			break;
	
			default:
			error("Errore in idlistp");
		}
	}
	
	private void bexpr(int ltrue,int lfalse) {	//B -> RELOP E E
		
		OpCode opCode = OpCode.temp;	// opCode temporaneo per salvare l'opCode del RELOP
		/*
		 * l'idea è quella di sfruttare il fatto di invertire l'operatore così se la condizione è vera 
		 * continua sotto il processo altrimenti salta all'uscita evitando così un goto
		 */
		switch(((Word) look).lexeme) {
	
			//GUIDA(B -> RELOP E E) = { RELOP }
	
			case "==":
			opCode = OpCode.if_icmpeq;
			break;
			
			case "<":
			opCode = OpCode.if_icmplt;
			break;
			
			case "<=":
			opCode = OpCode.if_icmple;
			break;
			
			case "<>":
			opCode = OpCode.if_icmpne;
			break;
			
			case ">":
			opCode = OpCode.if_icmpgt;
			break;
			
			case ">=":
			opCode = OpCode.if_icmpge;
			break;
	
			default:
			error("Error in bexpr method");
		}
		match(Tag.RELOP);
		expr();
		expr();
		code.emit(opCode, ltrue);
		code.emit(OpCode.GOto, lfalse);
	}
	
	private void expr() {	//E -> + (E') | - E E | * (E') | / E E | NUM | ID
	
		// se + richiamo exprlist con +, analogamente con *
	
		switch(look.tag){
	
			//GUIDA(E -> + (E')) = { + }
			case '+':
			match('+');
			match('(');
			exprlist('+');
			match(')');
			break;
	
			//GUIDA(E -> - E E) = { - }
			case '-':
			match('-');
			expr();
			expr();
			code.emit(OpCode.isub);
			break;
	
			//GUIDA(E -> * (E')) = { * }
			case '*':
			match('*');
			match('(');
			exprlist('*');
			match(')');
			// le emit di + e * vengono gestite in exprlistp
			break;
	
			//GUIDA(E -> / E E) = { / }
			case '/':
			match('/');
			expr();
			expr();
			code.emit(OpCode.idiv);
			break;
	
			//GUIDA(E -> NUM) = { NUM }
			case Tag.NUM:
			int numb = ((NumberTok)look).value;
			match(Tag.NUM);
			code.emit(OpCode.ldc,numb);
			break;
	
			//GUIDA(E -> ID) = { ID }
			case Tag.ID:
			int id_addr = st.lookupAddress(((Word)look).lexeme);
			if (id_addr == -1) {
				/*
				 * Arrivati qui non ci può essere una variabile che non corrisponde a nessun 
				 * indirizzo e che quindi è salvata quindi se == -1 c'è un errore
				 */
				throw new IllegalArgumentException("No memory variable matches this ID");
			}
			code.emit(OpCode.iload,id_addr); 
			match(Tag.ID); // lo consumo dopo averne trovato l'indirizzo in memoria
			break;
	
			default:
			error("Errore in expr");
		}
	}
	
	private void exprlist(char to_do) {	//E' -> E E''
		switch (look.tag) {
	
			//GUIDA(E' -> E E'') = { +, -, *, /, NUM, ID }
			case '+':
			case '-':
			case '*':
			case '/':
			case Tag.NUM:
			case Tag.ID:
			expr();
			exprlistp(to_do);
			break;
	
			default:
			error("Errore in exprlist");
		}
	}
	
	private void exprlistp(char to_do) {	//E'' -> , E E'' | eps
		switch(look.tag){
	
			//GUIDA(E'' -> , E E'') = { , }
			case ',':
			if(to_do == 'p'){
				code.emit(OpCode.invokestatic, 1);
			} // lo devo mettere in cima nel caso
			match(',');
			expr();
			exprlistp(to_do);
			if (to_do == '+') {
				code.emit(OpCode.iadd);
			} else if(to_do == '*') {
				code.emit(OpCode.imul);
			} else {
				break;
			}
			break;
	
			//GUIDA(E'' -> eps) = { ) }	
			case ')':
			break;
	
			default:
			error("Errore in exprlistp");
		}
	}
	
	public static void main(String[] args) {
		LexerDue lex = new LexerDue();
		String path = "test.lft"; // il percorso del file da leggere
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			Translator traduttore = new Translator(lex, br);
			traduttore.prog();
			System.out.println("Translation success!");
			br.close();
		} catch (IOException e) {e.printStackTrace();}
	}
}
