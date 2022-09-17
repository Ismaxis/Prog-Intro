public class Sum {
    public static void main(String[] args) {
        int sum = 0;
        for (String string : args) {
            boolean isSpace = true;
            int start = 0;
            for (int i = 0; i < string.length(); i++) {
                char curChar = string.charAt(i);

                if (Character.isWhitespace(curChar)) {
                    if (!isSpace) { // first space after number
                        sum += Integer.parseInt(string.substring(start, i));
                    }
                    start = i;
                    isSpace = true;
                } else if (isSpace) { // if cur is not space but prev was
                    start = i;
                    isSpace = false;
                }
            }

            if (!isSpace) {
                sum += Integer.parseInt(string.substring(start, string.length()));
            }
        }
        System.out.println(sum);
    }
}