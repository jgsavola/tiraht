package tiraht;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author jonne
 */
public class LZ78Decoder {
    StringDict dict;
    HashMap<Integer, String> reverseMap;

    public LZ78Decoder(StringDict dict) {
        this.dict = dict;
        reverseMap = new HashMap<Integer, String>();
        reverseMap.put(0, "");
    }

    public LZ78Decoder() {
        this(new StringDictWithHashMap());
    }

    public String decode(ArrayList<PairToken> tokens) {
        StringBuilder output = new StringBuilder();

        for (PairToken token : tokens) {
            String key = reverseMap.get(token.index) + token.c;
            output.append(key);
            int index = dict.insert(key);
            reverseMap.put(index, key);
        }

        return output.toString();
    }
}
