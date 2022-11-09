package md2html.mark;

import md2html.Tag;

public class Strikeout extends TextModificator {
    private static final String markdownTag = Tag.Strikeout.tagString;
    private static final String htmlOpenTag = "<s>";
    private static final String htmlCloseTag = "</s>";

    @Override
    public void toMarkdown(StringBuilder builder) {
        super.insertChildsMD(builder, markdownTag);
    }
    
    @Override
    public void toHtml(StringBuilder builder) {
        super.insertChildsHTML(builder, htmlOpenTag, htmlCloseTag);         
    }   
}
