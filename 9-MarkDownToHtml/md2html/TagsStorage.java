package md2html;

public class TagsStorage { 
    private static String[] tags = {
        "--",
        "__",
        "**",
        "_",
        "*",
        "`"
    };
    private static int maxTagLength;

    static {
        maxTagLength = tags.length > 0 ? tags[0].length() : 0;
    }

    public static Tag get(String s, int start) {
        for (int i = 0; i < tags.length; i++) {
            if (s.startsWith(tags[i], start)) {
                return Tag.values()[i];
            }
        }

        return null;
    }

    public static int maxTagLength() {
        return maxTagLength;
    }

    public static int amountOfTags() {
        return tags.length;
    }

    public static boolean isPartOfTag(char ch) {
        return ch == '*' || ch == '_' || ch == '`' || ch == '-';
    }

    public static boolean isPartOfHeader(char ch) {
        return ch == '#';
    }   

    public static boolean isEndOfHeader(char ch) {
        return ch == ' ';
    }

    public static boolean isLineSep(char ch) {
        return ch == '\n';
    }

    public static boolean isShield(char ch) {
        return ch == '\\';
    }

}
