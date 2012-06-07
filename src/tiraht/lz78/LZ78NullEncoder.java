package tiraht.lz78;

import java.io.IOException;

/**
 * LZ78-kirjoittaja, joka ei tee mitään algoritmin testaamista varten.
 *
 * @author jonne
 */
public class LZ78NullEncoder implements LZ78TokenWriter {
    @Override
    public void writeLZ78Token(LZ78Token token) throws IOException {
    }

    @Override
    public void flush() throws IOException {
    }
}
