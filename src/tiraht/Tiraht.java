package tiraht;

import java.io.IOException;
import java.io.StringReader;

public class Tiraht {
    public static void main(String[] args) throws IOException {
        /**
         * Esimerkki kirjasta Fundamental Data Compression, s. 137-138.
         */
        String sourceStr = "a date at a date";
        StringDict dict = new StringDictWithHashMap();
        StringReader reader = new StringReader(sourceStr);
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
            System.out.println("Tulosta (" + lastToken + ", '" + (char)symbol + "').");
            System.out.println("    Lisää sana \"" + (key + (char)symbol) + "\" koodilla " + nextToken + " sanakirjaan.");
        }
    }
}
