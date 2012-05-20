package tiraht;

import java.util.ArrayList;

/**
 *
 * @author jonne
 */
public class LZ78Decoder {
    StringDict dict;

    public LZ78Decoder(StringDict dict) {
        this.dict = dict;
    }

    public LZ78Decoder() {
        this.dict = new StringDictWithHashMap();
    }

    public String decode(ArrayList<PairToken> tokens) {
        String output = "";

        for (PairToken token : tokens) {
            String key = dict.lookup(token.index);
            output += key + token.c;
            dict.insert(key + token.c);
        }

        return output;
    }
}
