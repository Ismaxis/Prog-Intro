package md2html;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import md2html.tokens.*;

public class MdParser {
    public static String parseMdToHTML(BufferedReader givenReader) throws IOException {
        SectionReader reader = new SectionReader(givenReader);
        TreeBuiler tree = new TreeBuiler();

        String curSection = reader.nextSection();
        while (true) {
            int sectionLen = curSection.length();
            int alreadyRead = 0;
            List<Token> sectionTokens = new ArrayList<>();
            while (alreadyRead < sectionLen) {
                Token nextToken = MdTokinizer.getNextToken(curSection, alreadyRead);
                sectionTokens.add(nextToken);
                alreadyRead += nextToken.length();
            }

            tree.addSection(sectionTokens);

            if (reader.ready()) {
                curSection = reader.nextSection();
            } else {
                break;
            }
        }

        StringBuilder builder = new StringBuilder();
        tree.toHTML(builder);

        return builder.toString();
    }
}