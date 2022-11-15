package md2html.mark;

import md2html.Tag;

public class Strong extends TextModificator {
    private static final String markdownTagStar = Tag.StrongStar.tagString;
    private static final String markdownTagUnderLine = Tag.StrongUnderLine.tagString;
    private static final String htmlOpenTag = "<strong>";
    private static final String htmlCloseTag = "</strong>";

    private Tag type;

    public Strong(Tag type) {
        this.type = type;
    }

    @Override
    public void toMarkdown(StringBuilder builder) {
        if (type == Tag.StrongStar) {
            super.insertChildsMD(builder, markdownTagStar);
        } else if (type == Tag.StrongUnderLine) {
            super.insertChildsMD(builder, markdownTagUnderLine);
        }
    }

    @Override
    public void toHtml(StringBuilder builder) {
        super.insertChildsHTML(builder, htmlOpenTag, htmlCloseTag);
    }
}
