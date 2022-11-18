package md2html;

import java.util.Arrays;
import md2html.mark.*;
import md2html.tokens.*;

public enum Tag {
    OPEN_IMG_TAG("![", true),
    MID_IMG_TAG("](", true),
    CLOSE_IMG_TAG(")", true),

    STRIKEOUT("--", true),

    STRONG_UNDER_LINE("__", true),
    EMPHASIS_UNDER_LINE("_", true),

    STRONG_STAR("**", true),
    EMPHASIS_STAR("*", true),

    CODE("`", true),

    TEXT,
    HEADER("#", false),
    PARAGRAPH;

    private static final class Static {
        private static int numberOfMods;
    }

    public final String tagString;

    private Tag(String tag, boolean isTextMod) {
        this.tagString = tag;
        if (isTextMod) {
            Static.numberOfMods++;
        }
    }

    private Tag() {
        this.tagString = null;
    }

    public static Tag[] getTextModTags() {
        return Arrays.copyOf(values(), Static.numberOfMods);
    }

    public static TextModificator getNode(final Tag type) {
        if (type == Tag.EMPHASIS_STAR) {
            return new Emphasis(Tag.EMPHASIS_STAR);
        } else if (type == Tag.EMPHASIS_UNDER_LINE) {
            return new Emphasis(Tag.EMPHASIS_UNDER_LINE);
        } else if (type == Tag.STRONG_STAR) {
            return new Strong(Tag.STRONG_STAR);
        } else if (type == Tag.STRONG_UNDER_LINE) {
            return new Strong(Tag.STRONG_UNDER_LINE);
        } else if (type == Tag.STRIKEOUT) {
            return new Strikeout();
        } else if (type == Tag.CODE) {
            return new Code();
        } else if (type == Tag.CLOSE_IMG_TAG) {
            return new Img();
        } else {
            throw new EnumConstantNotPresentException(type.getClass(), type.name());
        }
    }

    public static TextModToken getTextModToken(final Tag type) {
        if (type == Tag.EMPHASIS_STAR || type == Tag.EMPHASIS_UNDER_LINE) {
            return new EmphasisToken(type);
        } else if (type == Tag.STRONG_STAR || type == Tag.STRONG_UNDER_LINE) {
            return new StrongToken(type);
        } else if (type == Tag.STRIKEOUT) {
            return new StrikeoutToken();
        } else if (type == Tag.CODE) {
            return new CodeToken();
        } else if (type == Tag.OPEN_IMG_TAG) {
            return new OpenImgToken();
        } else if (type == Tag.MID_IMG_TAG) {
            return new MidImgToken();
        } else if (type == Tag.CLOSE_IMG_TAG) {
            return new CloseImgToken();
        } else {
            throw new EnumConstantNotPresentException(type.getClass(), type.name());
        }
    }
}
