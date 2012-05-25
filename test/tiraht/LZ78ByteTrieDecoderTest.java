/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiraht;

import java.io.IOException;
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
public class LZ78ByteTrieDecoderTest {
    public LZ78ByteTrieDecoderTest() {
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
     * Test of decode method, of class LZ78ByteTrieDecoder.
     */
    @Test
    public void testDecode() {
        System.out.println("decode");
        byte[] sourceBytes = "a date at a date".getBytes();
        LZ78ByteTrieEncoder encoder = new LZ78ByteTrieEncoder();
        LZ78ByteTrieDecoder decoder = new LZ78ByteTrieDecoder();
        byte[] expResult = sourceBytes;
        try {
            byte[] result = decoder.decode(encoder.encode(sourceBytes));
            System.out.println("result: " + new String(result));
            assertArrayEquals(expResult, result);
        } catch (IOException ex) {
            fail("LZ78ByteEncoder tuotti IOException-poikkeuksen.");
        }
    }
}
