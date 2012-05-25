package tiraht.lz78;

import java.util.ArrayList;

/**
 * Kerää <code>LZ78Token</code>-kohteet <code>ArrayList</code>:iin.
 *
 * @author jgsavola
 */
public class LZ78ToArrayListEncoder implements LZ78TokenWriter {
    private ArrayList<LZ78Token> tokens;

    /**
     * Luo uusi <code>LZ78ToArrayListEncoder</code>. Luo samalla uusi
     * tyhjä lista.
     */
    public LZ78ToArrayListEncoder() {
        this(new ArrayList<LZ78Token>());
    }

    /**
     * Luo uusi <code>LZ78ToArrayListEncoder</code> käyttäen annettua
     * listaa.
     *
     * @param tokens Lista, johon kohteet kerätään.
     */
    public LZ78ToArrayListEncoder(ArrayList<LZ78Token> tokens) {
        this.tokens = tokens;
    }

    /**
     * Anna koodilista.
     *
     * @return <code>ArrayList&lt;LZ78Token&gt;</code>-lista.
     */
    public ArrayList<LZ78Token> getTokens() {
        return tokens;
    }

    @Override
    public void writeLZ78Token(LZ78Token token) {
        tokens.add(token);
    }
}
