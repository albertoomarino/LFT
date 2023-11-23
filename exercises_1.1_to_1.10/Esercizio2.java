public class Esercizio2 {
    public static boolean scan(String s) {
        int state = 0;
        int i = 0;
        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);

            switch (state) {
                case 0:
                    if (ch == '_')
                        state = 1;
                    else if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))
                        state = 2;
                    else state = -1;
                    break;

                case 1:
                    if ((ch >= '0' && ch <= '9') || ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')))
                        state = 2;
                    else if (ch == '_')
                        state = 1;
                    else state = -1;
                    break;

                case 2:
                    if ((ch >= '0' && ch <= '9') || ch == '_' || ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')))
                        state = 2;
                    else state = -1;
                    break;
            }
        }
        return state == 2;
    }

    public static void main(String[] args) {
        System.out.println(scan("x") ? "OK" : "NOPE");
        System.out.println(scan("flag") ? "OK" : "NOPE");
        System.out.println(scan("flag1") ? "OK" : "NOPE");
        System.out.println(scan("_flag1") ? "OK" : "NOPE");
        System.out.println(scan("x2y2") ? "OK" : "NOPE");
        System.out.println(scan("x_1") ? "OK" : "NOPE");
        System.out.println(scan("lft_lab") ? "OK" : "NOPE");
        System.out.println(scan("x_1_y_2") ? "OK" : "NOPE");
        System.out.println(scan("x___") ? "OK" : "NOPE");
        System.out.println(scan("___5") ? "OK" : "NOPE");
        System.out.println(scan("5") ? "OK" : "NOPE");
        System.out.println(scan("221B") ? "OK" : "NOPE");
        System.out.println(scan("123") ? "OK" : "NOPE");
        System.out.println(scan("9_to_5") ? "OK" : "NOPE");
        System.out.println(scan("___") ? "OK" : "NOPE");
        System.out.println(scan("") ? "OK" : "NOPE");
    }
}
