package tiraht;

public class Tiraht {
    public static void main(String[] args) {
        /**
         * Esimerkki kirjasta Fundamental Data Compression, s. 137-138.
         */
        String sourceStr = "a date at a date";
        StringDict dict = new StringDictWithHashMap();
        String key = "";
        int lastToken = 0;
        for (char c : sourceStr.toCharArray()) {
            /*
             * Ei yritetä etsiä tyhjää merkkijonoa sanakirjasta.
             */
            key += c;
            int token = dict.search(key);
            if (token != -1) {
                /**
                 * Symboli löytyi. Merkitään koodi muistiin ja yritetään
                 * etsiä pitempää osumaa.
                 */
                lastToken = token;
                continue;
            }
            /**
             * Avainta ei löytynyt sanakirjasta.
             */
            token = lastToken;
            int nextToken = dict.insert(key);
            System.out.println("Tulosta (" + token + ", '" + c + "').");
            System.out.println("    Lisää sana \"" + key + "\" koodilla " + nextToken + " sanakirjaan.");

            key = "";
            lastToken = 0;
        }
    }
}
