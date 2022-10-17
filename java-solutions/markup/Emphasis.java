package markup;

import java.util.List;

public class Emphasis extends TextModificator {

    public Emphasis(List<MarkDownCompatable> content) {
        super(content, "*");
    }
}
