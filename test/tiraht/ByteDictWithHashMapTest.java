/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiraht;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author jgsavola
 */
public class ByteDictWithHashMapTest {
    
    public ByteDictWithHashMapTest() {
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
     * Test of insert method, of class ByteDictWithHashMap.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        byte[] bytes = {0x1, 0x2, 0x4, 0x0};
        ByteDictWithHashMap dict = new ByteDictWithHashMap();
        int expResult = 1;
        int result = dict.insert(bytes);
        assertEquals("Sanakirjaan ensimmäiseksi lisätyn avaimen arvo on 1",
                expResult, result);

        byte[] lookedupBytes = dict.lookup(result).getBytes();
        assertEquals("Tavujono säilyy muuttumattomana sanakirjassa", 
                lookedupBytes, bytes);
    }
}
