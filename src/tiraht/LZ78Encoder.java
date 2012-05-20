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
    StringDict dict;

    public LZ78Encoder(StringDict dict) {
        this.dict = dict;
    }

    public LZ78Encoder() {
        this.dict = new StringDictWithHashMap();
    }

    public ArrayList<PairToken> encode(String source) throws IOException {
        StringReader reader = new StringReader(source);
        return encode(reader);
    }

    public ArrayList<PairToken> encode(Reader reader) throws IOException {
        ArrayList<PairToken> tokens = new ArrayList<PairToken>();

        int symbol;
        while ((symbol = reader.read()) != -1) {
            String key = "";
            int lastToken = 0;
            int token = 0;
            while ((token = dict.search(key + (char)symbol)) != -1) {
                int nextSymbol = reader.read();
                if (nextSymbol == -1)
                    break;
                key += (char)symbol;
                lastToken = token;
                symbol = nextSymbol;
            }
            /**
             * Avainta ei löytynyt sanakirjasta.
             */
            int nextToken = dict.insert(key + (char)symbol);
            tokens.add(new PairToken(lastToken, (char)symbol));
        }

        return tokens;
    }
}
