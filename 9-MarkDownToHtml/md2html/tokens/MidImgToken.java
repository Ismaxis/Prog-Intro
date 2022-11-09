package md2html.tokens;

import md2html.Tag;

public class MidImgToken extends TextModToken  {

    public MidImgToken() {
        super(2, Tag.MidImgTag);
    }

    @Override
    public String getMdTag() {
        return "](";
    }
    
}
