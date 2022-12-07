package sum;

public class SumOctal {
    public static void main(String[] args) {
        int sum = 0;
        for (String string : args) {
            int start = 0;
            for (int i = 0; i < string.length(); i++) {
                start = i;
                while (i != string.length() && !Character.isWhitespace(string.charAt(i))) {
                    i++;
                }
                // i - first space after number or end of str
                if (i != start) {
                    if (Character.toLowerCase(string.charAt(i - 1)) == 'o') {
                        sum += Integer.parseUnsignedInt(string.substring(start, i - 1), 8);
                    } else {
                        sum += Integer.parseInt(string.substring(start, i));
                    }
                }
            }
        }
        System.out.println(sum);
    }
}