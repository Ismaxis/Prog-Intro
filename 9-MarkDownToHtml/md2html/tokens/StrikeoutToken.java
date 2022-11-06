package md2html.tokens;

import md2html.Tag;

public class StrikeoutToken extends TextModToken {
    public StrikeoutToken() {
        super(2, Tag.Strikeout);
    }

    @Override
    public String getMdTag() {
        return "--";
    }
}
