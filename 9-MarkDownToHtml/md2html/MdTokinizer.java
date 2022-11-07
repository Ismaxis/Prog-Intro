package md2html;

import java.io.*;
import md2html.tokens.*;

public class MdTokinizer {
    public static Token getNextToken(String section, int start) throws IOException {
        char firstChar = section.charAt(start);
        if (firstChar == '#') {
            return parseHeaderToken(section, start);
        } else if (isPartOfTag(firstChar)) {
            return parseTextModToken(section, start);
        } else if (firstChar == '\n') {
            return new EndOfLineToken();
        } else {
            return parseTextToken(section, start);
        }
    }

    private static Token parseHeaderToken(String section, int start) throws IOException {
        char curChar = section.charAt(start + 1);
        int length = 1;
        while (curChar == '#') {
            length++;
            curChar = section.charAt(start + length);
        }
        if (curChar != ' ') {
            return new TextToken(section.substring(start, start + length));
        } else {
            length++; // last is space after #
            return new HeaderToken(length); 
        }
    }
    
    private static Token parseTextModToken(String section, int start) {
        return TextModFabric.getTextModToken(parseTextModType(section, start));
    }

    private static Tag parseTextModType(String section, int start) {
        int i = 1;
        Tag modType = null;
        while(i <= TagsStorage.maxTagLength()) {
            Tag curTag = TagsStorage.get(section.substring(start, start + i));
            if (modType != null && curTag == null) {
                break;
            }
            modType = curTag;
            i++;
        }

        return modType;
    }


    private static Token parseTextToken(String section, int start) {
        int sectionLen = section.length();
        int i = start;
        boolean isShielded = false;
        for (; i < sectionLen; i++) {
            char curChar = section.charAt(i);
            if (!isShielded && isPartOfTag(curChar)) {
                if (parseTextModType(section, i) != null) {
                    break;
                }
            }
            if (curChar == '\n') {
                break;
            }
            isShielded = curChar == '\\';
        }

        return new TextToken(section.substring(start, i));
    }

    private static boolean isPartOfTag(char ch) {
        return ch == '*' || ch == '_' || ch == '`' || ch == '-';
    }
}