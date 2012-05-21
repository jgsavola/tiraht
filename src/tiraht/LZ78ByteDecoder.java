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
        Vector<Byte> vec = new Vector<Byte>();

        for (Pair<Integer, Byte> token : tokens) {
            byte[] key = dict.lookup(token.first);
            for (byte b : key)
                vec.add(b);
            vec.add(token.second);

            Vector<Byte> keyvec = new Vector<Byte>(key.length + 1);
            for (int i = 0; i < key.length; i++)
                keyvec.add(key[i]);
            keyvec.add(token.second);
            dict.insert(keyvec);
        }

        byte[] ret = new byte[vec.size()];
        for (int i = 0; i < vec.size(); i++) {
            ret[i] = vec.get(i);
        }

        return ret;
    }
}
