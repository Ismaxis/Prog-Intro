package md2html.tokens;

import md2html.Tag;

public class CodeToken extends TextModToken {
    public CodeToken() {
        super(1, Tag.Code);
    }

    @Override
    public String getMdTag() {
        return "`";
    }
}
