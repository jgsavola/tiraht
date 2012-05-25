package tiraht.lz78;

import java.io.IOException;
import java.io.InputStream;

/**
 * Rajapinta Lempel-Ziv 78 -kompressioon.
 *
 * LZ78Compressor tuottaa virran (kokonaisluku, tavu)-pareja. Näiden koodaaminen
 * tavuiksi käyttäjän vastuulla.
 *
 * @author jgsavola
 */
public interface LZ78Compressor {
    /**
     * Kompressoi tavumuotoinen syötevirta (kokonaisluku, tavu)-pareiksi.
     *
     * @param is Syötevirta, josta kompressoitavat tavut luetaan.
     * @param writer Tulostusvirta johon kompressoinnin tuottamat parit kirjoitetaan.
     * @throws IOException Syötevirta <code>is</code> tai
     *     <code>LZ78Token</code>-kirjoittaja voi aiheuttaa poikkeuksen.
     */
    public void compress(InputStream is, LZ78TokenWriter writer) throws IOException;
}
