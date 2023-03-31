import java.io.*;

public class ParserDue {
    private LexerDue lex;
    private BufferedReader pbr;
    private Token look;

    public ParserDue(LexerDue l, BufferedReader br) {
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
		switch(look.tag) {
			
			//GUIDA(P -> S' EOF) = { assign, print, read, while, if { }
			case Tag.ASSIGN:
			case Tag.PRINT:
			case Tag.READ:
			case Tag.WHILE:
			case Tag.IF:
			case '{':
				statlist();
				match(Tag.EOF);
				break;
				
			default:
				error("Error in grammar prog");
		}
	}
	
	private void statlist() {	//S' -> S S''
		switch(look.tag) {
			
			//GUIDA(S' -> S S'') = { assign, print, read, while, if { }
			case Tag.ASSIGN:
			case Tag.PRINT:
			case Tag.READ:
			case Tag.WHILE:
			case Tag.IF:
			case '{':
				stat();
				statlistp();
				break;
				
			default:
				error("Error in grammar statlist");
		}
	}
	
	private void statlistp() {	//S'' -> ; S S'' | eps
		switch(look.tag) {
			
			//GUIDA(S'' -> S S'') = { ; }
			case ';':
				match(';');
				stat();
				statlistp();
				break;
				
			//GUIDA(S'' -> eps)	= { }, EOF }
			case '}':
			case Tag.EOF:
				break;
				
			default:
				error("Error in grammar statlistp");
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
	
	private void stat() {
		switch(look.tag) {
			
			//GUIDA(S -> assign E to I) = { assign }
			case Tag.ASSIGN:
				match(Tag.ASSIGN);
				expr();
				match(Tag.TO);
				idlist();
				break;
			
			//GUIDA(S -> print (E')) = { print }
			case Tag.PRINT:
				match(Tag.PRINT);
				match('(');
				exprlist();
				match(')');
				break;
			
			//GUIDA(S -> read (I)) = { read }
			case Tag.READ:
				match(Tag.READ);
				match('(');
				idlist();
				match(')');
				break;
			
			//GUIDA(S -> while (B) S) = { while }
			case Tag.WHILE:
				match(Tag.WHILE);
				match('(');
				bexpr();
				match(')');
				stat();
				break;
			
			//GUIDA(if (B) S V) = { if }
			case Tag.IF:
				match(Tag.IF);
				match('(');
				bexpr();
				match(')');
				stat();
				var();
				break;
			
			//GUIDA(S -> {S}) = { { }
			case '{':
				match('{');
				statlist();
				match('}');
				break;
			
			default:
				error("Error in grammar stat");
		}
	}
	
	private void var() {	//V -> end | else S end
		switch(look.tag) {
			
			//GUIDA(V -> end) = { end }
			case Tag.END:
				match(Tag.END);
				break;
			
			//GUIDA(V -> else S end) = { else }
			case Tag.ELSE:
				match(Tag.ELSE);
				stat();
				match(Tag.END);
				break;
				
			default:
				error("Error in grammar var");
		}
	}
	
	private void idlist() {	//I -> ID I'
		switch(look.tag) {
			
			//GUIDA(I -> ID I') = { ID }
			case Tag.ID:
				match(Tag.ID);
				idlistp();
				break;
				
			default:
				error("Error in grammar idlist");
		}
	}
	
	private void idlistp() {	//I' -> , ID I' | eps
		switch(look.tag) {
			
			//GUIDA(I' -> , ID I') = { , }
			case ',':
				match(',');
				match(Tag.ID);
				idlistp();
				break;
			
			//GUIDA(I' -> eps) = { end, else, ), }, ;, EOF}
			case Tag.END:
			case Tag.ELSE:
			case ')':
			case '}':
			case ';':
			case Tag.EOF:
				break;
				
			default:
				error("Error in grammar idlistp");
		}
	}
	
	private void bexpr() {	//B -> RELOP E E
		switch(look.tag) {
			
			//GUIDA(B -> RELOP E E) = { RELOP }
			case Tag.RELOP:
				match(Tag.RELOP);
				expr();
				expr();
				break;
				
			default:
				error("Error in grammar bexpr");
		}
	}
	
	private void expr() {	//E -> + (E') | - E E | * (E') | / E E | NUM | ID
		switch(look.tag) {
			
			//GUIDA(E -> + (E')) = { + }
			case '+':
				match('+');
				match('(');
				exprlist();
				match(')');
				break;
			
			//GUIDA(E -> - E E) = { - }
			case '-':
				match('-');
				expr();
				expr();
				break;
			
			//GUIDA(E -> * (E')) = { * }
			case '*':
				match('*');
				match('(');
				exprlist();
				match(')');
				break;
			
			//GUIDA(E -> / E E) = { / }
			case '/':
				match('/');
				expr();
				expr();
				break;
			
			//GUIDA(E -> NUM) = { NUM }
			case Tag.NUM:
				match(Tag.NUM);
				break;
			
			//GUIDA(E -> ID) = { ID }
			case Tag.ID:
				match(Tag.ID);
				break;
			
			default:
				error("Error in grammar expr");
		}
	}
	
	private void exprlist() {	//E' -> E E''
		switch(look.tag) {
			
			//GUIDA(E' -> E E'') = { +, -, *, /, NUM, ID }
			case('+'):
			case('-'):
			case('*'):
			case('/'):
			case(Tag.NUM):
			case(Tag.ID):
				expr();
				exprlistp();
				break;
				
			default:
				error("Error in grammar exprlist");
		}
	}
	
	private void exprlistp() {	//E'' -> , E E'' | eps
		switch(look.tag) {
			
			//GUIDA(E'' -> , E E'') = { , }
			case ',':
				match(',');
				expr();
				exprlistp();
				break;
			
			//GUIDA(E'' -> eps) = { ) }	
			case ')':
				break;
				
			default:
				error("Error in grammar exprlistp");
		}
	}
		
    public static void main(String[] args) {
        LexerDue lex = new LexerDue();
        String path = "esempio_semplice.lft"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            ParserDue parser = new ParserDue(lex, br);
            parser.prog();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}