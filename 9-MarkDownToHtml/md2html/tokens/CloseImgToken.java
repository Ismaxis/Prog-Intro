package md2html.tokens;

import md2html.Tag;

public class CloseImgToken extends TextModToken {

    public CloseImgToken() {
        super(1, Tag.CloseImgTag);
    }

    @Override
    public String getMdTag() {
        return ")";
    }

}
