package md2html.mark;

public abstract class TextModificator extends Root implements Node { 
    public TextModificator() {
        super();
    }

    protected void insertChildsMD(StringBuilder builder, String markdownTag) {
        builder.append(markdownTag);
        super.toMarkdown(builder);
        builder.append(markdownTag);
    } 

    protected void insertChildsHTML(StringBuilder builder, String openTag, String closeTag) {
        builder.append(openTag);
        super.toHtml(builder);
        builder.append(closeTag);
    }
}
