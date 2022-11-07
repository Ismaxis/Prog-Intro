package md2html;

import md2html.mark.*;
import md2html.tokens.CodeToken;
import md2html.tokens.EmphasisToken;
import md2html.tokens.StrikeoutToken;
import md2html.tokens.StrongToken;
import md2html.tokens.TextModToken;

public abstract class TextModFabric {
    public static TextModificator getNode(Tag type) {
        if (type == Tag.EmphasisStar || type == Tag.EmphasisUnderLine) {
            return new Emphasis();
        }
        if (type == Tag.StrongStar || type == Tag.StrongUnderLine) {
            return new Strong();
        }
        if (type == Tag.Strikeout) {;
            return new Strikeout();
        }
        if (type == Tag.Code) {
            return new Code();
        }

        throw new EnumConstantNotPresentException(type.getClass(), type.name());
    }

    public static TextModToken getTextModToken (Tag type) {
        if (type == Tag.EmphasisStar || type == Tag.EmphasisUnderLine) {
            return new EmphasisToken(type);
        }
        if (type == Tag.StrongStar || type == Tag.StrongUnderLine) {
            return new StrongToken(type);
        }
        if (type == Tag.Strikeout) {;
            return new StrikeoutToken();
        }
        if (type == Tag.Code) {
            return new CodeToken();
        }

        throw new EnumConstantNotPresentException(type.getClass(), type.name());
    }
}
