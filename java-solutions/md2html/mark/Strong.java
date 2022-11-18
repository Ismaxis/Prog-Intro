package md2html.mark;

import md2html.Tag;

public class Strong extends TextModificator {
    private static final String markdownTagStar = Tag.STRONG_STAR.tagString;
    private static final String markdownTagUnderLine = Tag.STRONG_UNDER_LINE.tagString;
    private static final String htmlOpenTag = "<strong>";
    private static final String htmlCloseTag = "</strong>";

    private Tag type;

    public Strong(Tag type) {
        this.type = type;
    }

    @Override
    public void toMarkdown(StringBuilder builder) {
        if (type == Tag.STRONG_STAR) {
            super.insertChildsMD(builder, markdownTagStar);
        } else if (type == Tag.STRONG_UNDER_LINE) {
            super.insertChildsMD(builder, markdownTagUnderLine);
        }
    }

    @Override
    public void toHtml(StringBuilder builder) {
        super.insertChildsHTML(builder, htmlOpenTag, htmlCloseTag);
    }
}
