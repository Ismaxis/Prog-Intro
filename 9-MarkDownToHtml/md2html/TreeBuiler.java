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
            int stackSize = stack.size();
            if (curTokenTag == Tag.TEXT) {
                stack.add(new Text(((TextToken) curToken).text()));
            } else if (curTokenTag == Tag.CLOSE_IMG_TAG) {
                int openIndex = openedTags[Tag.OPEN_IMG_TAG.ordinal()];
                int midIndex = openedTags[Tag.MID_IMG_TAG.ordinal()];
                if (openIndex != -1 && midIndex != -1 && openIndex < midIndex) {
                    pushImageOnStack(stack, openedTags, curTokenTag, stackSize, openIndex, midIndex);
                } else {
                    stack.add(new Text((curToken.getMdTag())));
                }
            } else {
                Integer openedTag = openedTags[curTokenTag.ordinal()];
                if (openedTag == -1) {
                    openedTags[curTokenTag.ordinal()] = stackSize;
                } else if (curTokenTag == Tag.OPEN_IMG_TAG || curTokenTag == Tag.MID_IMG_TAG) {
                    openedTags[curTokenTag.ordinal()] = stackSize;
                } else {
                    pushTextModOnStack(stack, openedTags, curTokenTag, stackSize, openedTag);
                    continue mainloop;
                }
                if (curTokenTag == Tag.HEADER) {
                    stack.add(new Text(curToken.getMdTag()));
                } else {
                    stack.add(curToken);
                }
            }
        }

        for (StackEntry stackEntry : stack) {
            curRoot.addChild(castStackEntryToNode(openedTags, stackEntry));
        }
        listOfRoot.add(curRoot);
    }

    private void pushTextModOnStack(List<StackEntry> stack, int[] openedTags, Tag curTokenTag, int stackSize,
            Integer openedTag) {
        TextModificator newTextMod = Tag.getNode(curTokenTag);
        for (int j = openedTag; j < stackSize - 1; j++) {
            newTextMod.addChild(castStackEntryToNode(openedTags, stack.remove(openedTag + 1)));
        }
        openedTags[curTokenTag.ordinal()] = -1;
        stack.remove(stack.size() - 1);
        stack.add(newTextMod);
    }

    private void pushImageOnStack(List<StackEntry> stack, int[] openedTags, Tag curTokenTag, int stackSize,
            int openIndex,
            int midIndex) {
        TextModificator img = Tag.getNode(curTokenTag);
        StringBuilder builder = new StringBuilder();

        betweenBracketsToString(stack, openedTags, midIndex, stackSize - 1, builder);
        String src = builder.toString();
        builder.setLength(0);

        betweenBracketsToString(stack, openedTags, openIndex, midIndex - 1, builder);
        String alt = builder.toString();

        ((Img) img).setProps(alt, src);
        stack.add(img);
    }

    private void betweenBracketsToString(List<StackEntry> stack, int[] openedTags, int from, int to,
            StringBuilder builder) {
        for (int i = from; i < to; i++) {
            getTextFromRemoved(stack.remove(from + 1), openedTags, builder);
        }
        stack.remove(from);
    }

    private void getTextFromRemoved(StackEntry removed, int[] openedTags, StringBuilder s) {
        if (removed instanceof Token) {
            Token removedToken = (Token) removed;
            openedTags[removedToken.type().ordinal()] = -1;
            s.append(removedToken.getMdTag());
        } else {
            Node removedNode = (Node) removed;
            removedNode.toMarkdown(s);
        }
    }

    private Node castStackEntryToNode(int[] openedTags, StackEntry stackEntry) {
        if (stackEntry instanceof Token) {
            Token removedToken = (Token) stackEntry;
            openedTags[removedToken.type().ordinal()] = -1;
            return new Text(removedToken.getMdTag());
        } else {
            return ((Node) stackEntry);
        }
    }

    public void toHTML(StringBuilder builder) {
        for (Root root : listOfRoot) {
            root.toHtml(builder);
            builder.append(System.lineSeparator());
        }
    }
}
