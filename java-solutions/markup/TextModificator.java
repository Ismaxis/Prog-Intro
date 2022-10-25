package markup;

import java.util.List;

public abstract class TextModificator implements Drawable {
    private final List<Drawable> content;
    protected TextModificator(List<Drawable> content) {
        this.content = content;
    }

    protected void insertContentMD(StringBuilder builder, String markdownTag) {
        builder.append(markdownTag);
        for (Drawable element : content) {
            element.toMarkdown(builder);
        } 
        builder.append(markdownTag);
    }

    protected void insertContentHTML(StringBuilder builder, String openTag, String closeTag) {
        builder.append(openTag);
        for (Drawable element : content) {
            element.toHtml(builder);
        } 
        builder.append(closeTag);
    }
}
