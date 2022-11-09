package md2html.mark;

public class Paragraph extends Root {
    private static final String htmlOpenTag = "<p>";
    private static final String htmlCloseTag = "</p>";

    public Paragraph() {
        super();
    }

    @Override
    public void toMarkdown(StringBuilder builder) {
        super.toMarkdown(builder);
    }

    @Override
    public void toHtml(StringBuilder builder) {
        builder.append(htmlOpenTag);
        super.toHtml(builder);
        builder.append(htmlCloseTag);
    }
}
