package md2html.tokens;

import md2html.Tag;

public class OpenImgToken extends TextModToken  {

    public OpenImgToken() {
        super(2, Tag.OpenImgTag);
    }

    @Override
    public String getMdTag() {
        return "![";
    }
    
}
