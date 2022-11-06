package md2html;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import md2html.tokens.*;

public class MdTokinizer {
    public static List<List<Token>> parseMd(BufferedReader givenReader) throws IOException {
        SectionReader reader = new SectionReader(givenReader);
        List<List<Token>> sections = new ArrayList<>(); 
        String curSection = reader.nextSection();
        while (true) {
            int sectionLen = curSection.length();
            int alreadyRead = 0;
            List<Token> tokens = new ArrayList<>();
            while (alreadyRead < sectionLen) {
                Token nextToken = getNextToken(curSection, alreadyRead);
                tokens.add(nextToken);
                alreadyRead += nextToken.length();
            }
            sections.add(tokens);
            if (reader.ready()) {
                curSection = reader.nextSection();
            } else {
                break;
            }
        }

        return sections;
    }
    
    private static Token getNextToken(String section, int start) throws IOException {
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
        char curChar = section.charAt(start);
        if (curChar == '*' || curChar == '_') {
            char nextChar = section.charAt(start + 1);

            Tag type = null;
            if (nextChar == curChar) {
                if (curChar == '*') {
                    type = Tag.StrongStar;
                } else if (curChar == '_') {
                    type = Tag.StrongUnderLine;
                }    
                return new StrongToken(type);
            } else {
                if (curChar == '*') {
                    type = Tag.EmphasisStar;
                } else if (curChar == '_') {
                    type = Tag.EmphasisUnderLine;
                }

                return new EmphasisToken(type);
            }
        } else if (curChar == '-') {
            char nextChar = section.charAt(start + 1);
            if (nextChar == curChar) {
                return new StrikeoutToken();
            }
        } else if (curChar == '`') {
            return new CodeToken();
        } 

        return parseTextToken(section, start);
    }

    private static Token parseTextToken(String section, int start) {
        int sectionLen = section.length();
        int i = start;
        for (; i < sectionLen; i++) {
            char curChar = section.charAt(i);
            if (isPartOfTag(curChar) || curChar == '\n') {
                if (curChar == '-' && i != sectionLen - 1) {
                    if (section.charAt(i + 1) == '-') {
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        return new TextToken(section.substring(start, i));
    }

    private static boolean isPartOfTag(char ch) {
        return ch == '*' || ch == '_' || ch == '`' || ch == '-';
    }
}