package tiraht;

import java.util.Arrays;
import tiraht.dict.ByteTrie;
import tiraht.util.ByteArray;

/**
 * Testaa ByteTrie:n toimintaa erikokoisilla aineistoilla.
 *
 * @author jonne
 */
public class TestByteTrie {
    private ByteTrie trie;

    public TestByteTrie() {
        trie = new ByteTrie();
        byte[] bytes = new byte[4];
        ByteArray ba = new ByteArray(bytes);
        for (long i = 0; i < 10000000L; i++) {
            bytes[3] = (byte)((i >> 24) & 0xff);
            bytes[2] = (byte)((i >> 16) & 0xff);
            bytes[1] = (byte)((i >> 8) & 0xff);
            bytes[0] = (byte)((i >> 0) & 0xff);
            trie.insert(ba.iterator(), (int)i);
            if (i % 1000000L == 0L)
                System.out.println("i " + i);
        }

        System.out.println("Hello " + trie.search(ba.iterator()));
    }
}
