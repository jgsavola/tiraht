package tiraht;

import java.io.IOException;
import java.util.ArrayList;

public class Tiraht {
    public static void main(String[] args) throws IOException {
        /**
         * Esimerkki kirjasta Fundamental Data Compression, s. 137-138.
         */
        String sourceStr = "a date at a date";
        LZ78Encoder encoder = new LZ78Encoder();
        ArrayList<PairToken> tokens = encoder.encode(sourceStr);
        for (PairToken token : tokens)
            System.out.println("Tulosta (" + token.index + ", '" + token.c + "').");

        LZ78Decoder decoder = new LZ78Decoder();
        String decodedStr = decoder.decode(tokens);
        System.out.println("purettu: " + decodedStr);
        if (sourceStr.equals(decodedStr))
            System.out.println("    ==> OK");
        else
            System.out.println("    ==> virhe");
    }
}
