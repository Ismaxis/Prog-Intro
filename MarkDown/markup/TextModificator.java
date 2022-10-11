package markup;

import java.util.List;

public abstract class TextModificator extends MarkDownContainer {
    protected final String surroundSymbols; 
    protected TextModificator(List<MarkDownCompatable> content, String surroundSymbols) {
        super(content);
        this.surroundSymbols = surroundSymbols;
    }

    @Override
    public void toMarkdown(StringBuilder builder) {
        builder.append(surroundSymbols);
        super.toMarkdown(builder);
        builder.append(surroundSymbols);        
    }
}
