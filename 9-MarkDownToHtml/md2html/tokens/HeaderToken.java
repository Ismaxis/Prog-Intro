package md2html.tokens;

import md2html.Tag;

public class HeaderToken extends Token {
    public HeaderToken(int level) {
        super(level, Tag.Header);
    }

    @Override
    public String getMdTag() {
        return Tag.Header.tagString.repeat(super.length - 1) + " ";
    }
}
