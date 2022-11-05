package markup;

import java.util.List;

public class Strong extends TextModificator {
    private static final String markdownTag = "__";
    private static final String htmlOpenTag = "<strong>";
    private static final String htmlCloseTag = "</strong>";

    public Strong(List<Drawable> content) {
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
