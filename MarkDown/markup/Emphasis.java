package markup;

import java.util.List;

public class Emphasis extends TextModificator {
    private static final String markdownTag = "*" ;
    private static final String htmlOpenTag = "<em>";
    private static final String htmlCloseTag = "</em>";

    public Emphasis(List<Drawable> content) {
       super(content);
    }

    @Override
    public void toMarkdown(StringBuilder builder) {
        super.insertContentMD(builder, markdownTag);
    }
    
    @Override
    public void toHtml(StringBuilder builder) {
        super.insertContentHTML(builder, htmlOpenTag, htmlCloseTag);        
    }  
}
