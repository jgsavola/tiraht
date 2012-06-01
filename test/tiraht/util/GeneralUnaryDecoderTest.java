package tiraht.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import static org.junit.Assert.assertEquals;
import org.junit.*;

/**
 * Testaa <code>GeneralUnaryDecoder</code>-luokkaa.
 *
 * @author jgsavola
 */
public class GeneralUnaryDecoderTest {
    public GeneralUnaryDecoderTest() {
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
     * Test of decode method, of class GeneralUnaryDecoder.
     */
    @Test
    public void testDecode() throws Exception {
        System.out.println("decode");
        {
            /**
             * (4, 1, 4): 1111(0000)
             */
            byte[] encodedData = {(byte)(0xf << 4)};
            ByteArrayInputStream bis = new ByteArrayInputStream(encodedData);
            BitInputStream bs = new BitInputStream(bis);
            GeneralUnaryDecoder decoder = new GeneralUnaryDecoder(bs, 4, 1, 4);
            int result = decoder.decode();
            assertEquals("Bittidekoodaus(4, 1, 4) toimii.", 0xf, result);
        }

        {
            /**
             * (4, 1, 5): 01111(000)
             */
            byte[] encodedData = {(byte)(0xf << 3)};
            ByteArrayInputStream bis = new ByteArrayInputStream(encodedData);
            BitInputStream bs = new BitInputStream(bis);
            GeneralUnaryDecoder decoder = new GeneralUnaryDecoder(bs, 4, 1, 5);
            int result = decoder.decode();
            assertEquals("Bittidekoodaus(4, 1, 5) toimii.", 0xf, result);
        }

        {
            /**
             * Luo koodaaja joillakin parametreilla ja koodaa kaikki koodausfunktion
             * määrittelyjoukon alkiot. Pura saatu tavuvirta ja tarkista, että
             * kaikki alkiot säilyvät.
             */
            int start = 3;
            int step = 1;
            int stop = 16;

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BitOutputStream bos = new BitOutputStream(baos);
            GeneralUnaryEncoder encoder = new GeneralUnaryEncoder(bos, start, step, stop);
            for (int n = 0; n < encoder.getNumberOfDifferentCodes(); n++)
                encoder.encode(n);
            bos.flush();

            ByteArrayInputStream bis = new ByteArrayInputStream(baos.toByteArray());
            BitInputStream bs = new BitInputStream(bis);
            GeneralUnaryDecoder decoder = new GeneralUnaryDecoder(bs, start, step, stop);
            for (int expected = 0; expected < encoder.getNumberOfDifferentCodes(); expected++) {
                int result = decoder.decode();
                assertEquals("Bittidekoodaus toimii koko koodiavaruudelle.", expected, result);
            }
        }

    }
}
