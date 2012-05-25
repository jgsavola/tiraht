package tiraht.lz78;

import java.util.Iterator;

/**
 * Lue <code>LZ78Token</code>-kohteita iteraattorista.
 *
 * @author jgsavola
 */
public class LZ78FromIteratorDecoder implements LZ78TokenReader {
    private Iterator<LZ78Token> iterator;

    /**
     * Luo uusi <code>LZ78FromIteratorDecoder</code> iteraattorille.
     *
     * @param iterator Käytettävä iteraattori.
     */
    public LZ78FromIteratorDecoder(Iterator<LZ78Token> iterator) {
        this.iterator = iterator;
    }

    /**
     * {@inheritDoc}
     *
     * @return Seuraava koodi tai <code>null</code>, luettava on loppu.
     */
    @Override
    public LZ78Token readLZ78Token() {
        if (!iterator.hasNext())
            return null;

        return iterator.next();
    }
}
