public class Esercizio5 {
    public static boolean scan(String s) {
        int state = 0;
        int i = 0;
        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);

            switch (state) {
                case 0:
                    if (ch >= 'A' && ch <= 'K')
                        state = 2;
                    else if (ch >= 'L' && ch <= 'Z')
                        state = 1;
                    else state = -1;
                    break;

                case 1:
                    if (ch >= 'a' && ch <= 'z')
                        state = 1;
                    else if ((ch >= '0' && ch <= '9') && ch % 2 == 0)
                        state = 3;
                    else if ((ch >= '0' && ch <= '9') && ch % 2 == 1)
                        state = 5;
                    else state = -1;
                    break;

                case 2:
                    if (ch >= 'a' && ch <= 'z')
                        state = 2;
                    else if ((ch >= '0' && ch <= '9') && ch % 2 == 0)
                        state = 6;
                    else if ((ch >= '0' && ch <= '9') && ch % 2 == 1)
                        state = 4;
                    else state = -1;
                    break;

                case 3:
                    if ((ch >= '0' && ch <= '9') && ch % 2 == 0)
                        state = 3;
                    else if ((ch >= '0' && ch <= '9') && ch % 2 == 1)
                        state = 5;
                    else state = -1;
                    break;

                case 4:
                    if ((ch >= '0' && ch <= '9') && ch % 2 == 0)
                        state = 6;
                    else if ((ch >= '0' && ch <= '9') && ch % 2 == 1)
                        state = 4;
                    else state = -1;
                    break;

                case 5:
                    if ((ch >= '0' && ch <= '9') && ch % 2 == 0)
                        state = 3;
                    else if ((ch >= '0' && ch <= '9') && ch % 2 == 1)
                        state = 5;
                    else state = -1;
                    break;

                case 6:
                    if ((ch >= '0' && ch <= '9') && ch % 2 == 0)
                        state = 6;
                    else if ((ch >= '0' && ch <= '9') && ch % 2 == 1)
                        state = 4;
                    else state = -1;
                    break;
            }
        }
        return state == 5 || state == 6;
    }

    public static void main(String[] args) {
        System.out.println(scan("E2") ? "OK" : "NOPE");
        System.out.println(scan("Einaudi2") ? "OK" : "NOPE");
        System.out.println(scan("E122") ? "OK" : "NOPE");
        System.out.println(scan("Cooper122") ? "OK" : "NOPE");
        System.out.println(scan("G123456") ? "OK" : "NOPE");
        System.out.println(scan("Cooper1234") ? "OK" : "NOPE");
        System.out.println(scan("L1") ? "OK" : "NOPE");
        System.out.println(scan("Turing123") ? "OK" : "NOPE");
        System.out.println(scan("Leopardi12345") ? "OK" : "NOPE");
        System.out.println(scan("Bianchi654321") ? "OK" : "NOPE");
        System.out.println(scan("Rossi123456") ? "OK" : "NOPE");
        System.out.println(scan("654322") ? "OK" : "NOPE");
        System.out.println(scan("Rossi") ? "OK" : "NOPE");
        System.out.println(scan("1") ? "OK" : "NOPE");
        System.out.println(scan("F") ? "OK" : "NOPE");
        System.out.println(scan("") ? "OK" : "NOPE");
    }
}
