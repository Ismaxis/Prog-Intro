package md2html;

public class TagsStorage {
    private static String[] tags;
    private static int maxTagLength;

    static {
        tags = Tag.getTagsStrings();

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

    public static boolean isPartOfTag(char ch) {
        for (int i = 0; i < tags.length; i++) {
            if (tags[i].charAt(0) == ch) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPartOfHeader(char ch) {
        return ch == Tag.HEADER.tagString.charAt(0);
    }

    public static boolean isEndOfHeader(char ch) {
        return ch == ' ';
    }

    public static boolean isShield(char ch) {
        return ch == '\\';
    }

}
