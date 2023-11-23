public class Esercizio7 {
    public static boolean scan(String s) {
        int state = 0;
        int i = 0;
        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);

            switch (state) {
                case 0:
                    if (ch == 'A')
                        state = 1;
                    else state = 8;
                    break;

                case 1:
                    if (ch == 'l')
                        state = 2;
                    else state = 9;
                    break;

                case 2:
                    if (ch == 'b')
                        state = 3;
                    else state = 10;
                    break;

                case 3:
                    if (ch == 'e')
                        state = 4;
                    else state = 11;
                    break;

                case 4:
                    if (ch == 'r')
                        state = 5;
                    else state = 12;
                    break;

                case 5:
                    if (ch == 't')
                        state = 6;
                    else state = 13;
                    break;

                case 6:
                    if (i == s.length())
                        state = 7;
                    else state = -1;
                    break;

                case 8:
                    if (ch == 'l')
                        state = 9;
                    else state = -1;
                    break;

                case 9:
                    if (ch == 'b')
                        state = 10;
                    else state = -1;
                    break;

                case 10:
                    if (ch == 'e')
                        state = 11;
                    else state = -1;
                    break;

                case 11:
                    if (ch == 'r')
                        state = 12;
                    else state = -1;
                    break;

                case 12:
                    if (ch == 't')
                        state = 13;
                    else state = -1;
                    break;

                case 13:
                    if (ch == 'o')
                        state = 7;
                    else state = -1;
                    break;
            }
        }
        return state == 7;
    }

    public static void main(String[] args) {
        System.out.println(scan("Alberto") ? "OK" : "NOPE");
        System.out.println(scan("Rlberto") ? "OK" : "NOPE");
        System.out.println(scan("Alb?rto") ? "OK" : "NOPE");
        System.out.println(scan("Alb!rto") ? "OK" : "NOPE");
        System.out.println(scan("Alber_o") ? "OK" : "NOPE");
        System.out.println(scan("Albert^") ? "OK" : "NOPE");
        System.out.println(scan("Albe~to") ? "OK" : "NOPE");
        System.out.println(scan("A@berto") ? "OK" : "NOPE");
        System.out.println(scan("Alber*o") ? "OK" : "NOPE");
        System.out.println(scan("Mlbnrto") ? "OK" : "NOPE");
        System.out.println(scan("A*b*r*o") ? "OK" : "NOPE");
        System.out.println(scan("Albberto") ? "OK" : "NOPE");
        System.out.println(scan("Albert") ? "OK" : "NOPE");
        System.out.println(scan("@lbert%") ? "OK" : "NOPE");
        System.out.println(scan("Alberto#!") ? "OK" : "NOPE");
        System.out.println(scan("_lb_rto") ? "OK" : "NOPE");
    }
}
