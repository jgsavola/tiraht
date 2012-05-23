package tiraht;

/**
 * Sanakirjarajapinta <code>String</code>-muotoisille sanoille.
 *
 * <code>StringDict</code>-luokka antaa juoksevan
 * kokonaislukuindeksin siihen syötetyille erillisille sanoille.
 * Sanakirjasta voi hakea sanan indeksin sanan avulla.
 *
 * Tämä sanakirjarajapinta tallentaa sanat merkkijonoina
 * (Javan <code>String</code>-luokka).
 *
 * @author jgsavola
 */
public interface StringDict {
    /**
     * Lisää merkkijono sanakirjaan.
     *
     * Huom! Avainta s ei saa olla ennestään taulukossa! Tämän varmistaminen
     * on käyttäjän vastuulla.
     *
     * @param s Lisättävä merkkijono.
     * @return Merkkijonon indeksi sanakirjassa tai -1, jos merkkijonoa ei löydy.
     */
    public int insert(String s);

    /**
     * Etsi merkkijono s sanakirjasta.
     *
     * @param s Etsittävä merkkijono.
     * @return Merkkijonon indeksi sanakirjassa tai -1, jos merkkijonoa ei löydy.
     */
    public int search(String s);

    /**
     * Anna seuraava vapaa indeksi.
     *
     * @return Seuraava vapaa indeksi.
     */
    public int getNextIndex();
}
