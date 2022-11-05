package markup;

import java.util.List;

public class Paragraph implements Listable, MarkDownCompatable, HtmlCompatable {
    List<Drawable> content;
    
    public Paragraph(List<Drawable> content) {
        this.content = content;
    }

    @Override
    public void toMarkdown(StringBuilder builder) {
        for (Drawable element : content) {
            element.toMarkdown(builder);
        }
    }

    @Override
    public void toHtml(StringBuilder builder) {
        for (Drawable element : content) {
            element.toHtml(builder);
        } 
    }
}
