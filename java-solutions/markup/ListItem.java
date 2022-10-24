package markup;

import java.util.List;

public class ListItem implements HtmlCompatable {
    private List<Listable> content;
    private static final String htmlOpenTag = "<li>";
    private static final String htmlCloseTag = "</li>";
    
    public ListItem(List<Listable> content) {
        this.content = content;
    }

    @Override
    public void toHtml(StringBuilder builder) {
        builder.append(htmlOpenTag);
        for (Listable element : content) {
            element.toHtml(builder);
        }
        builder.append(htmlCloseTag);
    }
}
