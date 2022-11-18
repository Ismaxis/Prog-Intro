package md2html;

import java.util.Arrays;

public class TagsStorage {
    private static Tag[] tags;

    private static int maxTagLength;

    static {
        tags = Tag.getTextModTags();
        Arrays.sort(tags, (Tag a, Tag b) -> b.tagString.length() - a.tagString.length());

        maxTagLength = tags.length > 0 ? tags[0].tagString.length() : 0;
    }

    public static Tag getTagByString(String s, int start) {
        for (Tag tag : tags) {
            if (s.startsWith(tag.tagString, start)) {
                return tag;
            }
        }

        return null;
    }

    public static int maxTagLength() {
        return maxTagLength;
    }

    public static boolean isPartOfTag(char ch) {
        for (Tag tag : tags) {
            if (tag.tagString.charAt(0) == ch) {
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
