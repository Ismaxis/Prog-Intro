package md2html.mark;

import java.util.ArrayList;
import java.util.List;

public abstract class Root implements MarkDownCompatable, HtmlCompatable {
    private List<Node> childs;

    public Root() {
        childs = new ArrayList<>();
    }

    public void addChild(Node child) {
        childs.add(child);
    }

    public void toMarkdown(StringBuilder builder) {
        for (Node element : childs) {
            element.toMarkdown(builder);
        }
    }

    public void toHtml(StringBuilder builder) {
        for (Node element : childs) {
            element.toHtml(builder);
        } 
    }
}
