package md2html.tokens;

import md2html.StackEntry;
import md2html.Tag;

public abstract class Token extends StackEntry {
    protected int length;
    protected Tag type;

    protected Token(int length, Tag type) {
        this.length = length;
        this.type = type;
    }

    public int length() {
        return length;
    }

    public Tag type() {
        return type;
    }

    public abstract String getMdTag();
}
