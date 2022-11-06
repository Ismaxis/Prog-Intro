package md2html.mark;

public class Code extends TextModificator {
    private static final String markdownTag = "`";
    private static final String htmlOpenTag = "<code>";
    private static final String htmlCloseTag = "</code>";

    @Override
    public void toMarkdown(StringBuilder builder) {
        super.insertChildsMD(builder, markdownTag);
    }
    
    @Override
    public void toHtml(StringBuilder builder) {
        super.insertChildsHTML(builder, htmlOpenTag, htmlCloseTag);         
    }   
}
