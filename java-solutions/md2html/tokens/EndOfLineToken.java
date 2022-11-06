package md2html.tokens;

import md2html.Tag;

public class EndOfLineToken extends Token {
    public EndOfLineToken() {
        super(1, Tag.EndOfLine);
    }

    @Override
    public String getMdTag() {
        return "\n";
    }
}
