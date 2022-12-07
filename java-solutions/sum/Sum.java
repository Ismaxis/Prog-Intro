package sum;

public class Sum {
    public static void main(String[] args) {
        int sum = 0;
        for (String string : args) {
            string = string + " ";
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
        }
        System.out.println(sum);
    }
}