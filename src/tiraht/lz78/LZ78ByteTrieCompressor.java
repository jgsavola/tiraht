package tiraht.lz78;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import tiraht.dict.ByteTrie;
import tiraht.dict.ByteTrie;
import tiraht.lz78.LZ78Compressor;
import tiraht.lz78.LZ78Token;
import tiraht.lz78.LZ78TokenWriter;

/**
 * LZ78-kompressio käyttäen sanakirjana <code>ByteTrie</code>-rakennetta.
 *
 * @author jgsavola
 */
public class LZ78ByteTrieCompressor implements LZ78Compressor {
    private ByteTrie trie;

    private int symbolsRead;
    private int tokensWritten;
    private int maxTokenIndex;

    public enum DictFillUpStrategy {
        DoNothing,
        Freeze,
        Reset
    };

    private DictFillUpStrategy fillUpStrategy;
    private int dictSizeLimit;

    public LZ78ByteTrieCompressor() {
        this(-1, DictFillUpStrategy.DoNothing);
    }

    public LZ78ByteTrieCompressor(int dictSizeLimit, DictFillUpStrategy fillUpStrategy) {
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

            if (dictSizeLimit != -1) {
                if (maxTokenIndex == dictSizeLimit) {
                    if (fillUpStrategy == DictFillUpStrategy.Freeze) {
                        trie = new ByteTrie();
                        maxTokenIndex = 0;
                    }
                }
            }

            ByteTrie<Integer> bt;
            ByteTrie<Integer> lastbt = trie;
            int lastToken = 0;
            Integer token;
            while ((bt = lastbt.retrieve((byte)symbol)) != null) {
                int nextSymbol = is.read();
                if (nextSymbol == -1)
                    break;
                lastbt = bt;
                symbolsRead++;
                token = (Integer)bt.getValue();
                lastToken = token;
                symbol = nextSymbol;
            }
            /**
             * Avainta ei löytynyt sanakirjasta.
             */
            if (dictSizeLimit == -1 || maxTokenIndex < dictSizeLimit)
                lastbt.insert((byte)symbol, ++maxTokenIndex);
            writer.writeLZ78Token(new LZ78Token(lastToken, (byte)symbol));
            tokensWritten++;
        }
    }

    public int getMaxTokenIndex() {
        return maxTokenIndex;
    }

    public int getSymbolsRead() {
        return symbolsRead;
    }

    public int getTokensWritten() {
        return tokensWritten;
    }
}
