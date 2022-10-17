package markup;

import java.util.List;

public class Strikeout extends TextModificator {

    public Strikeout(List<MarkDownCompatable> content) {
        super(content, "~");
    }
}
