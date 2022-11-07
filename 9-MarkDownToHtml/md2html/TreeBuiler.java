package md2html;

import java.util.ArrayList;
import java.util.Arrays;
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
        int[] openedTags = new int[Tag.values().length];
        Arrays.fill(openedTags, -1);
        
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
                Integer openedTag = openedTags[curTokenTag.ordinal()];
                if (openedTag == -1) {
                    openedTags[curTokenTag.ordinal()] = stackSize;
                } else {
                    TextModificator newTextMod = TextModFabric.getNode(curTokenTag);
                    for (int j = openedTag; j < stackSize - 1; j++) {
                        addChildToTextMod(openedTags, newTextMod, stack.remove(openedTag + 1));
                    }
                    openedTags[curTokenTag.ordinal()] = -1;
                    stack.remove(openedTag.intValue());
                    stack.add(newTextMod);
                    continue mainloop;
                }
                if (curTokenTag == Tag.Header) {
                    stack.add(new Text(curToken.getMdTag()));
                } else {
                    stack.add(curToken);
                } 
            }
        }
        stack.remove(stack.size() - 1);
        for (StackEntry stackEntry : stack) {
            addStackEntryAsChild(curRoot, stackEntry);
        }

        listOfRoot.add(curRoot);
    }

    private void addStackEntryAsChild(Root curRoot, StackEntry stackEntry) {
        if (stackEntry instanceof Token) {
            curRoot.addChild(new Text(((Token) stackEntry).getMdTag()));
        } else {
            curRoot.addChild((Node) stackEntry);
        }
    }

    private void addChildToTextMod(int[] openedTags, TextModificator newTextMod, StackEntry removed) {
        if (removed instanceof Token) {
            Token removedToken = (Token) removed;
            openedTags[removedToken.type().ordinal()] = - 1;
            newTextMod.addChild(new Text(removedToken.getMdTag()));
        } else if (removed instanceof Node) {
            newTextMod.addChild((Node) removed);
        }
    }

    public void toHTML(StringBuilder builder) {
        for (Root root : listOfRoot) {
            root.toHtml(builder);
            builder.append("\n");
        }
    }
}  

