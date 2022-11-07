package md2html.mark;

import java.util.List;
import md2html.Pair;

public class Text implements Node {
    private String text;

    public Text() {}

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
        builder.append(escapeHTML(text));
    }

    private String escapeHTML(String text) {
        for (Pair<String, String> pair : htmlSpecialSymbols) {
            text = text.replace(pair.first(), pair.second());
        }
        return text;
    }

    private static List<Pair<String, String>> htmlSpecialSymbols = List.of(
        new Pair<String, String>("&", "&amp;"),
        new Pair<String, String>("<", "&lt;"),
        new Pair<String, String>(">", "&gt;")
    );
}
