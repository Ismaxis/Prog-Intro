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
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8));
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
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8));
            try {
                writer.write(html);
            } catch (IOException e) {
                System.err.println("Writing error: " + e.getMessage());
                return;
            } finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println("Cannot close writer: " + e.getMessage());
                    return;
                }
            }
        } catch (FileNotFoundException e) {
            handleFNFE(args[1]);
            return;
        }
    }

    private static void handleFNFE(String fileName) {
        System.err.print("The file \"" + fileName + "\" does not exist, is a directory rather than a ");
        System.err.println("regular file, or for some other reason cannot be opened for reading.");
    }
}
