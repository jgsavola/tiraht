package tiraht;

import tiraht.dict.ByteTrie;
import tiraht.util.ByteArray;

/**
 * Testaa ByteTrie:n toimintaa erikokoisilla aineistoilla.
 *
 * @author jonne
 */
public class TestByteTrie {
    private ByteTrie trie;
    private ByteArray ba;

    public TestByteTrie() {
    }

    /**
     * Testaa vaihtelemalla lapsisolmujen määrää (trie:n haarautumisastetta).
     */
    public void testNumChildren() {
        System.out.println("#nchldr\ttime");
        int max = 10000000;
        for (int m = 1; m <= 256; m *= 2) {
            for (int n = 6; n < 7; n++) {
                trie = new ByteTrie();
                System.gc();System.gc();

                testInsert(m, n, max);
                {
                    long start = System.nanoTime();
                    testSearch(m, n, max);
                    long stop = System.nanoTime();
                    System.out.printf("%d\t%.3f\n", m,
                        (stop - start) / 1000000000.0);
                }
            }
        }
    }

    /**
     * Testaa vaihtelemalla fraasin pituutta.
     */
    public void testPhraseLength() {
        System.out.println("#phrlen\ttime / 1m searches");
        int max = 1000000;
        int m = 256;
        for (int n = 1; n < 32; n++) {
            trie = new ByteTrie();
            System.gc();System.gc();

            testInsert(m, n, max);
            {
                long start = System.nanoTime();
                testSearch(m, n, max);
                long stop = System.nanoTime();
                System.out.printf("%d\t%.3f\n", n,
                    (stop - start) / 1000000000.0);
            }
        }
    }

    private void testInsert(int m, int n) {
        testInsert(m, n, (int)Math.pow(m, n));
    }

    private void testInsert(int m, int n, long max) {
        byte[] bytes = new byte[n];
        for (int i = 0; i < max; i++) {
            ba = new ByteArray(bytes);
            for (int j = 0; j < n; j++) {
                bytes[j] = (byte) ((i / (int)Math.pow(m, j)) % m);
            }
            trie.insert(ba.iterator(), i);
        }
    }

    private void testSearch(int m, int n) {
        testInsert(m, n, (int)Math.pow(m, n));
    }

    private void testSearch(int m, int n, long max) {
        byte[] bytes = new byte[n];
        for (int i = 0; i < max; i++) {
            ba = new ByteArray(bytes);
            for (int j = 0; j < n; j++) {
                bytes[j] = (byte) ((i / (int)Math.pow(m, j)) % m);
            }
            int ret = trie.search(ba.iterator());
        }
    }
}
