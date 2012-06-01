package tiraht.util;

import java.io.IOException;

/**
 * Pura "General Unary" -koodeja kokonaisluvuiksi.
 *
 * Lähde: Salomon, David: Data Compression. The Complete Reference, 2007.
 *
 * @author jgsavola
 */
public class GeneralUnaryDecoder {
    private int start;
    private int step;
    private int stop;

    private BitInputStream bis;

    /**
     * Luo uusi "General Unary"-purkaja.
     *
     * @param bis Bittivirta, josta bitit luetaan.
     * @param start Lyhimmän bittijonon pituus.
     * @param step Bittien määrä, jolla bittijonon pituutta kasvatetaan.
     * @param stop Pisimmän bittijonon pituus.
     */
    public GeneralUnaryDecoder(BitInputStream bis, int start, int step, int stop) {
        this.start = start;
        this.step = step;
        this.stop = stop;

        this.bis = bis;
    }

    /**
     * Pura yksi kokonaisluku bittivirrasta.
     *
     * @return Purettu kokonaisluku.
     * @throws IOException
     */
    public int decode() throws IOException {
        int bits;
        int offset = 0;

        if (start == stop) {
            bits = start;
            /**
             * Unaari-osa on tyhjä.
             */
        } else {
            int n = decodeUnary();
            bits = start + n*step;

            /**
             * Tämän laskemiseen pitäisi etsiä halvempi keino.
             */
            for (int i = 0; i < n; i++)
                offset += 1 << (start + i*step);
        }

        int num = decodeBinary(bits);

        return offset + num;
    }

    /**
     * Pura unaarikoodattu luku.
     *
     * Huom! Tämä ei ole yleinen unaarikoodaus, vaan sovitettu
     * General Unary -koodausta varten.
     *
     * @return Kokonaisluku.
     * @throws IOException
     */
    private int decodeUnary() throws IOException {
        int n = 0;

        while (bis.readBit()) {
            n++;
            if (start + n == stop)
                break;
        }

        return n;
    }

    /**
     * Pura <code>bits</code>-bittinen binäärikoodattu kokonaisluku.
     *
     * @param bits Purettavien bittien lukumäärä.
     * @return Kokonaisluku.
     * @throws IOException
     */
    private int decodeBinary(int bits) throws IOException {
        int num = 0;

        for (int b = bits - 1; b >= 0; b--) {
            boolean bit = bis.readBit();
            if (bit)
                num += 1 << b;
        }

        return num;
    }
}
