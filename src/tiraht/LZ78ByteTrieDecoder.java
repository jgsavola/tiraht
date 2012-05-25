package tiraht;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author jonne
 */
public class LZ78ByteTrieDecoder {
    HashMap<Integer, ByteArray> reverseMap;

    public LZ78ByteTrieDecoder() {
        this.reverseMap = new HashMap<Integer, ByteArray>();
        this.reverseMap.put(0, new ByteArray());
    }

    public byte[] decode(ArrayList<Pair<Integer, Byte>> tokens) {
        ByteArray output = new ByteArray();

        int index = 1;
        for (Pair<Integer, Byte> token : tokens) {
            ByteArray key = reverseMap.get(token.first);
            //for (byte b : key.getBytes())
            //    output.add(b);
            output.add(key);
            output.add(token.second);

            ByteArray keybb = new ByteArray(key.getBytes(), key.length() + 1);
            keybb.add(token.second);
            reverseMap.put(index++, keybb);
        }

        return output.getBytes();
    }
}
