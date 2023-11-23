public class Esercizio6 {
    public static boolean scan(String s) {
        int state = 0;
        int i = 0;
        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);

            switch (state) {
                case 0:
                    if ((ch >= '0' && ch <= '9') && ch % 2 == 0)
                        state = 2;
                    else if ((ch >= '0' && ch <= '9') && ch % 2 == 1)
                        state = 1;
                    else state = -1;
                    break;

                case 1:
                    if ((ch >= '0' && ch <= '9') && ch % 2 == 0)
                        state = 3;
                    else if ((ch >= '0' && ch <= '9') && ch % 2 == 1)
                        state = 5;
                    else state = -1;
                    break;

                case 2:
                    if ((ch >= '0' && ch <= '9') && ch % 2 == 0)
                        state = 6;
                    else if ((ch >= '0' && ch <= '9') && ch % 2 == 1)
                        state = 4;
                    else state = -1;
                    break;

                case 3:
                    if ((ch >= '0' && ch <= '9') && ch % 2 == 0)
                        state = 6;
                    else if ((ch >= '0' && ch <= '9') && ch % 2 == 1)
                        state = 4;
                    else if (ch >= 'L' && ch <= 'Z')
                        state = 7;
                    else state = -1;
                    break;

                case 4:
                    if ((ch >= '0' && ch <= '9') && ch % 2 == 0)
                        state = 3;
                    else if ((ch >= '0' && ch <= '9') && ch % 2 == 1)
                        state = 5;
                    else if (ch >= 'A' && ch <= 'K')
                        state = 7;
                    else state = -1;
                    break;

                case 5:
                    if ((ch >= '0' && ch <= '9') && ch % 2 == 0)
                        state = 3;
                    else if ((ch >= '0' && ch <= '9') && ch % 2 == 1)
                        state = 5;
                    else if (ch >= 'L' && ch <= 'Z')
                        state = 7;
                    else state = -1;
                    break;

                case 6:
                    if ((ch >= '0' && ch <= '9') && ch % 2 == 0)
                        state = 6;
                    else if ((ch >= '0' && ch <= '9') && ch % 2 == 1)
                        state = 4;
                    else if (ch >= 'A' && ch <= 'K')
                        state = 7;
                    else state = -1;
                    break;

                case 7:
                    if (ch >= 'a' && ch <= 'z')
                        state = 7;
                    else state = -1;
                    break;
            }
        }
        return state == 7;
    }

    public static void main(String[] args) {
        System.out.println(scan("22B") ? "OK" : "NOPE");
        System.out.println(scan("22Bianchi") ? "OK" : "NOPE");
        System.out.println(scan("122B") ? "OK" : "NOPE");
        System.out.println(scan("122Bianchi") ? "OK" : "NOPE");
        System.out.println(scan("123456Rossi") ? "OK" : "NOPE");
        System.out.println(scan("123456Verdi") ? "OK" : "NOPE");
        System.out.println(scan("221B") ? "OK" : "NOPE");
        System.out.println(scan("3210Verdi") ? "OK" : "NOPE");
        System.out.println(scan("123456Manzoni") ? "OK" : "NOPE");
        System.out.println(scan("654321Bianchi") ? "OK" : "NOPE");
        System.out.println(scan("123456Bianchi") ? "OK" : "NOPE");
        System.out.println(scan("654321Rossi") ? "OK" : "NOPE");
        System.out.println(scan("Rossi") ? "OK" : "NOPE");
        System.out.println(scan("5R") ? "OK" : "NOPE");
        System.out.println(scan("654322") ? "OK" : "NOPE");
        System.out.println(scan("2Bianchi") ? "OK" : "NOPE");
    }
}
