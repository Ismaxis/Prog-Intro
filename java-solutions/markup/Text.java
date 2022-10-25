package markup;

public class Text implements Drawable {
    private final String text;

    public Text(String text) {
        this.text = text;
    }

    @Override
    public void toMarkdown(StringBuilder builder) {
        getText(builder);
    }

    @Override
    public void toHtml(StringBuilder builder) {
       getText(builder);
    }

    private void getText(StringBuilder builder) {
        builder.append(text);
    }
}
