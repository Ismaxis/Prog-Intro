package md2html.mark;

import md2html.Tag;

public class Img extends TextModificator {
    private static final String mdOpenTag = Tag.OPEN_IMG_TAG.tagString;
    private static final String mdMidTag = Tag.MID_IMG_TAG.tagString;
    private static final String mdCloseTag = Tag.CLOSE_IMG_TAG.tagString;

    private static final String htmlOpenTag = "<img";
    private static final String htmlCloseTag = ">";

    private String alt;
    private String src;

    public Img() {
    }

    public void setProps(String alt, String src) {
        this.alt = alt;
        this.src = src;
    }

    @Override
    public void toMarkdown(StringBuilder builder) {

        builder.append(mdOpenTag);
        builder.append(alt);
        builder.append(mdMidTag);
        builder.append(src);
        builder.append(mdCloseTag);
    }

    @Override
    public void toHtml(StringBuilder builder) {
        builder.append(htmlOpenTag);
        builder.append(String.format(" alt='%s' src='%s'", alt, src));
        builder.append(htmlCloseTag);
    }
}
