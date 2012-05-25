package tiraht.lz78;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Rajapinta Lempel-Ziv 78 kompression purkamiseen.
 *
 * LZ78Decompressor tuottaa tavuvirran <code>LZ78Token</code>-kohteiden
 * koodaamista merkkijonoista.
 *
 * @author jgsavola
 */
public interface LZ78Decompressor {
    /**
     * Pura LZ78-kompression tuottamia <code>LZ78Token</code>-kohteita.
     *
     * @param reader Lukija <code>LZ78Token</code> kohteille.
     * @param os Tulostusvirta tavumuotoiselle tulosdatalle.
     * @throws IOException
     */
    public void decompress(LZ78TokenReader reader, OutputStream os) throws IOException;
}
