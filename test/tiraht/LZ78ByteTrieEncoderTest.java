/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiraht;

import java.io.InputStream;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author jgsavola
 */
public class LZ78ByteTrieEncoderTest {

    public LZ78ByteTrieEncoderTest() {
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
     * Test of encode method, of class LZ78ByteEncoder.
     */
    @Test
    public void testEncode_byteArr() throws Exception {
        String source = "a date at a date";
        System.out.println("encode(\"" + source + "\")");
        LZ78ByteTrieEncoder instance = new LZ78ByteTrieEncoder();
        ArrayList expResult = new ArrayList<PairToken>();
        expResult.add(new Pair<Integer, Byte>(0, (byte)0x61));
        expResult.add(new Pair<Integer, Byte>(0, (byte)0x20));
        expResult.add(new Pair<Integer, Byte>(0, (byte)0x64));
        expResult.add(new Pair<Integer, Byte>(1, (byte)0x74));
        expResult.add(new Pair<Integer, Byte>(0, (byte)0x65));
        expResult.add(new Pair<Integer, Byte>(2, (byte)0x61));
        expResult.add(new Pair<Integer, Byte>(0, (byte)0x74));
        expResult.add(new Pair<Integer, Byte>(6, (byte)0x20));
        expResult.add(new Pair<Integer, Byte>(3, (byte)0x61));
        expResult.add(new Pair<Integer, Byte>(7, (byte)0x65));
        ArrayList result = instance.encode(source.getBytes());
        System.out.println(pairListToString(expResult));
        System.out.println(pairListToString(result));
        assertEquals(pairListToString(expResult), pairListToString(result));
    }

    String pairListToString(ArrayList<Pair<Integer, Byte>> tokens) {
        String result = "";
        for (Pair<Integer, Byte> token : tokens) {
            result += token + "\n";
        }

        return result;
    }
}
