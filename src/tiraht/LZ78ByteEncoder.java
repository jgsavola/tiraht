package tiraht;

import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author jonne
 */
public class LZ78ByteEncoder {
    private ByteDict dict;

    private int symbolsRead;
    private int tokensWritten;
    private int maxTokenIndex;

    public LZ78ByteEncoder(ByteDict dict) {
        this.dict = dict;
        symbolsRead = 0;
        tokensWritten = 0;
        maxTokenIndex = 0;
    }

    public LZ78ByteEncoder() {
        this(new ByteDictWithHashMap());
    }

    public ArrayList<Pair<Integer, Byte>> encode(byte[] source) throws IOException {
        ByteArrayInputStream reader = new ByteArrayInputStream(source);
        return encode(reader);
    }

    public ArrayList<Pair<Integer, Byte>> encode(InputStream reader) throws IOException {
        ArrayList<Pair<Integer, Byte>> tokens = new ArrayList<Pair<Integer, Byte>>();

        int symbol;
        while ((symbol = reader.read()) != -1) {
            symbolsRead++;
            Vector<Byte> key = new Vector<Byte>();
            key.add((byte)symbol);
            int lastToken = 0;
            int token = 0;
            while ((token = dict.search(key)) != -1) {
                int nextSymbol = reader.read();
                if (nextSymbol == -1)
                    break;
                symbolsRead++;
                lastToken = token;
                symbol = nextSymbol;
                key.add((byte)symbol);
            }
            /**
             * Avainta ei löytynyt sanakirjasta.
             */
            maxTokenIndex = dict.insert(key);
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
