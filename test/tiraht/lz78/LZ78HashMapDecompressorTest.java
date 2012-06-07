package tiraht.lz78;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.*;
import tiraht.lz78.LZ78Compressor.DictFillUpStrategy;

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

    /**
     * Test of decompress method, of class LZ78HashMapDecompressor.
     */
    @Test
    public void testDecompressWithResetAndDictSize1() throws UnsupportedEncodingException {
        System.out.println("decompress");

        {
            String sourceStr = "a date with a date";
            LZ78HashMapDecompressor decompressor1 = new LZ78HashMapDecompressor(1, DictFillUpStrategy.Reset);
            decoder = new LZ78FromIteratorDecoder(expectedWithResetAndDictSizes1And2().iterator());

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                decompressor1.decompress(decoder, os);
            } catch (IOException ex) {
                fail("IOException purkaessa kompressiota.");
            }

            byte[] result = os.toByteArray();
            assertArrayEquals("Dekompressio toimii.", sourceStr.getBytes("US-ASCII"), result);
        }
    }

    /**
     * Test of decompress method, of class LZ78HashMapDecompressor.
     */
    @Test
    public void testDecompressWithResetAndDictSize6() throws UnsupportedEncodingException {
        System.out.println("testDecompressWithResetAndDictSize6");

        {
            String sourceStr = "a date with a date";
            LZ78HashMapDecompressor decompressor6 = new LZ78HashMapDecompressor(6, DictFillUpStrategy.Reset);
            decoder = new LZ78FromIteratorDecoder(expectedWithResetAndDictSize6().iterator());

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                decompressor6.decompress(decoder, os);
            } catch (IOException ex) {
                fail("IOException purkaessa kompressiota.");
            }

            byte[] result = os.toByteArray();
            assertEquals("Dekompressio toimii.", sourceStr, new String(result, "US-ASCII"));
        }
    }

    /**
     * Test of decompress method, of class LZ78HashMapDecompressor.
     */
    @Test
    public void testDecompressWithResetAndDictSize16() throws UnsupportedEncodingException {
        System.out.println("testDecompressWithResetAndDictSize16");

        {
            String sourceStr = "Alice was beginning to get very tired of sitting by her sister"
                + " on the bank, and of having nothing to do:  once or twice she had"
                + " peeped into the book her sister was reading, but it had no"
                + "pictures or conversations in it, `and what is the use of a book,'"
                + "thought Alice `without pictures or conversation?'";
            LZ78HashMapDecompressor decompressor6 = new LZ78HashMapDecompressor(16, DictFillUpStrategy.Reset);
            decoder = new LZ78FromIteratorDecoder(expectedWithResetDictSize16().iterator());

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                decompressor6.decompress(decoder, os);
            } catch (IOException ex) {
                fail("IOException purkaessa kompressiota.");
            }

            byte[] result = os.toByteArray();
            assertEquals("Dekompressio toimii.", sourceStr, new String(result, "US-ASCII"));
        }
    }

    /**
     * Test of decompress method, of class LZ78HashMapDecompressor.
     */
    @Test
    public void testDecompressWithResetAndDictSize2() throws UnsupportedEncodingException {
        System.out.println("decompress");

        {
            String sourceStr = "a date with a date";
            LZ78HashMapDecompressor decompressor2 = new LZ78HashMapDecompressor(2, DictFillUpStrategy.Reset);
            decoder = new LZ78FromIteratorDecoder(expectedWithResetAndDictSizes1And2().iterator());

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                decompressor2.decompress(decoder, os);
            } catch (IOException ex) {
                fail("IOException purkaessa kompressiota.");
            }

            byte[] result = os.toByteArray();
            assertArrayEquals("Dekompressio toimii.", sourceStr.getBytes("US-ASCII"), result);
        }
    }

    private ArrayList<LZ78Token> expectedWithResetAndDictSizes1And2() {
        ArrayList<LZ78Token> tokens = new ArrayList<LZ78Token>();
        tokens.add(new LZ78Token(0, (byte)97));
        tokens.add(new LZ78Token(0, (byte)32));
        tokens.add(new LZ78Token(0, (byte)100));
        tokens.add(new LZ78Token(0, (byte)97));
        tokens.add(new LZ78Token(0, (byte)116));
        tokens.add(new LZ78Token(0, (byte)101));
        tokens.add(new LZ78Token(0, (byte)32));
        tokens.add(new LZ78Token(0, (byte)119));
        tokens.add(new LZ78Token(0, (byte)105));
        tokens.add(new LZ78Token(0, (byte)116));
        tokens.add(new LZ78Token(0, (byte)104));
        tokens.add(new LZ78Token(0, (byte)32));
        tokens.add(new LZ78Token(0, (byte)97));
        tokens.add(new LZ78Token(0, (byte)32));
        tokens.add(new LZ78Token(0, (byte)100));
        tokens.add(new LZ78Token(0, (byte)97));
        tokens.add(new LZ78Token(0, (byte)116));
        tokens.add(new LZ78Token(0, (byte)101));

        return tokens;
    }

    private ArrayList<LZ78Token> expectedWithResetAndDictSize5() {
        ArrayList<LZ78Token> tokens = new ArrayList<LZ78Token>();
        tokens.add(new LZ78Token(0, (byte)97));
        tokens.add(new LZ78Token(0, (byte)32));
        tokens.add(new LZ78Token(0, (byte)100));
        tokens.add(new LZ78Token(1, (byte)116));
        tokens.add(new LZ78Token(0, (byte)101));
        tokens.add(new LZ78Token(0, (byte)32));
        tokens.add(new LZ78Token(0, (byte)119));
        tokens.add(new LZ78Token(0, (byte)105));
        tokens.add(new LZ78Token(0, (byte)116));
        tokens.add(new LZ78Token(0, (byte)104));
        tokens.add(new LZ78Token(0, (byte)32));
        tokens.add(new LZ78Token(0, (byte)97));
        tokens.add(new LZ78Token(0, (byte)32));
        tokens.add(new LZ78Token(0, (byte)100));
        tokens.add(new LZ78Token(0, (byte)97));
        tokens.add(new LZ78Token(0, (byte)116));
        tokens.add(new LZ78Token(0, (byte)101));

        return tokens;
    }

    private ArrayList<LZ78Token> expectedWithResetAndDictSize6() {
        ArrayList<LZ78Token> tokens = new ArrayList<LZ78Token>();
        tokens.add(new LZ78Token(0, (byte)97));
        tokens.add(new LZ78Token(0, (byte)32));
        tokens.add(new LZ78Token(0, (byte)100));
        tokens.add(new LZ78Token(1, (byte)116));
        tokens.add(new LZ78Token(0, (byte)101));
        tokens.add(new LZ78Token(0, (byte)32));
        tokens.add(new LZ78Token(0, (byte)119));
        tokens.add(new LZ78Token(0, (byte)105));
        tokens.add(new LZ78Token(0, (byte)116));
        tokens.add(new LZ78Token(0, (byte)104));
        tokens.add(new LZ78Token(0, (byte)32));
        tokens.add(new LZ78Token(0, (byte)97));
        tokens.add(new LZ78Token(1, (byte)100));
        tokens.add(new LZ78Token(2, (byte)116));
        tokens.add(new LZ78Token(0, (byte)101));

        return tokens;
    }

    private ArrayList<LZ78Token> expectedWithResetDictSize16() {
        ArrayList<LZ78Token> tokens = new ArrayList<LZ78Token>();
        tokens.add(new LZ78Token(0, (byte)65));
        tokens.add(new LZ78Token(0, (byte)108));
        tokens.add(new LZ78Token(0, (byte)105));
        tokens.add(new LZ78Token(0, (byte)99));
        tokens.add(new LZ78Token(0, (byte)101));
        tokens.add(new LZ78Token(0, (byte)32));
        tokens.add(new LZ78Token(0, (byte)119));
        tokens.add(new LZ78Token(0, (byte)97));
        tokens.add(new LZ78Token(0, (byte)115));
        tokens.add(new LZ78Token(6, (byte)98));
        tokens.add(new LZ78Token(5, (byte)103));
        tokens.add(new LZ78Token(3, (byte)110));
        tokens.add(new LZ78Token(0, (byte)110));
        tokens.add(new LZ78Token(12, (byte)103));
        tokens.add(new LZ78Token(6, (byte)116));
        tokens.add(new LZ78Token(0, (byte)111));
        tokens.add(new LZ78Token(0, (byte)32));
        tokens.add(new LZ78Token(0, (byte)103));
        tokens.add(new LZ78Token(0, (byte)101));
        tokens.add(new LZ78Token(0, (byte)116));
        tokens.add(new LZ78Token(2, (byte)118));
        tokens.add(new LZ78Token(4, (byte)114));
        tokens.add(new LZ78Token(0, (byte)121));
        tokens.add(new LZ78Token(2, (byte)116));
        tokens.add(new LZ78Token(0, (byte)105));
        tokens.add(new LZ78Token(0, (byte)114));
        tokens.add(new LZ78Token(4, (byte)100));
        tokens.add(new LZ78Token(2, (byte)111));
        tokens.add(new LZ78Token(0, (byte)102));
        tokens.add(new LZ78Token(2, (byte)115));
        tokens.add(new LZ78Token(0, (byte)105));
        tokens.add(new LZ78Token(0, (byte)116));
        tokens.add(new LZ78Token(2, (byte)105));
        tokens.add(new LZ78Token(0, (byte)110));
        tokens.add(new LZ78Token(0, (byte)103));
        tokens.add(new LZ78Token(0, (byte)32));
        tokens.add(new LZ78Token(0, (byte)98));
        tokens.add(new LZ78Token(0, (byte)121));
        tokens.add(new LZ78Token(6, (byte)104));
        tokens.add(new LZ78Token(0, (byte)101));
        tokens.add(new LZ78Token(0, (byte)114));
        tokens.add(new LZ78Token(6, (byte)115));
        tokens.add(new LZ78Token(1, (byte)115));
        tokens.add(new LZ78Token(2, (byte)101));
        tokens.add(new LZ78Token(11, (byte)32));
        tokens.add(new LZ78Token(0, (byte)111));
        tokens.add(new LZ78Token(0, (byte)110));
        tokens.add(new LZ78Token(0, (byte)32));
        tokens.add(new LZ78Token(0, (byte)116));
        tokens.add(new LZ78Token(0, (byte)104));
        tokens.add(new LZ78Token(0, (byte)101));
        tokens.add(new LZ78Token(3, (byte)98));
        tokens.add(new LZ78Token(0, (byte)97));
        tokens.add(new LZ78Token(2, (byte)107));
        tokens.add(new LZ78Token(0, (byte)44));
        tokens.add(new LZ78Token(3, (byte)97));
        tokens.add(new LZ78Token(2, (byte)100));
        tokens.add(new LZ78Token(3, (byte)111));
        tokens.add(new LZ78Token(0, (byte)102));
        tokens.add(new LZ78Token(3, (byte)104));
        tokens.add(new LZ78Token(0, (byte)97));
        tokens.add(new LZ78Token(0, (byte)118));
        tokens.add(new LZ78Token(0, (byte)105));
        tokens.add(new LZ78Token(0, (byte)110));
        tokens.add(new LZ78Token(0, (byte)103));
        tokens.add(new LZ78Token(0, (byte)32));
        tokens.add(new LZ78Token(4, (byte)111));
        tokens.add(new LZ78Token(0, (byte)116));
        tokens.add(new LZ78Token(0, (byte)104));
        tokens.add(new LZ78Token(3, (byte)110));
        tokens.add(new LZ78Token(5, (byte)32));
        tokens.add(new LZ78Token(8, (byte)111));
        tokens.add(new LZ78Token(6, (byte)100));
        tokens.add(new LZ78Token(0, (byte)111));
        tokens.add(new LZ78Token(0, (byte)58));
        tokens.add(new LZ78Token(0, (byte)32));
        tokens.add(new LZ78Token(1, (byte)111));
        tokens.add(new LZ78Token(0, (byte)110));
        tokens.add(new LZ78Token(0, (byte)99));
        tokens.add(new LZ78Token(0, (byte)101));
        tokens.add(new LZ78Token(2, (byte)114));
        tokens.add(new LZ78Token(1, (byte)116));
        tokens.add(new LZ78Token(0, (byte)119));
        tokens.add(new LZ78Token(0, (byte)105));
        tokens.add(new LZ78Token(4, (byte)101));
        tokens.add(new LZ78Token(1, (byte)115));
        tokens.add(new LZ78Token(0, (byte)104));
        tokens.add(new LZ78Token(5, (byte)32));
        tokens.add(new LZ78Token(12, (byte)97));
        tokens.add(new LZ78Token(0, (byte)100));
        tokens.add(new LZ78Token(0, (byte)32));
        tokens.add(new LZ78Token(0, (byte)112));
        tokens.add(new LZ78Token(0, (byte)101));
        tokens.add(new LZ78Token(3, (byte)112));
        tokens.add(new LZ78Token(3, (byte)100));
        tokens.add(new LZ78Token(1, (byte)105));
        tokens.add(new LZ78Token(0, (byte)110));
        tokens.add(new LZ78Token(0, (byte)116));
        tokens.add(new LZ78Token(0, (byte)111));
        tokens.add(new LZ78Token(1, (byte)116));
        tokens.add(new LZ78Token(0, (byte)104));
        tokens.add(new LZ78Token(3, (byte)32));
        tokens.add(new LZ78Token(0, (byte)98));
        tokens.add(new LZ78Token(9, (byte)111));
        tokens.add(new LZ78Token(0, (byte)107));
        tokens.add(new LZ78Token(0, (byte)32));
        tokens.add(new LZ78Token(0, (byte)104));
        tokens.add(new LZ78Token(0, (byte)101));
        tokens.add(new LZ78Token(0, (byte)114));
        tokens.add(new LZ78Token(1, (byte)115));
        tokens.add(new LZ78Token(0, (byte)105));
        tokens.add(new LZ78Token(0, (byte)115));
        tokens.add(new LZ78Token(0, (byte)116));
        tokens.add(new LZ78Token(3, (byte)114));
        tokens.add(new LZ78Token(1, (byte)119));
        tokens.add(new LZ78Token(0, (byte)97));
        tokens.add(new LZ78Token(7, (byte)32));
        tokens.add(new LZ78Token(4, (byte)101));
        tokens.add(new LZ78Token(11, (byte)100));
        tokens.add(new LZ78Token(6, (byte)110));
        tokens.add(new LZ78Token(0, (byte)103));
        tokens.add(new LZ78Token(0, (byte)44));
        tokens.add(new LZ78Token(0, (byte)32));
        tokens.add(new LZ78Token(0, (byte)98));
        tokens.add(new LZ78Token(0, (byte)117));
        tokens.add(new LZ78Token(0, (byte)116));
        tokens.add(new LZ78Token(3, (byte)105));
        tokens.add(new LZ78Token(6, (byte)32));
        tokens.add(new LZ78Token(0, (byte)104));
        tokens.add(new LZ78Token(0, (byte)97));
        tokens.add(new LZ78Token(0, (byte)100));
        tokens.add(new LZ78Token(3, (byte)110));
        tokens.add(new LZ78Token(0, (byte)111));
        tokens.add(new LZ78Token(0, (byte)112));
        tokens.add(new LZ78Token(0, (byte)105));
        tokens.add(new LZ78Token(0, (byte)99));
        tokens.add(new LZ78Token(0, (byte)116));
        tokens.add(new LZ78Token(0, (byte)117));
        tokens.add(new LZ78Token(0, (byte)114));
        tokens.add(new LZ78Token(0, (byte)101));
        tokens.add(new LZ78Token(0, (byte)115));
        tokens.add(new LZ78Token(0, (byte)32));
        tokens.add(new LZ78Token(0, (byte)111));
        tokens.add(new LZ78Token(4, (byte)32));
        tokens.add(new LZ78Token(1, (byte)111));
        tokens.add(new LZ78Token(0, (byte)110));
        tokens.add(new LZ78Token(0, (byte)118));
        tokens.add(new LZ78Token(5, (byte)114));
        tokens.add(new LZ78Token(6, (byte)97));
        tokens.add(new LZ78Token(2, (byte)105));
        tokens.add(new LZ78Token(0, (byte)111));
        tokens.add(new LZ78Token(0, (byte)110));
        tokens.add(new LZ78Token(0, (byte)115));
        tokens.add(new LZ78Token(0, (byte)32));
        tokens.add(new LZ78Token(0, (byte)105));
        tokens.add(new LZ78Token(2, (byte)32));
        tokens.add(new LZ78Token(5, (byte)116));
        tokens.add(new LZ78Token(0, (byte)44));
        tokens.add(new LZ78Token(4, (byte)96));
        tokens.add(new LZ78Token(0, (byte)97));
        tokens.add(new LZ78Token(2, (byte)100));
        tokens.add(new LZ78Token(4, (byte)119));
        tokens.add(new LZ78Token(0, (byte)104));
        tokens.add(new LZ78Token(10, (byte)116));
        tokens.add(new LZ78Token(4, (byte)105));
        tokens.add(new LZ78Token(0, (byte)115));
        tokens.add(new LZ78Token(0, (byte)32));
        tokens.add(new LZ78Token(0, (byte)116));
        tokens.add(new LZ78Token(0, (byte)104));
        tokens.add(new LZ78Token(0, (byte)101));
        tokens.add(new LZ78Token(2, (byte)117));
        tokens.add(new LZ78Token(1, (byte)101));
        tokens.add(new LZ78Token(2, (byte)111));
        tokens.add(new LZ78Token(0, (byte)102));
        tokens.add(new LZ78Token(2, (byte)97));
        tokens.add(new LZ78Token(2, (byte)98));
        tokens.add(new LZ78Token(0, (byte)111));
        tokens.add(new LZ78Token(12, (byte)107));
        tokens.add(new LZ78Token(0, (byte)44));
        tokens.add(new LZ78Token(0, (byte)39));
        tokens.add(new LZ78Token(0, (byte)116));
        tokens.add(new LZ78Token(0, (byte)104));
        tokens.add(new LZ78Token(0, (byte)111));
        tokens.add(new LZ78Token(0, (byte)117));
        tokens.add(new LZ78Token(0, (byte)103));
        tokens.add(new LZ78Token(2, (byte)116));
        tokens.add(new LZ78Token(0, (byte)32));
        tokens.add(new LZ78Token(0, (byte)65));
        tokens.add(new LZ78Token(0, (byte)108));
        tokens.add(new LZ78Token(0, (byte)105));
        tokens.add(new LZ78Token(0, (byte)99));
        tokens.add(new LZ78Token(0, (byte)101));
        tokens.add(new LZ78Token(7, (byte)96));
        tokens.add(new LZ78Token(0, (byte)119));
        tokens.add(new LZ78Token(10, (byte)116));
        tokens.add(new LZ78Token(0, (byte)104));
        tokens.add(new LZ78Token(0, (byte)111));
        tokens.add(new LZ78Token(0, (byte)117));
        tokens.add(new LZ78Token(0, (byte)116));
        tokens.add(new LZ78Token(0, (byte)32));
        tokens.add(new LZ78Token(0, (byte)112));
        tokens.add(new LZ78Token(0, (byte)105));
        tokens.add(new LZ78Token(0, (byte)99));
        tokens.add(new LZ78Token(4, (byte)117));
        tokens.add(new LZ78Token(0, (byte)114));
        tokens.add(new LZ78Token(0, (byte)101));
        tokens.add(new LZ78Token(0, (byte)115));
        tokens.add(new LZ78Token(5, (byte)111));
        tokens.add(new LZ78Token(10, (byte)32));
        tokens.add(new LZ78Token(8, (byte)111));
        tokens.add(new LZ78Token(0, (byte)110));
        tokens.add(new LZ78Token(0, (byte)118));
        tokens.add(new LZ78Token(0, (byte)101));
        tokens.add(new LZ78Token(0, (byte)114));
        tokens.add(new LZ78Token(0, (byte)115));
        tokens.add(new LZ78Token(0, (byte)97));
        tokens.add(new LZ78Token(0, (byte)116));
        tokens.add(new LZ78Token(0, (byte)105));
        tokens.add(new LZ78Token(0, (byte)111));
        tokens.add(new LZ78Token(1, (byte)63));
        tokens.add(new LZ78Token(0, (byte)39));
        return tokens;
    }

    private ArrayList<LZ78Token> expectedWithFreezeAndDictSize2() {
        ArrayList<LZ78Token> tokens = new ArrayList<LZ78Token>();
        tokens.add(new LZ78Token(0, (byte)97));
        tokens.add(new LZ78Token(0, (byte)32));
        tokens.add(new LZ78Token(0, (byte)100));
        tokens.add(new LZ78Token(1, (byte)116));
        tokens.add(new LZ78Token(0, (byte)101));
        tokens.add(new LZ78Token(0, (byte)32));
        tokens.add(new LZ78Token(0, (byte)119));
        tokens.add(new LZ78Token(0, (byte)105));
        tokens.add(new LZ78Token(0, (byte)116));
        tokens.add(new LZ78Token(0, (byte)104));
        tokens.add(new LZ78Token(0, (byte)32));
        tokens.add(new LZ78Token(1, (byte)32));
        tokens.add(new LZ78Token(0, (byte)100));
        tokens.add(new LZ78Token(1, (byte)116));
        tokens.add(new LZ78Token(0, (byte)101));

        return tokens;
    }
}
