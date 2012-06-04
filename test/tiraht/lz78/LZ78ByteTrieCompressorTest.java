package tiraht.lz78;

import tiraht.lz78.LZ78Token;
import tiraht.lz78.LZ78ByteTrieCompressor;
import tiraht.lz78.LZ78ToArrayListEncoder;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.*;

/**
 * Testaa LZ78ByteTrieCompressor-luokkaa.
 *
 * @author jgsavola
 */
public class LZ78ByteTrieCompressorTest {
    private String example1Str;
    private byte[] example1Bytes;
    private ArrayList<LZ78Token> example1ExpResult;

    private LZ78ByteTrieCompressor instance;
    private LZ78ToArrayListEncoder encoder;

    public LZ78ByteTrieCompressorTest() throws UnsupportedEncodingException {
        example1Str = "a date at a date";
        example1Bytes = example1Str.getBytes("US-ASCII");

        example1ExpResult = new ArrayList<LZ78Token>();
        example1ExpResult.add(new LZ78Token(0, (byte)0x61));
        example1ExpResult.add(new LZ78Token(0, (byte)0x20));
        example1ExpResult.add(new LZ78Token(0, (byte)0x64));
        example1ExpResult.add(new LZ78Token(1, (byte)0x74));
        example1ExpResult.add(new LZ78Token(0, (byte)0x65));
        example1ExpResult.add(new LZ78Token(2, (byte)0x61));
        example1ExpResult.add(new LZ78Token(0, (byte)0x74));
        example1ExpResult.add(new LZ78Token(6, (byte)0x20));
        example1ExpResult.add(new LZ78Token(3, (byte)0x61));
        example1ExpResult.add(new LZ78Token(7, (byte)0x65));
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        instance = new LZ78ByteTrieCompressor();
        encoder = new LZ78ToArrayListEncoder();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of compress method, of class LZ78ByteTrieCompressor.
     */
    @Test
    public void testCompress_InputStream_LZ78TokenWriter() throws Exception {
        System.out.println("compress(\"" + example1Str + "\"::InputStream)");

        /**
         * Testataan <code>InputStream</code>:n avulla.
         */
        ByteArrayInputStream is = new ByteArrayInputStream(example1Bytes);

        instance.compress(is, encoder);

        assertEquals("Kompressio tuottaa odotetut LZ78-koodit.",
                pairListToString(example1ExpResult),
                pairListToString(encoder.getTokens()));
    }

    /**
     * Test of compress method, of class LZ78ByteTrieCompressor.
     */
    @Test
    public void testCompress_byteArr_LZ78TokenWriter() throws Exception {
        System.out.println("compress(\"" + example1Str + "\"::byte[])");

        instance.compress(example1Bytes, encoder);

        assertEquals("Kompressio tuottaa odotetut LZ78-koodit.",
                pairListToString(example1ExpResult),
                pairListToString(encoder.getTokens()));
    }

    /**
     * Test of compress method, of class LZ78ByteTrieCompressor.
     */
    @Test
    public void testDictFillUpResetIllegalSize() throws Exception {
        String sourceStr = "a date with a date";
        byte[] sourceBytes = sourceStr.getBytes("US-ASCII");

        try {
            LZ78ByteTrieCompressor compressor = new LZ78ByteTrieCompressor(0, LZ78ByteTrieCompressor.DictFillUpStrategy.Reset);
            fail("Sanakirjan koko 0 tai < -1 on virhe");
        } catch (IllegalArgumentException ex) {
            assertTrue("Sanakirjan koko 0 tai < -1 on virhe", true);
        }
        try {
            LZ78ByteTrieCompressor compressor = new LZ78ByteTrieCompressor(-2, LZ78ByteTrieCompressor.DictFillUpStrategy.Reset);
            fail("Sanakirjan koko 0 tai < -1 on virhe");
        } catch (IllegalArgumentException ex) {
            assertTrue("Sanakirjan koko 0 tai < -1 on virhe", true);
        }
    }

    /**
     * Test of compress method, of class LZ78ByteTrieCompressor.
     */
    @Test
    public void testDictFillUpResetSize1() throws Exception {
        String sourceStr = "a date with a date";
        byte[] sourceBytes = sourceStr.getBytes("US-ASCII");

        {
            LZ78ByteTrieCompressor compressor = new LZ78ByteTrieCompressor(1, LZ78ByteTrieCompressor.DictFillUpStrategy.Reset);
            System.out.println("testDictFillUpResetSize1(\"" + sourceStr + "\"::byte[])");

            compressor.compress(sourceBytes, encoder);

            System.out.println(pairListToCode(encoder.getTokens()));

            assertEquals("Kompressio tuottaa odotetut LZ78-koodit.",
                    pairListToString(expectedWithResetAndDictSizes1And2()),
                    pairListToString(encoder.getTokens()));
        }
    }

    /**
     * Test of compress method, of class LZ78ByteTrieCompressor.
     */
    @Test
    public void testDictFillUpResetSize2() throws Exception {
        String sourceStr = "a date with a date";
        byte[] sourceBytes = sourceStr.getBytes("US-ASCII");

        {
            LZ78ByteTrieCompressor compressor = new LZ78ByteTrieCompressor(2, LZ78ByteTrieCompressor.DictFillUpStrategy.Reset);
            System.out.println("testDictFillUpResetSize2(\"" + sourceStr + "\"::byte[])");

            compressor.compress(sourceBytes, encoder);

            System.out.println(pairListToCode(encoder.getTokens()));

            assertEquals("Kompressio tuottaa odotetut LZ78-koodit.",
                    pairListToString(expectedWithResetAndDictSizes1And2()),
                    pairListToString(encoder.getTokens()));
        }
    }

    /**
     * Test of compress method, of class LZ78ByteTrieCompressor.
     */
    @Test
    public void testDictFillUpResetSize5() throws Exception {
        String sourceStr = "a date with a date";
        byte[] sourceBytes = sourceStr.getBytes("US-ASCII");

        {
            LZ78ByteTrieCompressor compressor = new LZ78ByteTrieCompressor(5, LZ78ByteTrieCompressor.DictFillUpStrategy.Reset);
            System.out.println("testDictFillUpResetSize5(\"" + sourceStr + "\"::byte[])");

            compressor.compress(sourceBytes, encoder);

            System.out.println(pairListToCode(encoder.getTokens()));

            assertEquals("Kompressio tuottaa odotetut LZ78-koodit.",
                    pairListToString(expectedWithResetAndDictSize5()),
                    pairListToString(encoder.getTokens()));
        }
    }

    /**
     * Test of compress method, of class LZ78ByteTrieCompressor.
     */
    @Test
    public void testDictFillUpResetSize6() throws Exception {
        String sourceStr = "a date with a date";
        byte[] sourceBytes = sourceStr.getBytes("US-ASCII");

        {
            LZ78ByteTrieCompressor compressor = new LZ78ByteTrieCompressor(6, LZ78ByteTrieCompressor.DictFillUpStrategy.Reset);
            System.out.println("testDictFillUpResetSize6(\"" + sourceStr + "\"::byte[])");

            compressor.compress(sourceBytes, encoder);

            System.out.println(pairListToCode(encoder.getTokens()));

            assertEquals("Kompressio tuottaa odotetut LZ78-koodit.",
                    pairListToString(expectedWithResetAndDictSize6()),
                    pairListToString(encoder.getTokens()));
        }
    }

    /**
     * Test of compress method, of class LZ78ByteTrieCompressor.
     */
    @Test
    public void testDictFillUpFreezeSize1() throws Exception {
        String sourceStr = "a date with a date";
        byte[] sourceBytes = sourceStr.getBytes("US-ASCII");

        {
            LZ78ByteTrieCompressor compressor = new LZ78ByteTrieCompressor(1, LZ78ByteTrieCompressor.DictFillUpStrategy.Freeze);
            System.out.println("testDictFillUpFreezeSize1(\"" + sourceStr + "\"::byte[])");

            compressor.compress(sourceBytes, encoder);

            System.out.println(pairListToCode(encoder.getTokens()));

            assertEquals("Kompressio tuottaa odotetut LZ78-koodit.",
                    pairListToString(expectedWithResetAndDictSizes1And2()),
                    pairListToString(encoder.getTokens()));
        }
    }

    /**
     * Test of compress method, of class LZ78ByteTrieCompressor.
     */
    @Test
    public void testDictFillUpFreezeSize2() throws Exception {
        String sourceStr = "a date with a date";
        byte[] sourceBytes = sourceStr.getBytes("US-ASCII");

        {
            LZ78ByteTrieCompressor compressor = new LZ78ByteTrieCompressor(2, LZ78ByteTrieCompressor.DictFillUpStrategy.Freeze);
            System.out.println("testDictFillUpFreezeSize2: compress(\"" + sourceStr + "\"::byte[])");

            compressor.compress(sourceBytes, encoder);

            System.out.println(pairListToCode(encoder.getTokens()));

            assertEquals("Kompressio tuottaa odotetut LZ78-koodit.",
                    pairListToString(expectedWithFreezeAndDictSize2()),
                    pairListToString(encoder.getTokens()));
        }
    }

    /**
     * Test of compress method, of class LZ78ByteTrieCompressor.
     */
    @Test
    public void testDictFillUpResetSize16() throws Exception {
        String sourceStr = "Alice was beginning to get very tired of sitting by her sister"
                + " on the bank, and of having nothing to do:  once or twice she had"
                + " peeped into the book her sister was reading, but it had no"
                + "pictures or conversations in it, `and what is the use of a book,'"
                + "thought Alice `without pictures or conversation?'";
        byte[] sourceBytes = sourceStr.getBytes("US-ASCII");

        {
            LZ78ByteTrieCompressor compressor = new LZ78ByteTrieCompressor(16, LZ78ByteTrieCompressor.DictFillUpStrategy.Reset);
            System.out.println("testDictFillUpResetSize16: compress(\"" + sourceStr + "\"::byte[])");

            compressor.compress(sourceBytes, encoder);

            System.out.println(pairListToCode(encoder.getTokens()));

            assertEquals("Kompressio tuottaa odotetut LZ78-koodit.",
                    pairListToString(expectedWithResetDictSize16()),
                    pairListToString(encoder.getTokens()));
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

    /**
     * Käytetään tuloslistojen vertailemiseen.
     *
     * @param tokens Kompression tuloslista.
     * @return Tekstimuotoinen esitys.
     */
    private String pairListToString(ArrayList<LZ78Token> tokens) {
        String result = "";
        for (LZ78Token token : tokens)
            result += token + "\n";

        return result;
    }

    /**
     * Käytetään testauskoodin generoimiseen.
     *
     * @param tokens Kompression tuloslista.
     * @return Tekstimuotoinen esitys.
     */
    private String pairListToCode(ArrayList<LZ78Token> tokens) {
        String result = "ArrayList<LZ78Token> tokens = new ArrayList<LZ78Token>();\n";
        for (LZ78Token token : tokens)
            result += "tokens.add(new LZ78Token(" + token.getPrefixIndex() + ", (byte)" + (int)token.getSuffixByte() + "));\n";

        return result;
    }
}
