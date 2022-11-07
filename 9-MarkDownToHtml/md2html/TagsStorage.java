package md2html;

import java.util.HashMap;
import java.util.Map;

public class TagsStorage {
    private static Map<String, Tag> map; 
    private static int maxTagLength;
    static {
        map = new HashMap<>();
        map.put("*", Tag.EmphasisStar);
        map.put("**", Tag.StrongStar);
        map.put("_", Tag.EmphasisUnderLine);
        map.put("__", Tag.StrongUnderLine);
        map.put("`", Tag.Code);
        map.put("--", Tag.Strikeout);

        maxTagLength = 0;
        for (String key : map.keySet()) {
            maxTagLength = Math.max(key.length(), maxTagLength);
        }
    }

    public static Tag get(String s) {
        return map.get(s);
    }

    public static int maxTagLength() {
        return maxTagLength;
    }
}
