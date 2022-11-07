package md2html.mark;

public class Text implements Node {
    private String text;

    public Text() {

    }

    public Text(String text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void merge(Text that) {
        this.text += that.text;
    }

    @Override
    public void toMarkdown(StringBuilder builder) {
        builder.append(text);
    }

    @Override
    public void toHtml(StringBuilder builder) {
        builder.append(escapeHtml(text));
    }

    private Object escapeHtml(String text) {
        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}