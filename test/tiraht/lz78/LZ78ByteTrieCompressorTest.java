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
            System.out.println("compress(\"" + sourceStr + "\"::byte[])");

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
            System.out.println("compress(\"" + sourceStr + "\"::byte[])");

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
            System.out.println("compress(\"" + sourceStr + "\"::byte[])");

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
    public void testDictFillUpFreezeSize1() throws Exception {
        String sourceStr = "a date with a date";
        byte[] sourceBytes = sourceStr.getBytes("US-ASCII");

        {
            LZ78ByteTrieCompressor compressor = new LZ78ByteTrieCompressor(1, LZ78ByteTrieCompressor.DictFillUpStrategy.Freeze);
            System.out.println("compress(\"" + sourceStr + "\"::byte[])");

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
