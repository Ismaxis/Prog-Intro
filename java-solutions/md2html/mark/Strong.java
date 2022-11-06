package md2html.mark;

public class Strong extends TextModificator {
    private static final String markdownTag = "__";
    private static final String htmlOpenTag = "<strong>";
    private static final String htmlCloseTag = "</strong>";

    @Override
    public void toMarkdown(StringBuilder builder) {
        super.insertChildsMD(builder, markdownTag);
    }

    @Override
    public void toHtml(StringBuilder builder) {
        super.insertChildsHTML(builder, htmlOpenTag, htmlCloseTag);             
    }   
}
