package markup;

public class Text implements MarkDownCompatable{
    private String text;

    public Text(String text) {
        this.text = text;
    }

    @Override
    public void toMarkdown(StringBuilder builder) {
        builder.append(text);
    }
}
