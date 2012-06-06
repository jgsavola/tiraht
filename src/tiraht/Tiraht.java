package tiraht;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import tiraht.lz78.LZ78ByteTrieCompressor;
import tiraht.lz78.LZ78ByteTrieCompressor.DictFillUpStrategy;
import tiraht.lz78.LZ78GeneralUnaryDecoder;
import tiraht.lz78.LZ78GeneralUnaryEncoder;
import tiraht.lz78.LZ78HashMapDecompressor;

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
        Decompress
    };

    private static Mode mode = Mode.Compress;

    /**
     * Jos forceOutput == false, kirjoita tiedostoon.
     * Jos forceOutput == true, kirjoita stdout:iin.
     * (Mukaelma gzip:n optioista -f ja -c.)
     */
    private static boolean forceOutput = false;

    /**
     * Käytä pientä sanakirjaa, jotta muistinkäyttö ei olisi liian suuri.
     */
    private static int dictSize = -1;

    /**
     * Sanakirjan täyttyessä aloita alusta tyhjällä sanakirjalla.
     */
    private static LZ78ByteTrieCompressor.DictFillUpStrategy dictFillUpStrategy = LZ78ByteTrieCompressor.DictFillUpStrategy.DoNothing;

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
            else
                decompressFiles();
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
                    dictFillUpStrategy = LZ78ByteTrieCompressor.DictFillUpStrategy.Reset;
                else if ("donothing".equalsIgnoreCase(opt))
                    dictFillUpStrategy = LZ78ByteTrieCompressor.DictFillUpStrategy.DoNothing;
                else if ("freeze".equalsIgnoreCase(opt))
                    dictFillUpStrategy = LZ78ByteTrieCompressor.DictFillUpStrategy.Freeze;
                else
                    throw new IllegalArgumentException("Tuntematon sanakirjastrategia: " + opt);
            } else if ("--general-unary-params".equals(args[i])) {
                start = Integer.parseInt(args[++i]);
                step = Integer.parseInt(args[++i]);
                stop = Integer.parseInt(args[++i]);
            } else if ("-d".equals(args[i])) {
                mode = Mode.Decompress;
            } else if ("-f".equals(args[i])) {
                forceOutput = true;
            } else if ("-v".equals(args[i])) {
                verbose = true;
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
                    System.err.println("'" + filename + "' on hakemisto. Ohitetaan.");
                    continue;
                }
                if (filename.endsWith(".lz78")) {
                    System.err.println("Tiedostolla '" + filename + "' on jo pääte '.lz78'. Ohitetaan.");
                    continue;
                }

                if ("-".equals(filename)) {
                    is = System.in;
                    os = System.out;
                } else {
                    is = new FileInputStream(inputFile);
                    if (forceOutput)
                        os = System.out;
                    else {
                        /**
                         * Syötetiedosto korvataan .lz78-loppuisella pakatulla
                         * tiedostolla.
                         */
                        String compressedFileName = filename + ".lz78";
                        outputFile = new File(compressedFileName);
                        os = new FileOutputStream(outputFile);
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

                if (removeInputFile)
                    inputFile.delete();

                int bytesRead = compressor.getSymbolsRead();
                int tokensWritten = compressor.getTokensWritten();
                long bytesWritten = 0;
                if (outputFile != null)
                    bytesWritten = outputFile.length();

                System.err.println("" + (bytesRead - bytesWritten)*100 / bytesRead
                        + "% (sisään " + bytesRead + " tavua; ulos "
                        + tokensWritten + " koodia pakattuna " + bytesWritten + " tavuun). Pakkaussuhde: " + (float)bytesRead / bytesWritten + ":1");
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
                InputStream is;
                OutputStream os;

                if (inputFile.isDirectory()) {
                    System.err.println("'" + filename + "' on hakemisto. Ohitetaan.");
                    continue;
                }
                if (!filename.endsWith(".lz78")) {
                    System.err.println("Tiedostolla '" + filename + "' on tuntematon pääte. Ohitetaan.");
                    continue;
                }

                if ("-".equals(filename)) {
                    is = System.in;
                    os = System.out;
                } else {
                    String outputFileName = filename.replaceFirst("\\.lz78$", "");
                    is = new FileInputStream(inputFile);
                    os = new FileOutputStream(outputFileName);
                    removeInputFile = true;
                }

                /**
                 * Dekompressointi
                 */
                DataInputStream dis = new DataInputStream(is);
                if (!readMagic(dis)) {
                    System.err.println("Tiedosto '" + filename + "' ei ole ~LZ78-tiedosto. Ohitetaan");
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

                    if (removeInputFile)
                        inputFile.delete();
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Tiraht.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Tiraht.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
