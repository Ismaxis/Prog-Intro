package md2html.tokens;

import md2html.Tag;

public class MidImgToken extends TextModToken {

    public MidImgToken() {
        super(2, Tag.MID_IMG_TAG);
    }

    @Override
    public String getMdTag() {
        return "](";
    }

}
