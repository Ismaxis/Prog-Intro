package md2html.tokens;

import md2html.Tag;

public class HeaderToken extends Token {
    public HeaderToken(int level) {
        super(level, Tag.HEADER);
    }

    @Override
    public String getMdTag() {
        return Tag.HEADER.tagString.repeat(super.length - 1) + " ";
    }
}
