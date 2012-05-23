/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiraht;

import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jonne
 */
public class ByteTrieTest {

    public ByteTrieTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of insert method, of class ByteTrie.
     */
    @Test
    public void testInsert_ByteArray_List() {
        System.out.println("insert");
        List<Byte> key = new LinkedList<Byte>();
        key.add((byte)0x61);
        key.add((byte)0x62);
        key.add((byte)0x63);
        Integer expValue = 42;
        ByteTrie<Integer> instance = new ByteTrie<Integer>();
        instance.insert(key, expValue);

        Integer value = instance.search(key);
        assertEquals("Triehen tallennettu arvo löytyy haettaessa.", expValue, value);

        LinkedList<Byte> emptyKey = new LinkedList<Byte>();
        instance.insert(emptyKey, 0);
        assertEquals("Triehen onnistuu tallentaa arvo tyhjällä avaimella.", 0, (int)instance.search(emptyKey));
    }

    /**
     * Test of insert method, of class ByteTrie.
     */
    @Test
    public void testInsert_ByteArray_Iterator() {
        System.out.println("insert ByteArray_Iterator");
        ByteArray key = new ByteArray();
        key.add((byte)0x61);
        key.add((byte)0x62);
        key.add((byte)0x63);

        Integer expValue = 42;
        ByteTrie<Integer> instance = new ByteTrie<Integer>();
        instance.insert(key.iterator(), expValue);

        Integer value = instance.search(key.iterator());
        assertEquals("Triehen tallennettu arvo löytyy haettaessa.", expValue, value);

        LinkedList<Byte> emptyKey = new LinkedList<Byte>();
        instance.insert(emptyKey, 0);
        assertEquals("Triehen onnistuu tallentaa arvo tyhjällä avaimella.", 0, (int)instance.search(emptyKey));
    }

    /**
     * Test of insert method, of class ByteTrie.
     */
    @Test
    public void testRetrieve() {
        System.out.println("retrieve");

        int index = 0;
        byte[] bytes = {(byte)0xa1, (byte)0xa2, (byte)0xa3};
        ByteArray key = new ByteArray();
        ByteTrie<Integer> instance = new ByteTrie<Integer>();

        instance.insert(key.iterator(), index++);

        ByteTrie<Integer> trie = instance.retrieve(key.iterator());
        assertEquals(0, (int)trie.getValue());

        key.add(bytes[0]);
        trie.insert(key.iterator(index), index);
        trie = trie.retrieve(key.iterator(index));
        assertEquals(1, (int)trie.getValue());
//
//        instance.insert(key.iterator(), index++);
//
//        key.add(bytes[1]);
//        instance.insert(key.iterator(), index++);
//
//        key.add(bytes[2]);
//        instance.insert(key.iterator(), index++);
//
//        Integer expValue = 0xa0;
//        instance.insert(key.iterator(), expValue);
//
//
//
//        key.add((byte)0x61);
//        key.add((byte)0x62);
//        key.add((byte)0x63);
//
//
//        Integer value = instance.search(key.iterator());
//        assertEquals("Triehen tallennettu arvo löytyy haettaessa.", expValue, value);
//
//        LinkedList<Byte> emptyKey = new LinkedList<Byte>();
//        instance.insert(emptyKey, 0);
//        assertEquals("Triehen onnistuu tallentaa arvo tyhjällä avaimella.", 0, (int)instance.search(emptyKey));
    }
}
