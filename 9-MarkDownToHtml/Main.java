import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import md2html.MdParser;

public class Main {
    public static void main(String[] args) {
        String s = """
            Любите ли вы *вложеные __выделения__* так,
            как __--люблю--__ их я?
              
                """;
        BufferedReader reader = new BufferedReader(new StringReader(s));
        
        try {
            MdParser.parseMdToHTML(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(s);
    }
}
