package md2html.mark;

import java.util.ArrayList;
import java.util.List;

import md2html.Tag;

public abstract class TextModificator extends Node { 
    private List<Node> childs;
    public TextModificator() {
        childs = new ArrayList<>();
    }

    public void addChild(Node child) {
        childs.add(child);
    }

    protected void insertChildsMD(StringBuilder builder, String markdownTag) {
        builder.append(markdownTag);
        for (Node element : childs) {
            element.toMarkdown(builder);
        } 
        builder.append(markdownTag);
    }

    protected void insertChildsHTML(StringBuilder builder, String openTag, String closeTag) {
        builder.append(openTag);
        for (Node element : childs) {
            element.toHtml(builder);
        } 
        builder.append(closeTag);
    }
}
