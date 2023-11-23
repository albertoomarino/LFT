import java.io.*;
import java.util.*;

public class Lexer {

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
        while (peek == ' ' || peek == '\t' || peek == '\n' || peek == '\r') {
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

            case '/':
                peek = ' ';
                return Token.div;

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
                            + " after & : " + peek);
                    return null;
                }

            case '|':
                readch(br);
                if (peek == '|') {
                    peek = ' ';
                    return Word.or;
                } else {
                    System.err.println("Erroneous character"
                            + " after | : " + peek);
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
                } else if (peek == ' ' || Character.isDigit(peek) || Character.isLetter(peek)) {
                    peek = ' ';
                    return Word.lt;
                } else {
                    System.err.println("Erroneous character"
                            + " after < : " + peek);
                    return null;
                }

            case '>':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.ge;
                } else if (peek == ' ' || Character.isDigit(peek) || Character.isLetter(peek)) {
                    peek = ' ';
                    return Word.gt;
                } else {
                    System.err.println("Erroneous character"
                            + " after > : " + peek);
                    return null;
                }

            case '=':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.eq;
                } else {
                    System.err.println("Erroneous character"
                            + " after = : " + peek);
                    return null;
                }

            case (char) -1:
                return new Token(Tag.EOF);

            default:

                if (Character.isLetter(peek)) {

                    String s = "";
                    while (Character.isLetter(peek) || Character.isDigit(peek)) {
                        s = s + peek;
                        readch(br);
                    }

                    switch (s) {

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

                        default:

                            return new Word(Tag.ID, s);
                    }

                } else if (Character.isDigit(peek)) {

                    String s = "";
                    while (Character.isDigit(peek)) {
                        s = s + peek;
                        readch(br);
                    }

                    if (Character.isLetter(peek)) {
                        System.out.println("Erroneous ID : " + "<" + s + peek + ">");
                        return null;
                    } else {

                        int length = s.length() - 1;
                        int i = 0;
                        int value = 0;

                        while (length >= 0) {
                            final char ch = s.charAt(i++);
                            int p = ch - '0';
                            value = value + (p * ((int) Math.pow(10, length)));
                            length--;
                        }

                        return new NumberTok(Tag.NUM, value);
                    }

                } else {
                    System.err.println("Erroneous character: " + peek);
                    return null;
                }
        }
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "Y_test_pos.lft";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Token tok;
            do {
                tok = lex.lexical_scan(br);
                System.out.println("Scan: " + tok);
            } while (tok.tag != Tag.EOF);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
