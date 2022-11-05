package markup;

import java.util.List;

public class Strikeout extends TextModificator {
    private static final String markdownTag = "~";
    private static final String htmlOpenTag = "<s>";
    private static final String htmlCloseTag = "</s>";

    public Strikeout(List<Drawable> content) {
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
