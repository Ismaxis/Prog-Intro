package markup;

import java.util.List;

public abstract class HtmlList implements Listable {
    private final List<ListItem> content;

    public HtmlList(List<ListItem> content) {
        this.content = content;
    }

    public void insertContent(StringBuilder builder, String htmlOpenTag, String htmlCloseTag) {
        builder.append(htmlOpenTag);
        for (ListItem element : content) {
            element.toHtml(builder);
        }
        builder.append(htmlCloseTag);
    }
}
