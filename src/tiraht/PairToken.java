package tiraht;

/**
 * <code>PairToken</code> kuvaa merkkijonoa, jonka merkkijonomuotoinen
 * alkuosa on tallennettuna sanakirjaan indeksillä
 * <code>indeksi</code> ja loppuosa on merkki <code>merkki</code>.
 *
 * @author jonne
 */
public class PairToken {
    int index;
    char c;

    /**
     * Luo uusi <code>PairToken</code>
     *
     * @param index Indeksi sanakirjaan.
     * @param c Kuvattavan merkkijonon viimeinen merkki.
     */
    public PairToken(int index, char c) {
        this.index = index;
        this.c = c;
    }

    @Override
    public String toString() {
        return "(" + index + ", '" + c + "')";
    }
}
