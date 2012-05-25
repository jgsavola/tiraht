package tiraht.lz78;

import tiraht.lz78.LZ78Token;
import tiraht.lz78.LZ78ByteTrieCompressor;
import tiraht.lz78.LZ78ToArrayListEncoder;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
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
}
