package tiraht;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author jonne
 */
public class LZ78ByteDecoder {
    ByteDict dict;
    HashMap<Integer, ByteArray> reverseMap;

    public LZ78ByteDecoder() {
        this(new ByteDictWithHashMap());
    }

    public LZ78ByteDecoder(ByteDict dict) {
        this.dict = dict;
        this.reverseMap = new HashMap<Integer, ByteArray>();
        this.reverseMap.put(0, new ByteArray());
    }

    public byte[] decode(ArrayList<Pair<Integer, Byte>> tokens) {
        ByteArray output = new ByteArray();

        for (Pair<Integer, Byte> token : tokens) {
            ByteArray key = reverseMap.get(token.first);
            for (byte b : key.getBytes())
                output.add(b);
            output.add(token.second);

            ByteArray keybb = new ByteArray(key.getBytes(), key.length() + 1);
            keybb.add(token.second);
            int index = dict.insert(keybb);
            reverseMap.put(index, keybb);
        }

        return output.getBytes();
    }
}
