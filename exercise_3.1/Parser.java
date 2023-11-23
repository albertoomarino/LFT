import java.io.*;

public class Parser {
    private LexerDue lex;
    private BufferedReader pbr;
    private Token look;

    public Parser(LexerDue l, BufferedReader br) {
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
	switch(look.tag) {
		
		//GUIDA(S -> E EOF) = { (, NUM }
		case '(':
		case Tag.NUM:
			expr();
			match(Tag.EOF);
			break;
			
		default:
			error("Error in grammar <start>");
	}
	}

    private void expr() {	// E -> T E'
	switch(look.tag) {
		
		//GUIDA(E -> T E') = { (, NUM }
		case '(':
		case Tag.NUM:
			term();
			exprp();
			break;
			
		default:
			error("Error in grammar <expr>");
	}
    }

    private void exprp() {	// E' -> + T E' | - T E' | eps
	switch(look.tag) {
		
		//GUIDA(E' -> + T E') = { + }
		case '+':
			match('+');
			term();
			exprp();
			break;
		
		//GUIDA(E' -> - T E') = { - }
		case '-':
			match('-');
			term();
			exprp();
			break;
		
		//GUIDA(E' -> eps) = { ), EOF}
		case ')':
		case Tag.EOF:
			break;
		
		default:
			error("Error in grammar <exprp>");
	}
    }

    private void term() {	// T -> F T'
	switch(look.tag) {
		
	//GUIDA(T -> F T') = { (, NUM }	
	case '(':
	case Tag.NUM:
		fact();
		termp();
		break;
		
	default:
		error("Error in grammar <term>");
	}	
    }

    private void termp() {	// T' -> * F T' | / F T' | eps
    switch(look.tag) {
		
		//GUIDA(T' -> * F T') = { * }
		case '*':
			match('*');
			fact();
			termp();
			break;
			
		//GUIDA(T' -> / F T') = { / }	
		case '/':
			match('/');
			fact();
			termp();
			break;
		
		//GUIDA(T' -> eps) = { ), +, -, EOF }	
		case ')':
		case '+':
		case '-':
		case Tag.EOF:
			break;
		
		default:
			error("Error in grammar <termp>");
	}
    }

    private void fact() {	// F -> (E) | NUM
    switch(look.tag) {
		
		//GUIDA(F -> (E)) = { ( }
		case '(':
			match('(');
			expr();
			match(')');
			break;
		
		//GUIDA(F -> NUM) = { NUM }	
		case Tag.NUM:
			match(Tag.NUM);
			break;
			
		default:
			error("Error in grammar <fact>");
	}
    }
		
    public static void main(String[] args) {
        LexerDue lex = new LexerDue();
        String path = "Espressione_1.lft"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Parser parser = new Parser(lex, br);
            parser.start();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}