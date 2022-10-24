package markup;

import java.util.List;

public class UnorderedList extends HtmlList {
    private static final String htmlOpenTag = "<ul>";
    private static final String htmlCloseTag = "</ul>";
    
    public UnorderedList(List<ListItem> content) {
        super(content);
    }

    @Override
    public void toHtml(StringBuilder builder) {
        super.insertContent(builder, htmlOpenTag, htmlCloseTag);
    }    
}