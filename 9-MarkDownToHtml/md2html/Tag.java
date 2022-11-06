package md2html;

public enum Tag {
    Header,
    Paragraph,
    Text,
    EmphasisStar,
    EmphasisUnderLine,
    StrongStar,
    StrongUnderLine,
    Strikeout,
    Code,
    EndOfLine;
    // Header ("#"),
    // EmphasisStar ("*"),
    // EmphasisUnderLine ("_"),
    // StrongStar ("**"),
    // StrongUnderLine ("__"),
    // Strikeout ("--"),
    // Code ("`")
    // Text ("");

    // private String tag;
    // private Tag(String tag) {
    //     this.tag = tag;
    // }

    // public static Tag getTagByToken(String token) {
    //     for (Tag tag : Tag.values()) {
    //         if (tag.tag == token) {
    //             return tag;
    //         }
    //     }

    //     return null;
    // }
}
