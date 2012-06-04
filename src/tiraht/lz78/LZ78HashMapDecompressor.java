package tiraht.lz78;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import tiraht.lz78.LZ78ByteTrieCompressor.DictFillUpStrategy;
import tiraht.util.ByteArray;

/**
 * Pura LZ78-kompressoinnin tuottamia <code>LZ78Token</code>-kohteita.
 *
 * @author jgsavola
 */
public class LZ78HashMapDecompressor implements LZ78Decompressor {
    private HashMap<Integer, ByteArray> reverseMap;
    private DictFillUpStrategy dictFillUpStrategy;
    private int dictSize;

    /**
     * Luo uusi <code>LZ78HashMapDecompressor</code>.
     */
    public LZ78HashMapDecompressor() {
        this(-1, DictFillUpStrategy.DoNothing);
    }

    public LZ78HashMapDecompressor(int dictSize, DictFillUpStrategy dictFillUpStrategy) {
        this.dictSize = dictSize;
        this.dictFillUpStrategy = dictFillUpStrategy;
//        if (dictSize != -1 || dictFillUpStrategy != DictFillUpStrategy.DoNothing)
//            throw new UnsupportedOperationException("Vain sanakirjastrategia DoNothing toteutettu (sanakirjan max koko -1).");

        resetDictionary();
    }

    @Override
    public void decompress(LZ78TokenReader reader, OutputStream os) throws IOException {
        int index = 1;
        LZ78Token token;
        while ((token = reader.readLZ78Token()) != null) {
            ByteArray key = reverseMap.get(token.getPrefixIndex());
            os.write(key.getBytes());
            os.write(token.getSuffixByte());

            if (index == dictSize && dictFillUpStrategy == DictFillUpStrategy.Reset) {
                resetDictionary();
                index = 1;
            }

            if (dictSize == -1 || index < dictSize) {
                ByteArray keybb = new ByteArray(key.getBytes(), key.length() + 1);
                keybb.add(token.getSuffixByte());
                reverseMap.put(index++, keybb);
            }
        }
    }

    private void resetDictionary() {
        this.reverseMap = new HashMap<Integer, ByteArray>();
        this.reverseMap.put(0, new ByteArray());
    }
}
