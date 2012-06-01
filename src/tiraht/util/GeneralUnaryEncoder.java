package tiraht.util;

import java.io.IOException;

/**
 * Koodaa kokonaislukuja "General Unary" -koodilla.
 *
 * Lähde: Salomon, David: Data Compression. The Complete Reference, 2007.
 *
 * @author jgsavola
 */
public class GeneralUnaryEncoder {
    private int start;
    private int step;
    private int stop;
    private long numberOfDifferentCodes;

    private BitOutputStream bs;

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
     * bittivirtaan.
     *
     * @param bs Bittivirta, johon koodi kirjoitetaan.
     * @param start Lyhimmän bittijonon pituus.
     * @param step Bittien määrä, jolla bittijonon pituutta kasvatetaan.
     * @param stop Pisimmän bittijonon pituus
     */
    public GeneralUnaryEncoder(BitOutputStream bs, int start, int step, int stop) {
        if (stop < start)
            throw new IllegalArgumentException("stop < start");
        if (stop <= 0 || step <= 0 || stop <= 0)
            throw new IllegalArgumentException("stop <= 0 || step <= 0 || stop <= 0");

        this.bs = bs;
        this.start = start;
        this.step = step;
        this.stop = stop;

        numberOfDifferentCodes = ((1L << (stop+step)) - (1L << start)) / ((1L << step) - 1L);
    }

    /**
     * Anna koodaajan tukemien erilaisten koodien lukumäärä.
     *
     * @return Koodien lukumäärä.
     */
    public long getNumberOfDifferentCodes() {
        return numberOfDifferentCodes;
    }

    /**
     * Anna <code>start</code>-arvo.
     *
     * @return
     */
    public int getStart() {
        return start;
    }

    /**
     * Anna <code>step</code>-arvo.
     *
     * @return
     */
    public int getStep() {
        return step;
    }

    /**
     * Anna <code>stop</code>-arvo.
     *
     * @return
     */
    public int getStop() {
        return stop;
    }

    /**
     * Koodaa kokonaisluku ja kirjoita se bittivirtaan.
     *
     * @param num Koodattava kokonaisluku väliltä [0, numDifferentCodes).
     * @throws IOException
     */
    public void encode(int num) throws IOException {
        int a;
        int offset = 0;

        if (num < 0 || num >= numberOfDifferentCodes)
            throw new IllegalArgumentException("" + num + " >= " + numberOfDifferentCodes);

        /**
         * Selvitetään iteratiivisesti, minkä kertaluokan lukua ollaan
         * koodaamassa.
         *
         *   n       kertaluokka (n:s askel).
         *   a       bittien määrä, joka vaaditaan luvun binääriesitykseen
         *   offset  aikaisempien kertaluokkien koodaamien lukujen
         *           (kumulatiivinen) määrä.
         *
         * FIXME: tätä kannattaisi optimoida.
         */
        for (int n = 0; ; n++) {
            a = start + n*step;
            if (num < offset + (1 << a)) {
                encodeUnary(a, n + 1);
                encodeBinary(a, num - offset);
                return;
            }
            offset += 1 << a;
        }
    }

    /**
     * Koodaa kokonaisluku käyttäen "Unary"-koodausta.
     *
     * Huom! tämä poikkeaa normaalista Unary-koodauksesta siten, että
     * jos ollaan koodaamassa suurimmalla bittimäärällä (<code>stop</code>
     * bittiä), jätetään päättävä "0" kirjoittamatta.
     *
     * @param bits Seuraavaksi koodattavan kokonaisluvun vaatima bittimäärä.
     * @param n Ollaan koodaamassa kokonaislukua, jonka koko on kertaluokkaa
     *     <code>n</code>.
     * @throws IOException
     */
    private void encodeUnary(int bits, int n) throws IOException {
        for (int i = 0; i < n - 1; i++)
            bs.writeBit(true);

        if (bits != stop)
            bs.writeBit(false);
    }

    /**
     * Koodaa kokonaisluku <code>n</code> käyttäen <code>nBits</code> bittiä
     * normaalilla binäärikoodauksella.
     *
     * @param nBits Tarvittavien bittien määrä.
     * @param n Koodattava kokonaisluku.
     * @throws IOException
     */
    private void encodeBinary(int nBits, int n) throws IOException {
        while (nBits-- > 0)
            bs.writeBit((n & (0x1 << nBits)) != 0);
    }
}
