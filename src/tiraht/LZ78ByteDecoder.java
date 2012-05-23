package tiraht;

import java.util.ArrayList;
import java.lang.StringBuilder;
import java.util.Vector;

/**
 *
 * @author jonne
 */
public class LZ78ByteDecoder {
    ByteDict dict;

    public LZ78ByteDecoder(ByteDict dict) {
        this.dict = dict;
    }

    public LZ78ByteDecoder() {
        this.dict = new ByteDictWithHashMap(true);
    }

//    public String decode(ArrayList<PairToken> tokens) {
//        String output = "";
//
//        for (PairToken token : tokens) {
//            String key = dict.lookup(token.index);
//            output += key + token.c;
//            dict.insert(key + token.c);
//        }
//
//        return output;
//    }
    
    public byte[] decode(ArrayList<Pair<Integer, Byte>> tokens) {
        ByteArray output = new ByteArray();

        for (Pair<Integer, Byte> token : tokens) {
            ByteArray key = dict.lookup(token.first);
            for (byte b : key.getBytes())
                output.add(b);
            output.add(token.second);

            ByteArray keybb = new ByteArray(key.getBytes(), key.length() + 1);
            keybb.add(token.second);
            dict.insert(keybb);
        }

        return output.getBytes();
    }
}
