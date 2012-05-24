package tiraht;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author jonne
 */
public class LZ78ByteTrieEncoder {
    private ByteTrie trie;

    private int symbolsRead;
    private int tokensWritten;
    private int maxTokenIndex;

    public LZ78ByteTrieEncoder(ByteTrie trie) {
        this.trie = trie;
        symbolsRead = 0;
        tokensWritten = 0;
        maxTokenIndex = 0;
    }

    public LZ78ByteTrieEncoder() {
        this(new ByteTrie());
    }

    public ArrayList<Pair<Integer, Byte>> encode(byte[] source) throws IOException {
        ByteArrayInputStream reader = new ByteArrayInputStream(source);
        return encode(reader);
    }

    public ArrayList<Pair<Integer, Byte>> encode(InputStream reader) throws IOException {
        ArrayList<Pair<Integer, Byte>> tokens = new ArrayList<Pair<Integer, Byte>>();

        maxTokenIndex = 0;
        int symbol;
        while ((symbol = reader.read()) != -1) {
            symbolsRead++;
            ByteTrie<Integer> bt = trie;
            ByteTrie<Integer> lastbt = trie;
            int lastToken = 0;
            Integer token = 0;
            while ((bt = lastbt.retrieve((byte)symbol)) != null) {
                int nextSymbol = reader.read();
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
            lastbt.insert((byte)symbol, ++maxTokenIndex);
            tokens.add(new Pair<Integer, Byte>(lastToken, (byte)symbol));
            tokensWritten++;
        }

        return tokens;
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
