package md2html.tokens;

import md2html.Tag;

public class TextToken extends Token {
    private String text;

    public TextToken(String text) {
        super(text.length(), Tag.Text);
        this.text = text.replace("\\", "");
    }

    public String text() {
        return text;
    }

    @Override
    public String getMdTag() {
        return text();
    }
}
