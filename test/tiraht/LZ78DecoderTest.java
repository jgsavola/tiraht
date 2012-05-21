/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiraht;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author jgsavola
 */
public class LZ78DecoderTest {
    
    public LZ78DecoderTest() {
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
     * Test of decode method, of class LZ78Decoder.
     */
    @Test
    public void testDecode() {
        System.out.println("decode");
        String sourceStr = "a date at a date";
        LZ78Encoder encoder = new LZ78Encoder();
        LZ78Decoder decoder = new LZ78Decoder();
        String expResult = sourceStr;
        String result;
        try {
            result = decoder.decode(encoder.encode(sourceStr));
            assertEquals(expResult, result);
        } catch (IOException ex) {
            fail("LZ78Encoder tuotti IOException-poikkeuksen.");
        }
    }
}
