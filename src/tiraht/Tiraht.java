package tiraht;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import tiraht.lz78.LZ78ByteTrieCompressor;
import tiraht.lz78.LZ78Compressor.DictFillUpStrategy;
import tiraht.lz78.LZ78GeneralUnaryDecoder;
import tiraht.lz78.LZ78GeneralUnaryEncoder;
import tiraht.lz78.LZ78HashMapDecompressor;
import tiraht.lz78.LZ78NullEncoder;

public class Tiraht {
    private static void writeMagic(DataOutputStream dos) throws IOException {
        String magic = "~LZ78\000";
        for (byte b : magic.getBytes("US-ASCII"))
            dos.writeByte(b);
    }

    private static boolean readMagic(DataInputStream dis) throws IOException {
        String magic = "~LZ78\000";
        for (byte expectedByte : magic.getBytes("US-ASCII")) {
            byte actualByte = dis.readByte();
            if (expectedByte != actualByte)
                return false;
        }

        return true;
    }

    private static boolean verbose = false;

    private enum Mode {
        Compress,
        Decompress,
        TestCompress,
        TestByteTrieNumChildren,
        TestByteTriePhraseLength
        };

    private static Mode mode = Mode.Compress;

    /**
     * Jos forceOutput == false, kirjoita tiedostoon.
     * Jos forceOutput == true, kirjoita stdout:iin.
     * (Mukaelma gzip:n optioista -f ja -c.)
     */
    private static boolean forceOutput = false;

    /**
     * Jos noDelete == false, hävitä käsiteltävä tiedosto, jos tulostiedosto
     * on syntynyt. Jos noDelete == true, jätä käsiteltävä tiedosto ennalleen.
     */
    private static boolean noDelete = false;

    /**
     * Käytä pientä sanakirjaa, jotta muistinkäyttö ei olisi liian suuri.
     */
    private static int dictSize = -1;

    /**
     * Sanakirjan täyttyessä aloita alusta tyhjällä sanakirjalla.
     */
    private static DictFillUpStrategy dictFillUpStrategy = LZ78ByteTrieCompressor.DictFillUpStrategy.DoNothing;

    /**
     * Arvot (start=12, step=1) näyttävät tuottavan kohtalaisen tuloksen
     * tiedostoilla, joiden koko on sadoista kymmenistä kiloista muutamaan
     * megatavuun, kun sanakirjan koko on luokkaa 100000.
     *
     * Suurin koodattava koodi olkoon 30 bittiä, jotta ei tule
     * kokonaislukuylivuotoa.
     */
    private static int start = 12;
    private static int step = 1;
    private static int stop = 30;

    private static ArrayList<String> inputFilenames = new ArrayList<String>();

    public static void main(String[] args) {
        parseCommandLine(args);

        try {
            if (mode == Mode.Compress)
                compressFiles();
            else if (mode == Mode.Decompress)
                decompressFiles();
            else if (mode == Mode.TestCompress)
                testCompress();
            else if (mode == Mode.TestByteTrieNumChildren)
                testByteTrieNumChildren();
            else if (mode == Mode.TestByteTriePhraseLength)
                testByteTriePhraseLength();
        } catch (Exception ex) {
            Logger.getLogger(Tiraht.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void parseCommandLine(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if ("--dict-size".equals(args[i])) {
                dictSize = Integer.parseInt(args[++i]);
            } else if ("--dict-fill-up-strategy".equals(args[i])) {
                String opt = args[++i];
                if ("reset".equalsIgnoreCase(opt))
                    dictFillUpStrategy = DictFillUpStrategy.Reset;
                else if ("donothing".equalsIgnoreCase(opt))
                    dictFillUpStrategy = DictFillUpStrategy.DoNothing;
                else if ("freeze".equalsIgnoreCase(opt))
                    dictFillUpStrategy = DictFillUpStrategy.Freeze;
                else
                    throw new IllegalArgumentException("Tuntematon sanakirjastrategia: " + opt);
            } else if ("--general-unary-params".equals(args[i])) {
                start = Integer.parseInt(args[++i]);
                step = Integer.parseInt(args[++i]);
                stop = Integer.parseInt(args[++i]);
            } else if ("-d".equals(args[i])) {
                mode = Mode.Decompress;
            } else if ("--test-compress".equals(args[i])) {
                mode = Mode.TestCompress;
            } else if ("--test-bytetrie-num-children".equals(args[i])) {
                mode = Mode.TestByteTrieNumChildren;
            } else if ("--test-bytetrie-phrase-length".equals(args[i])) {
                mode = Mode.TestByteTriePhraseLength;
            } else if ("-f".equals(args[i])) {
                forceOutput = true;
            } else if ("-v".equals(args[i])) {
                verbose = true;
            } else if ("--no-delete".equals(args[i])) {
                noDelete = true;
            } else {
                inputFilenames.add(args[i]);
            }
        }
    }

    public static void compressFiles() {
        for (String filename : inputFilenames) {
            File inputFile = new File(filename);
            File outputFile = null;
            try {
                InputStream is;
                OutputStream os;
                boolean removeInputFile = false;

                if (inputFile.isDirectory()) {
                    System.err.println(filename + " on hakemisto. Ohitetaan.");
                    continue;
                }
                if (filename.endsWith(".lz78")) {
                    System.err.println("Tiedostolla " + filename + " on jo pääte '.lz78'. Ohitetaan.");
                    continue;
                }

                if ("-".equals(filename)) {
                    is = System.in;
                    os = System.out;
                } else {
                    is = new BufferedInputStream(new FileInputStream(inputFile));
                    if (forceOutput)
                        os = System.out;
                    else {
                        /**
                         * Syötetiedosto korvataan .lz78-loppuisella pakatulla
                         * tiedostolla.
                         */
                        String compressedFileName = filename + ".lz78";
                        outputFile = new File(compressedFileName);
                        os = new BufferedOutputStream(new FileOutputStream(outputFile));
                        removeInputFile = true;
                    }
                }

                if (verbose)
                    System.err.printf("%-30s ", filename + ":");

                /**
                 * Kompressointi
                 */
                LZ78ByteTrieCompressor compressor = new LZ78ByteTrieCompressor(dictSize, dictFillUpStrategy);
                LZ78GeneralUnaryEncoder encoder = new LZ78GeneralUnaryEncoder(os, start, step, stop);

                DataOutputStream dos = new DataOutputStream(os);
                writeMagic(dos);
                dos.writeInt(dictSize);
                dos.writeInt(dictFillUpStrategy.ordinal());
                encoder.writeHeader();
                compressor.compress(is, encoder);

                /**
                 * Puskurin tyhjentämistä ei saa unohtaa!
                 */
                encoder.flush();

                if (os != System.out)
                    os.close();

                if (removeInputFile && !noDelete)
                    inputFile.delete();

                int bytesRead = compressor.getSymbolsRead();
                int tokensWritten = compressor.getTokensWritten();
                long bytesWritten = 0;
                if (outputFile != null)
                    bytesWritten = outputFile.length();

                if (verbose) {
                    System.err.printf(Locale.ROOT,
                            "%d%% (sisään %d tavua; ulos %d tavua; %d koodia). Pakkaussuhde: %.2f:1\n",
                            (bytesRead - bytesWritten)*100 / bytesRead,
                            bytesRead,
                            bytesWritten,
                            tokensWritten,
                            (float)bytesRead / bytesWritten);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Tiraht.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Tiraht.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void decompressFiles() throws Exception {
        for (String filename : inputFilenames) {
            File inputFile = new File(filename);
            boolean removeInputFile = false;
            try {
                InputStream is = System.in;
                OutputStream os;

                if (inputFile.isDirectory()) {
                    System.err.println(filename + " on hakemisto. Ohitetaan.");
                    continue;
                }
                if (!filename.endsWith(".lz78")) {
                    System.err.println("Tiedostolla " + filename + " on tuntematon pääte. Ohitetaan.");
                    continue;
                }

                if ("-".equals(filename)) {
                    os = System.out;
                } else {
                    is = new BufferedInputStream(new FileInputStream(inputFile));
                    if (!forceOutput) {
                        String outputFileName = filename.replaceFirst("\\.lz78$", "");
                        os = new BufferedOutputStream(new FileOutputStream(outputFileName));
                    } else {
                        os = System.out;
                    }
                    removeInputFile = true;
                }

                /**
                 * Dekompressointi
                 */
                DataInputStream dis = new DataInputStream(is);
                if (!readMagic(dis)) {
                    System.err.println("Tiedosto " + filename + " ei ole ~LZ78-tiedosto. Ohitetaan");
                    continue;
                }

                int thisDictSize = dis.readInt();
                int thisStrategyOrdinal = dis.readInt();
                DictFillUpStrategy thisStrategy;
                if (DictFillUpStrategy.DoNothing.ordinal() == thisStrategyOrdinal) {
                    thisStrategy = DictFillUpStrategy.DoNothing;
                } else if (DictFillUpStrategy.Reset.ordinal() == thisStrategyOrdinal) {
                    thisStrategy = DictFillUpStrategy.Reset;
                } else if (DictFillUpStrategy.Freeze.ordinal() == thisStrategyOrdinal) {
                    thisStrategy = DictFillUpStrategy.Freeze;
                } else {
                    throw new Exception("Virheellinen sanakirjastrategia syötteessä");
                }
                LZ78HashMapDecompressor decompressor = new LZ78HashMapDecompressor(thisDictSize, thisStrategy);
                LZ78GeneralUnaryDecoder decoder = new LZ78GeneralUnaryDecoder(is);

                try {
                    decompressor.decompress(decoder, os);
                } catch (EOFException ex) {
                   /**
                    * EOFException ei ole virhe. Dataa luetaan niin kauan
                    * kunnes tiedosto loppuu. Tiedostoon ei ole koodattu
                    * sen pituutta, joten purkaja ei voi tietää tiedoston
                    * pituutta.
                    *
                    * XXX: tietyissä kohdissa ohjelma osaa odottaa lisädataa,
                    * jolloin EOF on tietysti virhe. Tämä pitäisi kuitenkin
                    * huomioida lähempänä virhettä.
                    */

                   /**
                    * Huom! Kirjoituspuskurin tyhjentäminen tässä kohdassa
                    * on kriittistä. Muuten viimeiset tavut voivat jäädä
                    * kirjoittamatta.
                    */
                    os.flush();

                    if (os != System.out)
                        os.close();

                    if (is != System.in)
                        is.close();

                    if (removeInputFile && !noDelete)
                        inputFile.delete();
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Tiraht.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Tiraht.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void testCompress() {
        for (String filename : inputFilenames) {
            File inputFile = new File(filename);
            try {
                InputStream is;

                if (inputFile.isDirectory()) {
                    System.err.println(filename + " on hakemisto. Ohitetaan.");
                    continue;
                }
                if (filename.endsWith(".lz78")) {
                    System.err.println("Tiedostolla " + filename + " on jo pääte '.lz78'. Ohitetaan.");
                    continue;
                }

                if ("-".equals(filename)) {
                    is = System.in;
                } else {
                    is = new BufferedInputStream(new FileInputStream(inputFile));
                }

                if (verbose)
                    System.err.printf("%-30s ", filename + ":");

                /**
                 * Kompressointi
                 */
                LZ78ByteTrieCompressor compressor = new LZ78ByteTrieCompressor(dictSize, dictFillUpStrategy);
                LZ78NullEncoder encoder = new LZ78NullEncoder();

                compressor.compress(is, encoder);

                /**
                 * Puskurin tyhjentämistä ei saa unohtaa!
                 */
                encoder.flush();

                int bytesRead = compressor.getSymbolsRead();
                int tokensWritten = compressor.getTokensWritten();

                if (verbose) {
                    System.err.printf(Locale.ROOT,
                            "(sisään %d tavua; ulos %d koodia).\n",
                            bytesRead,
                            tokensWritten);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Tiraht.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Tiraht.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void testByteTrieNumChildren() {
        TestByteTrie test = new TestByteTrie();
        test.testNumChildren();
    }

    private static void testByteTriePhraseLength() {
        TestByteTrie test = new TestByteTrie();
        test.testPhraseLength();
    }
}
