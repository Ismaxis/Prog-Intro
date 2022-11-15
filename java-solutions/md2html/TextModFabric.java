package md2html;

import md2html.mark.*;
import md2html.tokens.*;

public abstract class TextModFabric {
    public static TextModificator getNode(final Tag type) {
        if (type == Tag.EmphasisStar) {
            return new Emphasis(Tag.EmphasisStar);
        }
        if (type == Tag.EmphasisUnderLine) {
            return new Emphasis(Tag.EmphasisUnderLine);
        }
        if (type == Tag.StrongStar) {
            return new Strong(Tag.StrongStar);
        }
        if (type == Tag.StrongUnderLine) {
            return new Strong(Tag.StrongUnderLine);
        }
        if (type == Tag.Strikeout) {
            return new Strikeout();
        }
        if (type == Tag.Code) {
            return new Code();
        }
        if (type == Tag.CLOSE_IMG_TAG) {
            return new Img();
        }

        throw new EnumConstantNotPresentException(type.getClass(), type.name());
    }

    public static TextModToken getTextModToken(final Tag type) {
        if (type == Tag.EmphasisStar || type == Tag.EmphasisUnderLine) {
            return new EmphasisToken(type);
        } else if (type == Tag.StrongStar || type == Tag.StrongUnderLine) {
            return new StrongToken(type);
        } else if (type == Tag.Strikeout) {
            return new StrikeoutToken();
        }
        if (type == Tag.Code) {
            return new CodeToken();
        }
        if (type == Tag.OpenImgTag) {
            return new OpenImgToken();
        }
        if (type == Tag.MidImgTag) {
            return new MidImgToken();
        }
        if (type == Tag.CLOSE_IMG_TAG) {
            return new CloseImgToken();
        } else {
            throw new EnumConstantNotPresentException(type.getClass(), type.name());
        }
    }
}
