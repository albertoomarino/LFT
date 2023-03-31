//Esercizi 2.2 - 2.3

import java.io.*;
import java.util.*;

public class LexerDue {

    public static int line = 1;
    private char peek = ' ';
    
    private void readch(BufferedReader br) {
        try {
            peek = (char) br.read();
        } catch (IOException exc) {
            peek = (char) -1;
        }
    }

    public Token lexical_scan(BufferedReader br) {
        while (peek == ' ' || peek == '\t' || peek == '\n'  || peek == '\r' || peek == '/') {
			
			// gestisco i commenti in questo while poichè devono essere ignorati dal programma per l'analisi lessicale
			  
			if(peek == '/') {
				readch(br);
				if(peek == '/') {
					
					/*
					 * commenti su una riga:
					 * -) ignoro tutti i caratteri successivi fino a quando non trovo un 'a capo'
					 * -) se trovo EOF l'analisi lessicale termina
					*/
					
					while(peek != '\n' && peek != (char)-1) {
						readch(br);
					}
				} else if (peek == '*') {
					
						// commenti su una o più righe: utilizzo l'automa 1.9
						
						int state = 2; // parto dallo stato 2 perchè ho già riconosciuto /*
						int op_com = line; // linea a cui apro un commento multi-linea
						
						
						while (state != 4) { // se state == 4 ho riconosciuto la sequenza di fine commento */
							readch(br);
							
							if(peek == '\n') line++; // se trovo un 'a capo' aggiorno line
							
							switch(state) {
								
								case 2:
									if(peek == '*')
										state = 3;
									else if (peek == (char)-1) { // se il programma finisce lasciando aperto un commento segnalo errore
										System.out.println("Missing closing comment open on line: " + op_com);
										return null;
									}
									else state = 2; // nel commento posso leggere qualunque carattere
									break;
									
								case 3:
									if(peek == '/')
										state = 4;
									else if (peek == '*')
										state = 3;
									else if (peek == (char)-1) {
										System.out.println("Missing closing comment open on line: " + op_com);
										return null;
									}
									else state = 2;
									break;
									
							}
						}
						peek = ' '; // rientro nel while per continuare
					}
					else {
					return Token.div; // se non ho riconosciuto un commento, restituisco il Token della divisione
				}
			}
            if (peek == '\n') line++;
            readch(br);
        }
        
        switch (peek) {
            case '!':
                peek = ' ';
                return Token.not;
				
			case '(':
                peek = ' ';
                return Token.lpt;
				
			case ')':
                peek = ' ';
                return Token.rpt;
				
			case '{':
                peek = ' ';
				return Token.lpg;
				
			case '}':
                peek = ' ';
                return Token.rpg;
				
			case '+':
                peek = ' ';
                return Token.plus;
				
			case '-':
                peek = ' ';
                return Token.minus;
				
			case '*':
                peek = ' ';
                return Token.mult;
				
			case ';':
                peek = ' ';
                return Token.semicolon;
				
			case ',':
                peek = ' ';
                return Token.comma;
	
            case '&':
                readch(br);
                if (peek == '&') {
                    peek = ' ';
                    return Word.and;
                } else {
                    System.err.println("Erroneous character"
                            + " after & : "  + peek );
                    return null;
                }
				
			case '|':
                readch(br);
                if (peek == '|') {
                    peek = ' ';
                    return Word.or;
                } else {
                    System.err.println("Erroneous character"
                            + " after | : "  + peek );
                    return null;
                }
				
			case '<':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.le;
                } else if (peek == '>') {
					peek = ' ';
					return Word.ne;
				} else 
					return Word.lt;
				
			case '>':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.ge;
                } else
					return Word.gt;
				
			case '=':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.eq;
                } else {
                    System.err.println("Erroneous character"
                            + " after = : "  + peek );
                    return null;
                }
          
            case (char)-1:
                return new Token(Tag.EOF);

            default:
			
				// identificatori e parole chiave
				
                if (Character.isLetter(peek) || peek == '_') { // un identificatore inizia con una lettera o con _
					
					String s = "";
					while(Character.isLetter(peek) || Character.isDigit(peek) || peek == '_') {
						s = s + peek; //costruisco la stringa
						readch(br);
					}
					
						switch(s) { // se la stringa è diversa dalle parole chiave allora potrebbe essere un identificatore
							
							case "assign":
								return Word.assign;
							
							case "to":
								return Word.to;
								
							case "if":
								return Word.iftok;
								
							case "else":
								return Word.elsetok;
								
							case "while":
								return Word.whiletok;
								
							case "begin":
								return Word.begin;
								
							case "end":
								return Word.end;
								
							case "read":
								return Word.read;
								
							case "print":
								return Word.print;
						}
						
						// uso l'automa 1.2 per controllare che l'identificatore non sia composto da soli _
						
						int state = 0;
						int i = 0;
						
						while(state >= 0 && i < s.length()) {
							
							final char ch = s.charAt(i++);
							
							switch(state) {
								
								case 0:
								if(ch == '_')
									state = 1;
								else if((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))
									state = 2;
								else state = -1;
								break;
								
								case 1:
								if((ch >= '0' && ch <= '9') || ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')))
									state = 2;
								else if(ch == '_')
									state = 1;
								else state = -1;
								break;
								
								case 2:
								if((ch >= '0' && ch <= '9') || ch == '_' || ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')))
									state = 2;
								else state = -1;
								break;
								
							}
						}
						
						if(state == 2)
							return new Word(Tag.ID,s);
					
						else {
							System.out.println("Erroneous ID : " + s);
							return null;
						}

                } else if (Character.isDigit(peek)) {

					// numeri
					
					String s = "";
					while(Character.isDigit(peek)) {
						s = s + peek; // costruisco il numero come stringa
						readch(br);
					}
					
					/* 
					 * un identificatore non comincia con un numero, quindi se dopo un numero 
					 * trovo una lettera segnalo errore 
					 */
					
					if(Character.isLetter(peek)) {
						System.out.println("Erroneous ID : " + "<" + s + peek + ">");
						return null;
					}
					else {
						
						// costruisco l'intero (attributo value di Tag.NUM) corrispondente alla stringa
						
						int length = s.length() -1;
						int i = 0;
						int value = 0;
						
						while(length >= 0) {
							final char ch = s.charAt(i++);  // metodo charAt(int indice) ritorna il carattere all’indice specificato in una stringa
							int p = ch - '0';	// conversione char - intero
							value = value + (p*((int)Math.pow(10,length))); // cast a int poichè restituisce un double altrimenti
							length--;
						}
						
						return new NumberTok(Tag.NUM,value);
					}

                } 
				
				else {
                        System.err.println("Erroneous character: " + peek );
                        return null;
                }
         }
    }
		
    public static void main(String[] args) {
        LexerDue lex = new LexerDue();
        String path = "test.lft"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Token tok;
            do {
                tok = lex.lexical_scan(br);
                System.out.println("Scan: " + tok);
            } while (tok.tag != Tag.EOF);
            br.close();
        } catch (IOException e) {e.printStackTrace();}    
    }

}
