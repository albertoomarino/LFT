import java.io.*;
public class Valutatore {
    private LexerDue lex;
    private BufferedReader pbr;
    private Token look;

    public Valutatore(LexerDue l, BufferedReader br) {
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

    public void start() {	// S -> E EOF
		switch(look.tag){
			
			//GUIDA(S -> E EOF) = { (, NUM }
			case '(':
			case Tag.NUM:
				int S_v = expr();
				match(Tag.EOF);
				System.out.println(S_v);
				break;
			default:
				error("Errore nello start");
		}
    }

    private int expr() {	// E -> T E'
		switch(look.tag){
			
			//GUIDA(E -> T E') = { (, NUM }
			case '(':
			case Tag.NUM:{
				int T_val = term();
				int E_val = exprp(T_val);
				return E_val;
			}
			default:{
				error("Errore nell'expr");
				return -100;
			}
		}
    }

	// Er_val = valore ereditato
    private int exprp(int Er_val) {	// E' -> + T E' | - T E' | eps
	switch (look.tag) { //ritorno
	
		//GUIDA(E' -> + T E') = { + }
		case '+':{
			match('+');
			int T_val = term();
			int Ex_val = exprp(Er_val+T_val);
			return Ex_val;
		}
		
		//GUIDA(E' -> - T E') = { - }
		case '-':{
			match('-');
			int T_val = term();
			int Ex_val = exprp(Er_val-T_val);
			return Ex_val;
		}
			
		//GUIDA(E' -> eps) = { ), EOF}
		case Tag.EOF:
		case ')':{return Er_val;}
			
		default:{
			error("Errore in exprp");
			return -100;
		}	
	
		}
    }

    private int term() {	// T -> F T'
		// T - >FT' unica produzione
		switch(look.tag){
			
			//GUIDA(T -> F T') = { (, NUM }	
			case '(':
			case Tag.NUM:{
				int F_val = fact();
				int T_val = termp(F_val);
				return T_val;
			}
			
			default:{
				error("Errore nel term");
				return -100;
			}
		}
    }

    private int termp(int Te_val) {	// T' -> * F T' | / F T' | eps
       switch (look.tag) {
		   
		//GUIDA(T' -> * F T') = { * }
		case '*':{
			match('*');
			int F_val = fact();
			int T_val = termp(Te_val*F_val);
			return T_val;
		}
		
		//GUIDA(T' -> / F T') = { / }
		case '/':{
			match('/');
			int F_val = fact();
			int T_val = termp(Te_val/F_val);
			return T_val;
		}
			
		//GUIDA(T' -> eps) = { ), +, -, EOF }
		case Tag.EOF:
		case Tag.NUM:
		case ')':
		case '+':
		case '-':{
			return Te_val;
		}
		default:{
			error("Errore in termp");
		}
		return Te_val;
	
		}
    }

    private int fact() {	// F -> (E) | NUM
		switch(look.tag){
			
			//GUIDA(F -> (E)) = { ( }
			case '(':{
				match('(');
				int F_s = expr();
				match(')');
				return F_s;
			}
			
			//GUIDA(F -> NUM) = { NUM }	
			case Tag.NUM:{
				/*
				 * salvo l'intero prima di matchare il tag del numero perchè se lo mettessi dopo 
				 * non riconoscerebbe più il numero dato che dopo il match viene fatto avanzare il Lexer
				 */
				int i = ((NumberTok)look).value; // cast per poter accedere al valore del NumberTok relativo al numero riconosciuto
				match(Tag.NUM);
				return i;
			}
			
			default:
				error("Errore nel fact");
				return -100;
		}
        
    }
		
    public static void main(String[] args) {
        LexerDue lex = new LexerDue();
        String path = "Espressione_1.lft"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Valutatore valutatore = new Valutatore(lex, br);
            valutatore.start();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}