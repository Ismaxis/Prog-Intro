package md2html.tokens;

import md2html.Tag;

public class StrongToken extends TextModToken {
    public StrongToken(Tag type) {
        super(2, type);
    }

    @Override
    public String getMdTag() {
        if (super.type == Tag.STRONG_STAR) {
            return "**";
        } else if (super.type == Tag.STRONG_UNDER_LINE) {
            return "__";
        } else {
            return "";
        }
    }
}
