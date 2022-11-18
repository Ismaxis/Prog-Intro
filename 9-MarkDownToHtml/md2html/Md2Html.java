package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Md2Html {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.printf("Arguments given: %d, expected: 2", args.length);
            return;
        }

        String html;
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8));
            try {
                html = MdParser.parseMdToHTML(reader);
            } finally {
                reader.close();
            }
        } catch (final IOException e) {
            System.err.println("Reading error in tokinizer: " + e.getMessage());
            return;
        }

        try {
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8));
            try {
                writer.write(html);
            } finally {
                writer.close();
            }
        } catch (final IOException e) {
            System.err.println("Writing error: " + e.getMessage());
            return;
        }
    }
}
