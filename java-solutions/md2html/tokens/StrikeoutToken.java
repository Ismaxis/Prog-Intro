package md2html.tokens;

import md2html.Tag;

public class StrikeoutToken extends TextModToken {
    public StrikeoutToken() {
        super(2, Tag.STRIKEOUT);
    }

    @Override
    public String getMdTag() {
        return "--";
    }
}
