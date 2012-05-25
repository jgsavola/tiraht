package tiraht;

import java.io.BufferedOutputStream;
import tiraht.lz78.LZ78FromIteratorDecoder;
import tiraht.lz78.LZ78ByteTrieCompressor;
import tiraht.lz78.LZ78ToArrayListEncoder;
import tiraht.lz78.LZ78Token;
import tiraht.lz78.LZ78HashMapDecompressor;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import tiraht.lz78.LZ78ByteStreamEncoder;

public class Tiraht {
    public static void main(String[] args) {
        System.out.println("Tavuilla (trie):");
        //testWithByteTrie(args);
        testWithByteTrieWriteToFile(args);
    }

    public static void testWithByteTrie(String[] args) {
        for (String filename : args) {
            try {
                Runtime.getRuntime().gc();
                long startMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                long startTime = System.nanoTime();
                FileInputStream reader = new FileInputStream(filename);

                /**
                 * Kompressointi
                 */
                LZ78ByteTrieCompressor compressor = new LZ78ByteTrieCompressor();
                LZ78ToArrayListEncoder encoder = new LZ78ToArrayListEncoder();
                compressor.compress(reader, encoder);
                ArrayList<LZ78Token> tokens = encoder.getTokens();

                long stopTime = System.nanoTime();
                Runtime.getRuntime().gc();
                long stopMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

                long elapsed = stopTime - startTime;
                long memUsed = stopMem - startMem;

                System.err.println("pakkaus(" + filename + ")"
                        + ": symboleita=" + compressor.getSymbolsRead()
                        + " (" + (int)(compressor.getSymbolsRead() / (elapsed / 1000000000.)) + "/s)"
                        + ", koodeja=" + compressor.getTokensWritten()
                        + " (" + (int)(compressor.getTokensWritten() / (elapsed / 1000000000.)) + "/s)"
                        + ", aika=" + elapsed / 1000000. + "ms"
                        + ", muisti=" + memUsed);

                Runtime.getRuntime().gc();
                startMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                startTime = System.nanoTime();

                /**
                 * Dekompressointi
                 */
                LZ78HashMapDecompressor decompressor = new LZ78HashMapDecompressor();
                LZ78FromIteratorDecoder decoder = new LZ78FromIteratorDecoder(tokens.iterator());
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                decompressor.decompress(decoder, os);
                byte[] decodedBytes = os.toByteArray();

                stopTime = System.nanoTime();
                Runtime.getRuntime().gc();
                stopMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                System.err.println("purku(" + filename + ")  "
                        + ": symboleita=" + decodedBytes.length
                        + ", aika=" + (stopTime-startTime) / 1000000. + "ms"
                        + ", muisti=" + (stopMem - startMem));
                //System.out.print(new String(decodedBytes));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Tiraht.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Tiraht.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void testWithByteTrieWriteToFile(String[] args) {
        for (String filename : args) {
            try {
                Runtime.getRuntime().gc();
                long startMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                long startTime = System.nanoTime();
                FileInputStream reader = new FileInputStream(filename);

                /**
                 * Kompressointi
                 */
                LZ78ByteTrieCompressor compressor = new LZ78ByteTrieCompressor();
                FileOutputStream fos = new FileOutputStream(filename + ".lz78");
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                LZ78ByteStreamEncoder encoder = new LZ78ByteStreamEncoder(bos);
                compressor.compress(reader, encoder);
                bos.flush();
                fos.close();
                long stopTime = System.nanoTime();
                Runtime.getRuntime().gc();
                long stopMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

                long elapsed = stopTime - startTime;
                long memUsed = stopMem - startMem;

                System.err.println("pakkaus(" + filename + ")"
                        + ": symboleita=" + compressor.getSymbolsRead()
                        + " (" + (int)(compressor.getSymbolsRead() / (elapsed / 1000000000.)) + "/s)"
                        + ", koodeja=" + compressor.getTokensWritten()
                        + " (" + (int)(compressor.getTokensWritten() / (elapsed / 1000000000.)) + "/s)"
                        + ", aika=" + elapsed / 1000000. + "ms"
                        + ", muisti=" + memUsed);

                Runtime.getRuntime().gc();
                startMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                startTime = System.nanoTime();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Tiraht.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Tiraht.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
