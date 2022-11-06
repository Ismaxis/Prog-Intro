package md2html.mark;

import java.util.ArrayList;
import java.util.List;

import md2html.tokens.Token;

public class Text extends Node {
    private String text;

    public Text() {

    }

    public Text(String text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    // public void addReamain(Token token) {
    //     if (remain == null) {
    //         remain = new ArrayList<>();
    //     }
    //     remain.add(token);
    // }

    // public void handleRamains() {
    //     if (remain == null) {
    //         return;
    //     }
    //     StringBuilder s = new StringBuilder();
    //     for (Token token : remain) {
    //         s.append(token.getMdTag());
    //     }
    //     remain = null;
    //     text = s.toString();
    // }

    private void getText(StringBuilder builder) {
        builder.append(text);
    }


    @Override
    public void toMarkdown(StringBuilder builder) {
        getText(builder);
    }

    @Override
    public void toHtml(StringBuilder builder) {
       getText(builder);
    }

}
