package md2html;

import java.io.*;
import md2html.tokens.*;

public class MdTokinizer {
    public static Token getNextToken(final String section, final int start) throws IOException {
        final char firstChar = section.charAt(start);
        if (TagsStorage.isPartOfHeader(firstChar)) {
            return parseHeaderToken(section, start);
        } else if (TagsStorage.isPartOfTag(firstChar)) {
            return parseTextModToken(section, start);
        } else {
            return parseTextToken(section, start);
        }
    }

    private static Token parseHeaderToken(final String section, final int start) throws IOException {
        char curChar = section.charAt(start + 1);
        int length = 1;
        while (TagsStorage.isPartOfHeader(curChar)) {
            length++;
            curChar = section.charAt(start + length);
        }
        if (!TagsStorage.isEndOfHeader(curChar)) {
            return new TextToken(section.substring(start, start + length));
        } else {
            length++; // last is space after #
            return new HeaderToken(length);
        }
    }

    private static Token parseTextModToken(final String section, final int start) {
        return Tag.getTextModToken(parseTextModType(section, start));
    }

    private static Token parseTextToken(final String section, final int start) {
        final int sectionLen = section.length();
        int i = start;
        boolean isShielded = false;
        for (; i < sectionLen; i++) {
            final char curChar = section.charAt(i);
            if (!isShielded && TagsStorage.isPartOfTag(curChar)) {
                if (parseTextModType(section, i) != null) {
                    break;
                }
            }
            isShielded = TagsStorage.isShield(curChar);
        }

        return new TextToken(section.substring(start, i));
    }

    private static Tag parseTextModType(final String section, final int start) {
        return TagsStorage.getTagByString(section, start);
    }
}
