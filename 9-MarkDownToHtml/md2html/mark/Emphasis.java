package md2html.mark;

import java.util.List;

public class Emphasis extends TextModificator {
    private static final String markdownTag = "*" ;
    private static final String htmlOpenTag = "<em>";
    private static final String htmlCloseTag = "</em>";

    @Override
    public void toMarkdown(StringBuilder builder) {
        super.insertChildsMD(builder, markdownTag);
    }
    
    @Override
    public void toHtml(StringBuilder builder) {
        super.insertChildsHTML(builder, htmlOpenTag, htmlCloseTag);        
    }  
}
