package md2html.mark;

import md2html.Tag;

public class Emphasis extends TextModificator {
    private static final String markdownTagStar = Tag.EMPHASIS_STAR.tagString;
    private static final String markdownTagUnderLine = Tag.EMPHASIS_UNDER_LINE.tagString;
    private static final String htmlOpenTag = "<em>";
    private static final String htmlCloseTag = "</em>";

    private Tag type;

    public Emphasis(Tag type) {
        super();
        this.type = type;
    }

    @Override
    public void toMarkdown(StringBuilder builder) {
        if (type == Tag.EMPHASIS_STAR) {
            super.insertChildsMD(builder, markdownTagStar);
        } else if (type == Tag.EMPHASIS_UNDER_LINE) {
            super.insertChildsMD(builder, markdownTagUnderLine);
        }
    }

    @Override
    public void toHtml(StringBuilder builder) {
        super.insertChildsHTML(builder, htmlOpenTag, htmlCloseTag);
    }
}
