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
            if (curTokenTag == Tag.Text) {
                stack.add(new Text(((TextToken) curToken).text()));
            } else if (curTokenTag == Tag.CLOSE_IMG_TAG) {
                int openIndex = openedTags[Tag.OpenImgTag.ordinal()];
                int midIndex = openedTags[Tag.MidImgTag.ordinal()];
                if (openIndex != -1 && midIndex != -1 && openIndex < midIndex) {
                    TextModificator img = TextModFabric.getNode(curTokenTag);
                    StringBuilder builder = new StringBuilder();

                    for (int i = midIndex; i < stackSize - 1; i++) {
                        getTextFromRemoved(stack.remove(midIndex + 1), openedTags, builder);
                    }
                    stack.remove(midIndex);

                    String src = builder.toString();
                    builder.setLength(0);

                    for (int i = openIndex + 1; i < midIndex; i++) {
                        getTextFromRemoved(stack.remove(openIndex + 1), openedTags, builder);
                    }
                    stack.remove(openIndex);

                    String alt = builder.toString();

                    ((Img) img).setProps(alt, src);
                    stack.add(img);
                } else {
                    stack.add(new Text((curToken.getMdTag())));
                }
            } else {
                Integer openedTag = openedTags[curTokenTag.ordinal()];
                if (openedTag == -1) {
                    openedTags[curTokenTag.ordinal()] = stackSize;
                } else if (curTokenTag == Tag.OpenImgTag || curTokenTag == Tag.MidImgTag) {
                    openedTags[curTokenTag.ordinal()] = stackSize;
                } else {
                    TextModificator newTextMod = TextModFabric.getNode(curTokenTag);
                    for (int j = openedTag; j < stackSize - 1; j++) {
                        newTextMod.addChild(castRemovedToNode(openedTags, stack.remove(openedTag + 1)));
                    }
                    openedTags[curTokenTag.ordinal()] = -1;
                    stack.remove(stack.size() - 1);
                    stack.add(newTextMod);
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
            addStackEntryAsChild(curRoot, stackEntry);
        }
        listOfRoot.add(curRoot);
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

    private void addStackEntryAsChild(Root curRoot, StackEntry stackEntry) {
        if (stackEntry instanceof Token) {
            curRoot.addChild(new Text(((Token) stackEntry).getMdTag()));
        } else {
            curRoot.addChild((Node) stackEntry);
        }
    }

    private Node castRemovedToNode(int[] openedTags, StackEntry removed) {
        if (removed instanceof Token) {
            Token removedToken = (Token) removed;
            openedTags[removedToken.type().ordinal()] = -1;
            return new Text(removedToken.getMdTag());
        } else {
            return ((Node) removed);
        }
    }

    public void toHTML(StringBuilder builder) {
        for (Root root : listOfRoot) {
            root.toHtml(builder);
            builder.append(System.lineSeparator());
        }
    }
}
