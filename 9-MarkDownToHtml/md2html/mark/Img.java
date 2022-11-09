package md2html.mark;

public class Img extends TextModificator {
    private static final String htmlOpenTag = "<img";
    private static final String htmlCloseTag = ">";

    private String alt;
    private String src;

    public Img() {}

    public void setProps(String alt, String src) {
        this.alt = alt;
        this.src = src;
    }

    @Override
    public void toMarkdown(StringBuilder builder) {
        builder.append(String.format("![%s](%s)", alt, src));
    }
    
    @Override
    public void toHtml(StringBuilder builder) {
        builder.append(htmlOpenTag);
        builder.append(String.format(" alt='%s' src='%s'", alt, src));
        builder.append(htmlCloseTag);
        // super.insertChildsHTML(builder, htmlOpenTag, htmlCloseTag);         
    }   
}
