package md2html.tokens;

import md2html.Tag;

public class HeaderToken extends Token {
    public HeaderToken(int level) {
        super(level, Tag.Header);
    }

    @Override
    public String getMdTag() {
        return "#".repeat(super.length - 1) + " "; // last is space 
    }
}
