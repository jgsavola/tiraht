package tiraht.lz78;

import java.io.IOException;
import java.io.InputStream;
import tiraht.util.BitInputStream;
import tiraht.util.GeneralUnaryDecoder;

/**
 * Dekoodaa LZ78-kompressoitua virtaa käyttäen
 * <code>GeneralUnaryDecoder</code>-dekooderia.
 *
 * Lähde: Salomon, David: Data Compression. The Complete Reference, 2007.
 *
 * @author jgsavola
 */
public class LZ78GeneralUnaryDecoder implements LZ78TokenReader {
    private GeneralUnaryDecoder unaryDecoder;
    private BitInputStream bis;
    private InputStream is;

    /**
     * Luo uusi dekooderi.
     *
     * Lue "General Unary"-koodin parametrit syötevirrasta.
     *
     * @param is Syötevirta.
     */
    public LZ78GeneralUnaryDecoder(InputStream is) throws IOException {
        int start = is.read();
        int step  = is.read();
        int stop  = is.read();
        init(is, start, step, stop);
    }

    /**
     * Luo uusi dekooderi annetuilla "General Unary"-parametreilla.
     *
     * @param is Syötevirta.
     * @param start Lyhimmän bittijonon pituus.
     * @param step Bittien määrä, jolla bittijonon pituutta kasvatetaan.
     * @param stop Pisimmän bittijonon pituus
     * @throws IOException
     */
    public LZ78GeneralUnaryDecoder(InputStream is, int start, int step, int stop) throws IOException {
        init(is, start, step, stop);
    }

    private void init(InputStream is, int start, int step, int stop) {
        this.is = is;
        this.bis = new BitInputStream(is);
        unaryDecoder = new GeneralUnaryDecoder(bis, start, step, stop);
    }

    @Override
    public LZ78Token readLZ78Token() throws IOException {
        int prefixIndex = unaryDecoder.decode();

        byte suffixByte = 0;
        for (int b = 7; b >= 0; b--)
            if (bis.readBit())
                suffixByte += (1 << b);

        return new LZ78Token(prefixIndex, suffixByte);
    }
}
