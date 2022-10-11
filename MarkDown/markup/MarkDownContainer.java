package markup;

import java.util.List;

public abstract class MarkDownContainer implements MarkDownCompatable {
    protected List<MarkDownCompatable> content;

    protected MarkDownContainer(List<MarkDownCompatable> content) {
        this.content = content;
    }

    @Override
    public void toMarkdown(StringBuilder builder) {
       for (MarkDownCompatable element : content) {
            element.toMarkdown(builder);
       }   
    }
}
