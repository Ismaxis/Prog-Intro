package markup;

import java.util.List;

public class Strong extends TextModificator {

    public Strong(List<MarkDownCompatable> content) {
        super(content, "__");
    }
}
