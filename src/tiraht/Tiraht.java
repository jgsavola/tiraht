package tiraht;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import tiraht.lz78.LZ78ByteTrieCompressor;
import tiraht.lz78.LZ78GeneralUnaryDecoder;
import tiraht.lz78.LZ78GeneralUnaryEncoder;
import tiraht.lz78.LZ78HashMapDecompressor;

public class Tiraht {
    private enum Mode {
        Compress,
        Decompress
    };

    static Mode mode = Mode.Compress;

    /**
     * Käytä pientä sanakirjaa, jotta muistinkäyttö ei olisi liian suuri.
     */
    static int dictSize = -1;

    /**
     * Sanakirjan täyttyessä aloita alusta tyhjällä sanakirjalla.
     */
    static LZ78ByteTrieCompressor.DictFillUpStrategy dictFillUpStrategy = LZ78ByteTrieCompressor.DictFillUpStrategy.DoNothing;

    /**
     * Arvot (start=12, step=1) näyttävät tuottavan kohtalaisen tuloksen
     * tiedostoilla, joiden koko on sadoista kymmenistä kiloista muutamaan
     * megatavuun, kun sanakirjan koko on luokkaa 100000.
     *
     * Suurin koodattava koodi olkoon 30 bittiä, jotta ei tule
     * kokonaislukuylivuotoa.
     */
    static int start = 12;
    static int step = 1;
    static int stop = 30;

    static ArrayList<String> inputFilenames = new ArrayList<String>();

    public static void main(String[] args) {
        parseCommandLine(args);
        if (mode == Mode.Compress)
            compressFiles();
        else
            decompressFiles();
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
            } else {
                inputFilenames.add(args[i]);
            }
        }
    }

    public static void compressFiles() {
        for (String filename : inputFilenames) {
            try {
                InputStream is;
                if ("-".equals(filename)) {
                    is = System.in;
                } else {
                    is = new FileInputStream(filename);
                }

                /**
                 * Kompressointi
                 */
                LZ78ByteTrieCompressor compressor = new LZ78ByteTrieCompressor(dictSize, dictFillUpStrategy);
                LZ78GeneralUnaryEncoder encoder = new LZ78GeneralUnaryEncoder(System.out, start, step, stop);
                encoder.writeHeader();
                compressor.compress(is, encoder);

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

    public static void decompressFiles() {
        for (String filename : inputFilenames) {
            try {
                InputStream is;
                if ("-".equals(filename)) {
                    is = System.in;
                } else {
                    is = new FileInputStream(filename);
                }

                /**
                 * Dekompressointi
                 */
                LZ78HashMapDecompressor decompressor = new LZ78HashMapDecompressor(dictSize, dictFillUpStrategy);
                LZ78GeneralUnaryDecoder decoder = new LZ78GeneralUnaryDecoder(is);

                try {
                    decompressor.decompress(decoder, System.out);
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
                    System.out.flush();
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Tiraht.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Tiraht.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
