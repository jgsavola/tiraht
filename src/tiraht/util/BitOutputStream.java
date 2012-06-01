package tiraht.util;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Tämä luokka kirjoittaa bittejä tavuvirtaan käyttäen tavupuskuria.
 *
 * Bitit kirjoitetaan järjestyksessä merkitsevimmästä bitistä vähiten
 * merkitsevään. Jos bittien määrä ei ole jaollinen 8:lla, viimeisen
 * tavun vähiten merkitsevät bitit ovat nollia.
 *
 * @author jgsavola
 */
public class BitOutputStream {
    private byte buffer;
    private int position;
    private OutputStream os;

    /**
     * Luo uusi bittivirta.
     *
     * @param os Tulostusvirta, johon bitit kirjoitetaan.
     */
    public BitOutputStream(OutputStream os) {
        this.buffer = 0;
        this.position = 0;
        this.os = os;
    }

    /**
     * Kirjoita yksi bitti bittivirtaan.
     *
     * @param bit Kirjoitettava bitti: true => 1, false => 0.
     * @throws IOException 
     */
    public void writeBit(boolean bit) throws IOException {
        /**
         * Puskurin loppupään jäännösbittien oletetaan olevan nollia,
         * joten vain ykköset lisätään puskuriin.
         */
        if (bit)
            buffer |= 1 << (7 - position);

        position++;
        if (position == 8) {
            os.write(buffer);
            position = 0;
            buffer = 0;
        }
    }

    /**
     * Kirjoita viimeinen, ei-täysi tavu tulostusvirtaan.
     *
     * <p>
     * Huom! Jos tämän metodin kutsumisen jälkeen kirjoitetan lisää bittejä
     * tai kutsutaan <code>flush</code>-metodia, bittivirtaan tulee aukkoja.
     *
     * <p>
     * Puskuri alustetaan jokaisella <code>flush</code>-kutsulla.
     *
     * @throws IOException
     */
    public void flush() throws IOException {
        if (position > 0) {
            os.write(buffer);
            position = 0;
        }
        os.flush();
        buffer = 0;
    }
}
