package tiraht.util;

import java.io.ByteArrayInputStream;
import static org.junit.Assert.assertEquals;
import org.junit.*;

/**
 * Testaa <code>BitInputStream</code>-luokkaa.
 *
 * @author jgsavola
 */
public class BitInputStreamTest {
    public BitInputStreamTest() {
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
     * Test of readBit method, of class BitInputStream.
     */
    @Test
    public void testReadBit() throws Exception {
        System.out.println("readBit");

        byte[] buffer = {(byte)0xaa};
        ByteArrayInputStream bis = new ByteArrayInputStream(buffer);

        boolean[] expected = {true, false, true, false,
                              true, false, true, false};

        BitInputStream instance = new BitInputStream(bis);
        for (boolean expectedBit : expected) {
            assertEquals("Bittivirran lukeminen onnistuu.", expectedBit, instance.readBit());
        }
    }
}
