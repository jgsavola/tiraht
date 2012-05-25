package tiraht.dict;

import tiraht.util.ByteArray;

/**
 * Sanakirjarajapinta <code>byte[]</code>-muotoisille tavujonoille.
 *
 * <code>ByteDict</code>-rajapinta antaa juoksevan
 * kokonaislukuindeksin siihen syötetyille erillisille sanoille.
 *
 * Tämä sanakirjarajapinta tallentaa sanat tavujonoina
 * (<code>byte[]</code>).
 *
 * @author jgsavola
 */
public interface ByteDict {
    /**
     * Lisää tavujono sanakirjaan.
     *
     * Huom! Avainta s ei saa olla ennestään taulukossa! Tämän varmistaminen
     * on käyttäjän vastuulla.
     *
     * @param b Lisättävä tavujono.
     * @return Tavujonon indeksi sanakirjassa tai -1, jos tavujonoa ei löydy.
     */
    public int insert(byte[] b);

    /**
     * Etsi tavujono b sanakirjasta.
     *
     * @param b Etsittävä tavujono.
     * @return Tavujonon indeksi sanakirjassa tai -1, jos tavujonoa ei löydy.
     */
    public int search(byte[] b);

    /**
     * Anna seuraava vapaa indeksi.
     *
     * @return Seuraava vapaa indeksi.
     */
    public int getNextIndex();

    public int search(ByteArray key);

    public int insert(ByteArray key);
}
