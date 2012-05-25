package tiraht.lz78;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import tiraht.util.ByteArray;

/**
 * Pura LZ78-kompressoinnin tuottamia <code>LZ78Token</code>-kohteita.
 *
 * @author jgsavola
 */
public class LZ78HashMapDecompressor implements LZ78Decompressor {
    HashMap<Integer, ByteArray> reverseMap;

    /**
     * Luo uusi <code>LZ78HashMapDecompressor</code>.
     */
    public LZ78HashMapDecompressor() {
        this.reverseMap = new HashMap<Integer, ByteArray>();
        this.reverseMap.put(0, new ByteArray());
    }

    @Override
    public void decompress(LZ78TokenReader reader, OutputStream os) throws IOException {
        int index = 1;
        LZ78Token token;
        while ((token = reader.readLZ78Token()) != null) {
            ByteArray key = reverseMap.get(token.getPrefixIndex());
            os.write(key.getBytes());
            os.write(token.getSuffixByte());

            ByteArray keybb = new ByteArray(key.getBytes(), key.length() + 1);
            keybb.add(token.getSuffixByte());
            reverseMap.put(index++, keybb);
        }
    }
}
