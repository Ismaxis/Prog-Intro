package md2html.tokens;

import md2html.Tag;

public abstract class TextModToken extends Token {
    protected TextModToken(int length, Tag type) {
        super(length, type);
    }
}
