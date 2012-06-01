package tiraht.lz78;

import java.io.IOException;

/**
 * <code>LZ7TokenReader</code> lukee ja palauttaa <code>LZ78Token</code>-kohteita.
 *
 * @author jgsavola
 */
public interface LZ78TokenReader {
    /**
     * Lue <code>LZ78Token</code>-kohde.
     *
     * @return <code>LZ78Token</code>-kohde.
     */
    public LZ78Token readLZ78Token() throws IOException;
}
