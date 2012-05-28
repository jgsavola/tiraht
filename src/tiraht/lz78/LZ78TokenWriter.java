package tiraht.lz78;

import java.io.IOException;

/**
 * Rajapinta <code>LZ78Token</code>-muotoisten kohteiden kirjoittamiseen.
 *
 * @author jgsavola
 */
public interface LZ78TokenWriter {
    /**
     * Kirjoita <code>LZ78Token</code>-muotoisia kohteita.
     *
     * @param token Kirjoitettava kohde.
     */
    public void writeLZ78Token(LZ78Token token) throws IOException;
}
