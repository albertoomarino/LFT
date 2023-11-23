public class Esercizio4 {
    public static boolean scan(String s) {
        int state = 0;
        int i = 0;
        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);

            switch (state) {
                case 0:
                    if (ch == ' ')
                        state = 0;
                    else if ((ch >= '0' && ch <= '9') && ch % 2 == 1)
                        state = 1;
                    else if ((ch >= '0' && ch <= '9') && ch % 2 == 0)
                        state = 2;
                    else state = -1;
                    break;

                case 1:
                    if (ch == ' ')
                        state = 3;
                    else if (ch >= 'L' && ch <= 'Z')
                        state = 5;
                    else if ((ch >= '0' && ch <= '9') && ch % 2 == 0)
                        state = 2;
                    else if ((ch >= '0' && ch <= '9') && ch % 2 == 1)
                        state = 1;
                    else state = -1;
                    break;

                case 2:
                    if (ch == ' ')
                        state = 4;
                    else if (ch >= 'A' && ch <= 'K')
                        state = 5;
                    else if ((ch >= '0' && ch <= '9') && ch % 2 == 0)
                        state = 2;
                    else if ((ch >= '0' && ch <= '9') && ch % 2 == 1)
                        state = 1;
                    else state = -1;
                    break;

                case 3:
                    if (ch == ' ')
                        state = 3;
                    else if (ch >= 'L' && ch <= 'Z')
                        state = 5;
                    else state = -1;
                    break;

                case 4:
                    if (ch == ' ')
                        state = 4;
                    else if (ch >= 'A' && ch <= 'K')
                        state = 5;
                    else state = -1;
                    break;

                case 5:
                    if (ch >= 'a' && ch <= 'z')
                        state = 5;
                    else if (ch == ' ')
                        state = 6;
                    else state = -1;
                    break;

                case 6:
                    if (ch >= 'A' && ch <= 'Z')
                        state = 5;
                    else if (ch == ' ')
                        state = 6;
                    else state = -1;
                    break;
            }
        }
        return state == 5 || state == 6;
    }

    public static void main(String[] args) {
        System.out.println(scan("2C") ? "OK" : "NOPE");
        System.out.println(scan(" 2Carducci ") ? "OK" : "NOPE");
        System.out.println(scan("   122C") ? "OK" : "NOPE");
        System.out.println(scan("122Alighieri   ") ? "OK" : "NOPE");
        System.out.println(scan("123456 G") ? "OK" : "NOPE");
        System.out.println(scan(" 1234 Da Vinci ") ? "OK" : "NOPE");
        System.out.println(scan(" 1L") ? "OK" : "NOPE");
        System.out.println(scan("123Manzoni  ") ? "OK" : "NOPE");
        System.out.println(scan("12345 Pascal") ? "OK" : "NOPE");
        System.out.println(scan("654 321Alighieri") ? "OK" : "NOPE");
        System.out.println(scan(" 123456Ro ssi") ? "OK" : "NOPE");
        System.out.println(scan("654322") ? "OK" : "NOPE");
        System.out.println(scan("Rossi") ? "OK" : "NOPE");
        System.out.println(scan("1ROSSI") ? "OK" : "NOPE");
        System.out.println(scan("1") ? "OK" : "NOPE");
        System.out.println(scan("F") ? "OK" : "NOPE");
        System.out.println(scan("") ? "OK" : "NOPE");
    }
}
