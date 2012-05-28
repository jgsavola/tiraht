package tiraht.util;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author jgsavola
 */
public class GeneralUnaryEncoderTest {
    
    public GeneralUnaryEncoderTest() {
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
     * Test of encode method, of class GeneralUnaryEncoder.
     */
    @Test
    public void testEncode() {
        System.out.println("encode");

        int start = 3;
        int step  = 2;
        int stop  = 9;
        int numCodes = 680;
        GeneralUnaryEncoder encoder = new GeneralUnaryEncoder(start, step, stop);
        
        assertEquals("0|000", encoder.encode(0));
        assertEquals("0|111", encoder.encode(7));
        assertEquals("10|00000", encoder.encode(8));
        assertEquals("111|111111111", encoder.encode(679));
        for (int i = 0; i < numCodes; i++) {
            String result = encoder.encode(i);
            System.out.printf("%6d %s\n", i, result);
        }
    }
}
