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
    CloseImgTag(")"),
    
    Text,
    Header("#"),
    Paragraph;

    private static final int amountOfMods = 9;

    public final String tagString;

    private Tag(String tag) {
        this.tagString = tag;
    }

    private Tag() {
        tagString = "";
    }

    public static String[] getTagsStrings() {
        Tag[] tags = values();
        String[] values = new String[amountOfMods];
        for (int i = 0; i < amountOfMods; i++) {
            values[i] = tags[i].tagString;
        }
        return values;
    }
}
