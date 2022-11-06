package md2html.mark;

public class Paragraph extends Root {
    public Paragraph() {
        super();
    }

    @Override
    public void toMarkdown(StringBuilder builder) {
        super.toMarkdown(builder);
    }

    @Override
    public void toHtml(StringBuilder builder) {
        builder.append("<p>");
        super.toHtml(builder);
        builder.append("</p>");
    }
}
