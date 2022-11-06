package md2html;

import java.util.ArrayList;
import java.util.List;

import md2html.mark.*;
import md2html.tokens.*;

public class TreeBuiler {
    private List<Root> listOfRoot;

    public TreeBuiler() {
        listOfRoot = new ArrayList<>();
    }

    public void addSection(List<Token> tokens) {
        List<StackEntry> stack = new ArrayList<>();
        Root curRoot;
        if (tokens.get(0) instanceof HeaderToken) {
            curRoot = new Header(tokens.get(0).length() - 1); // level = length - 1, because of end space
            tokens.remove(0);
        } else {
            curRoot = new Paragraph();
        }
        mainloop: for (Token token : tokens) {
            if (token.type() == Tag.Text) {
                stack.add(new Text(((TextToken) token).text()));
            } else if (token.type() == Tag.EndOfLine) {
                stack.add(new Text(token.getMdTag()));
            } else {
                int stackSize = stack.size();
                for (int i = stackSize - 1; i >= 0; i--) {
                    StackEntry stackElem = stack.get(i);
                    if (stackElem instanceof Token && token.type() == ((Token) stackElem).type()) {
                        TextModificator newTextMod = TextModFabric.getNode(token.type());
                        for (int j = i; j < stackSize - 1; j++) {
                            StackEntry removed = stack.remove(i + 1);
                            if (removed instanceof Token) {
                                newTextMod.addChild(new Text(((Token) removed).getMdTag()));
                            } else if (removed instanceof Node) {
                                newTextMod.addChild((Node) removed);
                            }
                        }
                        stack.remove(i);
                        stack.add(newTextMod);
                        continue mainloop;
                    }
                }
                if (token instanceof HeaderToken) {
                    stack.add(new Text(token.getMdTag()));
                } else {
                    stack.add(token);
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

