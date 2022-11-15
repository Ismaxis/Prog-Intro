package md2html.mark;

import md2html.Tag;

public class Emphasis extends TextModificator {
    private static final String markdownTagStar = Tag.EmphasisStar.tagString;
    private static final String markdownTagUnderLine = Tag.EmphasisUnderLine.tagString;
    private static final String htmlOpenTag = "<em>";
    private static final String htmlCloseTag = "</em>";

    private Tag type;

    public Emphasis(Tag type) {
        super();
        this.type = type;
    }

    @Override
    public void toMarkdown(StringBuilder builder) {
        if (type == Tag.EmphasisStar) {
            super.insertChildsMD(builder, markdownTagStar);
        } else if (type == Tag.EmphasisUnderLine) {
            super.insertChildsMD(builder, markdownTagUnderLine);
        }
    }

    @Override
    public void toHtml(StringBuilder builder) {
        super.insertChildsHTML(builder, htmlOpenTag, htmlCloseTag);
    }
}
