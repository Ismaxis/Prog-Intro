package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Md2Html {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.printf("Arguments given: %d, expected: 2", args.length);
            System.exit(-1);
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8));
            try {
                MdTokinizer parser = new MdTokinizer();
                parser.parseMd(reader);
            } catch (IOException e) {
                // TODO: handle exception
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Cannot close reader: " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException: " + e.getMessage());
        }
    }
}