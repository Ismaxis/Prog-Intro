package md2html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import md2html.mark.*;
import md2html.tokens.*;

public class TreeBuiler {
    private List<Root> listOfRoot;

    public TreeBuiler() {
        listOfRoot = new ArrayList<>();
    }

    public void addSection(List<Token> tokens) {
        List<StackEntry> stack = new ArrayList<>();
        Map<Tag, Integer> openedTags = new HashMap<>();
        Root curRoot;
        if (tokens.get(0) instanceof HeaderToken) {
            curRoot = new Header(tokens.get(0).length() - 1); // level = length - 1, because of end space
            tokens.remove(0);
        } else {
            curRoot = new Paragraph();
        }
        mainloop: for (Token curToken : tokens) {
            Tag curTokenTag = curToken.type();
            if (curTokenTag == Tag.Text) {
                stack.add(new Text(((TextToken) curToken).text()));
            } else if (curTokenTag == Tag.EndOfLine) {
                stack.add(new Text(curToken.getMdTag()));
            } else {
                int stackSize = stack.size();
                Integer openedTag = openedTags.get(curTokenTag);
                if (openedTag == null) {
                    openedTags.put(curTokenTag, stackSize);
                } else {
                    TextModificator newTextMod = TextModFabric.getNode(curTokenTag);
                        for (int j = openedTag; j < stackSize - 1; j++) {
                            StackEntry removed = stack.remove(openedTag + 1);
                            if (removed instanceof Token) {
                                newTextMod.addChild(new Text(((Token) removed).getMdTag()));
                            } else if (removed instanceof Node) {
                                newTextMod.addChild((Node) removed);
                            }
                        }
                        openedTags.put(curTokenTag, null);
                        stack.remove(openedTag.intValue());
                        stack.add(newTextMod);
                        continue mainloop;
                }
                if (curToken instanceof HeaderToken) {
                    stack.add(new Text(curToken.getMdTag()));
                } else {
                    stack.add(curToken);
                } 
            }
        }
        stack.remove(stack.size() - 1);
        for (StackEntry stackEntry : stack) {
            if (stackEntry instanceof Token) {
                curRoot.addChild(new Text(((Token) stackEntry).getMdTag()));
            } else {
                curRoot.addChild((Node) stackEntry);
            }
        }

        listOfRoot.add(curRoot);
    }

    public void toHTML(StringBuilder builder) {
        for (Root root : listOfRoot) {
            root.toHtml(builder);
            builder.append("\n");
        }
    }
}  

