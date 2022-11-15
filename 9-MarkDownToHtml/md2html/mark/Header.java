package md2html.mark;

public class Header extends Root {
    private final int level;

    public Header(int level) {
        super();
        this.level = level;
    }

    @Override
    public void toMarkdown(StringBuilder builder) {
        super.toMarkdown(builder);
    }

    @Override
    public void toHtml(StringBuilder builder) {
        String htmlTag = String.format("h%d", level);
        surroundTag(builder, htmlTag, false);
        super.toHtml(builder);
        surroundTag(builder, htmlTag, true);
    }

    private void surroundTag(StringBuilder builder, String tag, boolean isClose) {
        builder.append("<");
        if (isClose) {
            builder.append("/");
        }
        builder.append(tag);
        builder.append(">");
    }
}
