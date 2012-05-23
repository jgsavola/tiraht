package tiraht;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tiraht {
    public static void main(String[] args) {

        System.out.println("Tavuilla:");
        testWithBytes(args);

        System.out.println("\nMerkkijonoilla:");
        testWithStrings(args);
    }

    public static void testWithStrings(String[] args) {
        for (String filename : args) {
            LZ78Encoder encoder = new LZ78Encoder();
            try {
                Runtime.getRuntime().gc();
                long startMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                long startTime = System.nanoTime();
                FileReader reader = new FileReader(filename);
                ArrayList<PairToken> tokens = encoder.encode(reader);
                long stopTime = System.nanoTime();
                Runtime.getRuntime().gc();
                long stopMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                System.out.println("pakkaus(" + filename + ")"
                        + ": symboleita=" + encoder.getSymbolsRead()
                        + ", koodeja=" + encoder.getTokensWritten()
                        + " (" + (int)(encoder.getTokensWritten() / ((stopTime-startTime) / 1000000000.)) + "/s)"
                        + ", aika=" + (stopTime-startTime) / 1000000. + "ms"
                        + ", muisti=" + (stopMem - startMem));

                Runtime.getRuntime().gc();
                startMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                startTime = System.nanoTime();
                LZ78Decoder decoder = new LZ78Decoder();
                String decodedStr = decoder.decode(tokens);
                stopTime = System.nanoTime();
                Runtime.getRuntime().gc();
                stopMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                System.out.println("purku(" + filename + ")  "
                        + ": symboleita=" + decodedStr.length()
                        + ", aika=" + (stopTime-startTime) / 1000000. + "ms"
                        + ", muisti=" + (stopMem - startMem));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Tiraht.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Tiraht.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void testWithBytes(String[] args) {
        for (String filename : args) {
            LZ78ByteEncoder encoder = new LZ78ByteEncoder();
            try {
                Runtime.getRuntime().gc();
                long startMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                long startTime = System.nanoTime();
                FileInputStream reader = new FileInputStream(filename);
                ArrayList<Pair<Integer, Byte>> tokens = encoder.encode(reader);
                long stopTime = System.nanoTime();
                Runtime.getRuntime().gc();
                long stopMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                System.err.println("pakkaus(" + filename + ")"
                        + ": symboleita=" + encoder.getSymbolsRead()
                        + ", koodeja=" + encoder.getTokensWritten()
                        + " (" + (int)(encoder.getTokensWritten() / ((stopTime-startTime) / 1000000000.)) + "/s)"
                        + ", aika=" + (stopTime-startTime) / 1000000. + "ms"
                        + ", muisti=" + (stopMem - startMem));

                Runtime.getRuntime().gc();
                startMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                startTime = System.nanoTime();
                LZ78ByteDecoder decoder = new LZ78ByteDecoder();
                byte[] decodedBytes = decoder.decode(tokens);
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

    public static void smallSanityTest() throws IOException {
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
