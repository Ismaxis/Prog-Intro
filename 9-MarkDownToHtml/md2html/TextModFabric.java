package md2html;

import md2html.mark.*;
import md2html.tokens.*;

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
        if (type == Tag.CloseImgTag) {
            return new Img();
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
        if (type == Tag.OpenImgTag) {
            return new OpenImgToken();
        } 
        if (type == Tag.MidImgTag) {
            return new MidImgToken();
        } 
        if (type == Tag.CloseImgTag) {
            return new CloseImgToken();
        }  

        throw new EnumConstantNotPresentException(type.getClass(), type.name());
    }
}
