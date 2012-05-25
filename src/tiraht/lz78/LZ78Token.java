package tiraht.lz78;

/**
 * LZ78-kompression tuottamat parit.
 *
 * @author jgsavola
 */
public class LZ78Token {
    private int prefixIndex;
    private byte suffixByte;

    /**
     * Luo uusi <code>LZ78Token</code>.
     *
     * @param prefixIndex Koodattavan merkkijonon alkuosan indeksi sanakirjassa.
     * @param suffixByte koodattavan merkkijonon viimeinen tavu.
     */
    public LZ78Token(int prefixIndex, byte suffixByte) {
        this.prefixIndex = prefixIndex;
        this.suffixByte = suffixByte;
    }

    /**
     * Anna koodattavan merkkijonon alkuosan indeksi.
     *
     * @return Koodattavan merkkijonon alkuosan indeksi sanakirjassa.
     */
    public int getPrefixIndex() {
        return prefixIndex;
    }

    /**
     * Anna koodattavan merkkijonon viimeinen tavu.
     *
     * @return koodattavan merkkijonon viimeinen tavu.
     */
    public byte getSuffixByte() {
        return suffixByte;
    }

    /**
     * Anna merkkijonomuotoinen esitys. Huom! esitys ei välttämättä säilytä
     * kaikki arvoja.
     *
     * @return Merkkijonoesitys.
     */
    @Override
    public String toString() {
        return "(" + prefixIndex + ", '" + suffixByte + "')";
    }
}
