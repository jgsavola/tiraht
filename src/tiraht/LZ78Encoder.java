/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiraht;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

/**
 *
 * @author jonne
 */
public class LZ78Encoder {
    private StringDict dict;

    private int symbolsRead;
    private int tokensWritten;
    private int maxTokenIndex;

    public LZ78Encoder(StringDict dict) {
        this.dict = dict;
        symbolsRead = 0;
        tokensWritten = 0;
        maxTokenIndex = 0;
    }

    public LZ78Encoder() {
        this(new StringDictWithHashMap());
    }

    public ArrayList<PairToken> encode(String source) throws IOException {
        StringReader reader = new StringReader(source);
        return encode(reader);
    }

    public ArrayList<PairToken> encode(Reader reader) throws IOException {
        ArrayList<PairToken> tokens = new ArrayList<PairToken>();

        int symbol;
        while ((symbol = reader.read()) != -1) {
            symbolsRead++;
            StringBuilder sb = new StringBuilder();
            sb.append((char)symbol);
            int lastToken = 0;
            int token = 0;
            while ((token = dict.search(sb.toString())) != -1) {
                int nextSymbol = reader.read();
                if (nextSymbol == -1)
                    break;
                symbolsRead++;
                lastToken = token;
                symbol = nextSymbol;
                sb.append((char)symbol);
            }
            /**
             * Avainta ei löytynyt sanakirjasta.
             */
            maxTokenIndex = dict.insert(sb.toString());
            tokens.add(new PairToken(lastToken, (char)symbol));
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
