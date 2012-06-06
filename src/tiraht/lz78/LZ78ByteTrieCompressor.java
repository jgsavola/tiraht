package tiraht.lz78;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import tiraht.dict.ByteTrie;

/**
 * LZ78-kompressio käyttäen sanakirjana <code>ByteTrie</code>-rakennetta.
 *
 * @author jgsavola
 */
public class LZ78ByteTrieCompressor implements LZ78Compressor {
    private ByteTrie trie;

    private int symbolsRead;
    private int tokensWritten;
    private int maxPrefixIndex;

    private DictFillUpStrategy fillUpStrategy;
    private int dictSizeLimit;

    public LZ78ByteTrieCompressor() {
        this(-1, DictFillUpStrategy.DoNothing);
    }

    public LZ78ByteTrieCompressor(int dictSizeLimit, DictFillUpStrategy fillUpStrategy) {
        if (dictSizeLimit < -1 || dictSizeLimit == 0)
            throw new IllegalArgumentException("dictSizeLimit:in pitää olla -1 tai positiivinen kokonaisluku.");

        trie = new ByteTrie();
        this.dictSizeLimit = dictSizeLimit;
        this.fillUpStrategy = fillUpStrategy;
    }

    public void compress(byte[] source, LZ78TokenWriter writer) throws IOException {
        ByteArrayInputStream is = new ByteArrayInputStream(source);
        compress(is, writer);
    }

    @Override
    public void compress(InputStream is, LZ78TokenWriter writer) throws IOException {
        int symbol;
        while ((symbol = is.read()) != -1) {
            symbolsRead++;

            if (maxPrefixIndex == dictSizeLimit - 1
                && fillUpStrategy == DictFillUpStrategy.Reset) {
                trie = new ByteTrie();
                maxPrefixIndex = 0;
            }

            ByteTrie<Integer> node;
            ByteTrie<Integer> lastNode = trie;
            int lastPrefixIndex = 0;
            while ((node = lastNode.retrieve((byte)symbol)) != null) {
                /**
                 * Lue seuraava symboli. Jos syöte ehtyy, lopetetaan
                 * iterointi. Viimeinen solmu on tällöin lastNode:ssa
                 * ja viimeinen symboli symbol:ssa.
                 */
                int nextSymbol = is.read();
                if (nextSymbol == -1)
                    break;

                lastNode = node;
                lastPrefixIndex = (Integer)node.getValue();
                symbol = nextSymbol;
                symbolsRead++;
            }
            /**
             * Avainta ei löytynyt sanakirjasta.
             */
            if (dictSizeLimit == -1 || maxPrefixIndex < dictSizeLimit - 1)
                lastNode.insert((byte)symbol, ++maxPrefixIndex);
            writer.writeLZ78Token(new LZ78Token(lastPrefixIndex, (byte)symbol));
            tokensWritten++;
        }
    }

    public int getMaxTokenIndex() {
        return maxPrefixIndex;
    }

    public int getSymbolsRead() {
        return symbolsRead;
    }

    public int getTokensWritten() {
        return tokensWritten;
    }
}
