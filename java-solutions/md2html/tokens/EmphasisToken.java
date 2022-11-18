package md2html.tokens;

import md2html.Tag;

public class EmphasisToken extends TextModToken {
    public EmphasisToken(Tag type) {
        super(1, type);
    }

    @Override
    public String getMdTag() {
        if (super.type == Tag.EMPHASIS_STAR) {
            return "*";
        } else if (super.type == Tag.EMPHASIS_UNDER_LINE) {
            return "_";
        } else {
            return "";
        }
    }
}
