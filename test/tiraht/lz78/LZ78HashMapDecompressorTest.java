package tiraht.lz78;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;
import org.junit.*;

/**
 * Testaa kompressoidun datan purkamista.
 *
 * @author jonne
 */
public class LZ78HashMapDecompressorTest {
    private String example1Str = "a date with a date";
    private byte[] example1Bytes;

    private LZ78ByteTrieCompressor compressor;
    private LZ78ToArrayListEncoder encoder;
    private LZ78HashMapDecompressor decompressor;
    private LZ78FromIteratorDecoder decoder;

    public LZ78HashMapDecompressorTest() throws UnsupportedEncodingException {
        example1Bytes = example1Str.getBytes("US-ASCII");
        compressor = new LZ78ByteTrieCompressor();
        encoder = new LZ78ToArrayListEncoder();
        decompressor = new LZ78HashMapDecompressor();
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
     * Test of decode method, of class LZ78HashMapDecompressor.
     */
    @Test
    public void testDecompress() {
        System.out.println("decode");

        try {
            compressor.compress(example1Bytes, encoder);
        } catch (IOException ex) {
            fail("IOException kompressoidessa.");
        }

        decoder = new LZ78FromIteratorDecoder(encoder.getTokens().iterator());
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            decompressor.decompress(decoder, os);
        } catch (IOException ex) {
            fail("IOException kompressoidessa.");
        }

        byte[] result = os.toByteArray();
        assertArrayEquals("Dekompressio toimii.", example1Bytes, result);
    }
}
