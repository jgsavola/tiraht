package tiraht;

import tiraht.lz78.LZ78FromIteratorDecoder;
import tiraht.lz78.LZ78ByteTrieCompressor;
import tiraht.lz78.LZ78ToArrayListEncoder;
import tiraht.lz78.LZ78Token;
import tiraht.lz78.LZ78HashMapDecompressor;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import tiraht.lz78.*;

public class Tiraht {
    public static void main(String[] args) {
        System.out.println("Tavuilla (trie):");
        testWithByteTrie(args);
    }

    public static void testWithByteTrie(String[] args) {
        int start;
        int step;
        int stop;
        
        LinkedList<String> la = new LinkedList<String>();
        for (int i = 0; i < args.length; i++) {
            la.add(args[i]);
        }
        start = Integer.parseInt(la.removeFirst());
        step  = Integer.parseInt(la.removeFirst());
        stop  = Integer.parseInt(la.removeFirst());
        
        for (String filename : la) {
            try {
                FileInputStream reader = new FileInputStream(filename);

                /**
                 * Kompressointi
                 */

                LZ78ByteTrieCompressor compressor = new LZ78ByteTrieCompressor(16384, LZ78ByteTrieCompressor.DictFillUpStrategy.Reset);
                LZ78GeneralUnaryEncoder encoder = new LZ78GeneralUnaryEncoder(System.out, start, step, stop);
                compressor.compress(reader, encoder);

                /**
                 * Puskurin tyhjentämistä ei saa unohtaa!
                 */
                encoder.flush();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Tiraht.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Tiraht.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
