package tiraht.util;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Tämä luokka lukee bittejä tavuvirrasta käyttäen tavupuskuria.
 *
 * Bitit luetaan järjestyksessä merkitsevimmästä bitistä vähiten
 * merkitsevään. Jos bittien määrä ei ole jaollinen 8:lla, viimeisen
 * tavun vähiten merkitsevät bitit ovat nollia.
 *
 * @author jgsavola
 */
public class BitInputStream {
    private byte buffer;
    private int position;
    private InputStream is;

    /**
     * Luo uusi bittivirta.
     *
     * @param os Syötevirta, josta bitit luetaan.
     */
    public BitInputStream(InputStream is) {
        this.buffer = 0;
        this.position = 7;
        this.is = is;
    }

    /**
     * Lue yksi bitti syötevirrasta käyttäen yhden tavun puskuria.
     *
     * @return Luettu bitti: true ==> 1, false ==> 0
     * @throws EOFException Syötevirta ehtyi.
     * @throws IOException IO-virhe.
     */
    public boolean readBit() throws IOException {
        if (++position == 8) {
            position = 0;
            int input = is.read();
            if (input == -1) {
                throw new EOFException();
            }

            buffer = (byte)input;
        }

        return (buffer & (1 << (7 - position))) != 0;
    }
}
