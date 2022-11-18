package md2html;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import md2html.tokens.*;

public class MdParser {
    public static String parseMdToHTML(final BufferedReader givenReader) throws IOException {
        final SectionReader reader = new SectionReader(givenReader);
        final TreeBuiler tree = new TreeBuiler();

        while (reader.hasNextSection()) {
            final String section = reader.nextSection();
            final int sectionLen = section.length();
            int alreadyRead = 0;
            final List<Token> sectionTokens = new ArrayList<>();
            while (alreadyRead < sectionLen) {
                final Token nextToken = MdTokinizer.getNextToken(section, alreadyRead);
                sectionTokens.add(nextToken);
                alreadyRead += nextToken.length();
            }

            tree.addSection(sectionTokens);
        }

        final StringBuilder builder = new StringBuilder();
        tree.toHTML(builder);

        return builder.toString();
    }
}
