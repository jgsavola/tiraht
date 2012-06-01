/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiraht.util;

import java.io.ByteArrayOutputStream;
import static org.junit.Assert.assertArrayEquals;
import org.junit.*;

/**
 * Testaa "General Unary"-koodausta.
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
    public void testEncode() throws Exception {
        System.out.println("encode");

        {
            /**
             * (4, 1, 4): 1111(0000)
             */
            byte[] expected = {(byte)(0xf << 4)};
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            BitOutputStream bs = new BitOutputStream(bos);
            GeneralUnaryEncoder encoder = new GeneralUnaryEncoder(bs, 4, 1, 4);
            encoder.encode(0xf);
            bs.flush();
            assertArrayEquals("Bittikoodaus(4, 1, 4) toimii.", expected, bos.toByteArray());
        }

        {
            /**
             * (4, 1, 5): 01111(000)
             */
            byte[] expected = {(byte)(0xf << 3)};
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            BitOutputStream bs = new BitOutputStream(bos);
            GeneralUnaryEncoder encoder = new GeneralUnaryEncoder(bs, 4, 1, 5);
            encoder.encode(0xf);
            bs.flush();
            assertArrayEquals("Bittikoodaus(4, 1, 5) toimii.", expected, bos.toByteArray());
        }
    }
}
