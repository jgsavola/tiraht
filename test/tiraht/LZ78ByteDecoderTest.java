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
public class LZ78ByteDecoderTest {
    public LZ78ByteDecoderTest() {
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
     * Test of decode method, of class LZ78ByteDecoder.
     */
    @Test
    public void testDecode() {
        System.out.println("decode");
        byte[] sourceBytes = "a date at a date".getBytes();
        LZ78ByteEncoder encoder = new LZ78ByteEncoder();
        LZ78ByteDecoder decoder = new LZ78ByteDecoder();
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
