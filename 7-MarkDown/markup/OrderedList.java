package markup;

import java.util.List;

public class OrderedList extends HtmlList {
    private static final String htmlOpenTag = "<ol>";
    private static final String htmlCloseTag = "</ol>";
    
    public OrderedList(List<ListItem> content) {
        super(content);
    }

    @Override
    public void toHtml(StringBuilder builder) {
        super.insertContent(builder, htmlOpenTag, htmlCloseTag);
    }    
}