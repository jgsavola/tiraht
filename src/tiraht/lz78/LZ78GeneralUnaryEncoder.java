package tiraht.lz78;

import java.io.IOException;
import java.io.OutputStream;
import tiraht.util.BitStream;
import tiraht.util.GeneralUnaryEncoder;

/**
 * Koodaa LZ78-kompressoitua virtaa käyttäen
 * <code>GeneralUnaryEncoder</code>-kooderia.
 *
 * Lähde: Salomon, David: Data Compression. The Complete Reference, 2007.
 *
 * @author jgsavola
 */
public class LZ78GeneralUnaryEncoder implements LZ78TokenWriter {
    private GeneralUnaryEncoder unaryEncoder;
    private BitStream bs;

    /**
     * Luo "General Unary" -kooderi.
     *
     * Parametrit <code>start</code>, <code>step</code>, <code>stop</code>
     * kannattaa valita koodattavien lukujen jakauman mukaan. Erilaisten
     * koodien määrä saadaan kaavasta
     *
     * <pre>
     *   (2^(stop+step) - 2^start) / (2^step - 1).
     * </pre>
     *
     * Luokka kirjoittaa koodatut kokonaisluvut suoraan annettuun
     * tulostusvirtaan käyttäen <code>BitStream</code>-luokkaa.
     *
     * @param os Tulostusvirta, johon koodi kirjoitetaan.
     * @param start Lyhimmän bittijonon pituus.
     * @param step Bittien määrä, jolla bittijonon pituutta kasvatetaan.
     * @param stop Pisimmän bittijonon pituus
     */
    public LZ78GeneralUnaryEncoder(OutputStream os, int start, int step, int stop) {
        this.bs = new BitStream(os);
        unaryEncoder = new GeneralUnaryEncoder(bs, start, step, stop);
    }

    @Override
    public void writeLZ78Token(LZ78Token token) throws IOException {
        unaryEncoder.encode(token.getPrefixIndex());
        for (int b = 7; b >= 0; b--)
            bs.writeBit((token.getSuffixByte() & (1 << b)) != 0);
    }

    @Override
    public void flush() throws IOException {
        bs.flush();
    }
}
