package md2html;

public enum Tag {
    OpenImgTag("!["),
    MidImgTag("]("),
    Strikeout("--"),
    StrongUnderLine("__"),
    StrongStar("**"),
    EmphasisUnderLine("_"),
    EmphasisStar("*"),
    Code("`"),
    CLOSE_IMG_TAG(")"),

    Text,
    HEADER("#"),
    Paragraph;

    private static final int numberOfMods = 9;

    public final String tagString;

    private Tag(String tag) {
        this.tagString = tag;
    }

    private Tag() {
        tagString = "";
    }

    public static String[] getTagsStrings() {
        Tag[] tags = values();
        String[] values = new String[numberOfMods];
        for (int i = 0; i < numberOfMods; i++) {
            values[i] = tags[i].tagString;
        }
        return values;
    }
}
