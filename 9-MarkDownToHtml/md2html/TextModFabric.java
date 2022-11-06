package md2html;

import md2html.mark.*;

public abstract class TextModFabric {
    public static TextModificator getNode(Tag tag) {
        if (tag.equals(Tag.EmphasisStar) || tag.equals(Tag.EmphasisUnderLine)) {
            return new Emphasis();
        }
        if (tag.equals(Tag.StrongStar) || tag.equals(Tag.StrongUnderLine)) {
            return new Strong();
        }
        if (tag.equals(Tag.Strikeout)) {;
            return new Strikeout();
        }
        if (tag.equals(Tag.Code)) {
            return new Code();
        }

        throw new EnumConstantNotPresentException(tag.getClass(), tag.name());
    }
}
