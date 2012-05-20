/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiraht;

import java.io.StringReader;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jonne
 */
public class LZ78EncoderTest {

    public LZ78EncoderTest() {
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
     * Test of encode method, of class LZ78Encoder.
     */
    @Test
    public void testEncode_String1() throws Exception {
        String source = "a date at a date";
        System.out.println("encode(\"" + source + "\")");
        LZ78Encoder instance = new LZ78Encoder();
        ArrayList expResult = new ArrayList<PairToken>();
        expResult.add(new PairToken(0, 'a'));
        expResult.add(new PairToken(0, ' '));
        expResult.add(new PairToken(0, 'd'));
        expResult.add(new PairToken(1, 't'));
        expResult.add(new PairToken(0, 'e'));
        expResult.add(new PairToken(2, 'a'));
        expResult.add(new PairToken(0, 't'));
        expResult.add(new PairToken(6, ' '));
        expResult.add(new PairToken(3, 'a'));
        expResult.add(new PairToken(7, 'e'));
        ArrayList result = instance.encode(source);
        System.out.println(pairTokenListToString(expResult));
        System.out.println(pairTokenListToString(result));
        assertEquals(pairTokenListToString(expResult), pairTokenListToString(result));
    }

    /**
     * Test of encode method, of class LZ78Encoder.
     */
    @Test
    public void testEncode_String2() throws Exception {
        String source = "a date at a datea";
        System.out.println("encode(\"" + source + "\")");
        LZ78Encoder instance = new LZ78Encoder();
        ArrayList expResult = new ArrayList<PairToken>();
        expResult.add(new PairToken(0, 'a'));
        expResult.add(new PairToken(0, ' '));
        expResult.add(new PairToken(0, 'd'));
        expResult.add(new PairToken(1, 't'));
        expResult.add(new PairToken(0, 'e'));
        expResult.add(new PairToken(2, 'a'));
        expResult.add(new PairToken(0, 't'));
        expResult.add(new PairToken(6, ' '));
        expResult.add(new PairToken(3, 'a'));
        expResult.add(new PairToken(7, 'e'));
        expResult.add(new PairToken(0, 'a'));
        ArrayList result = instance.encode(source);
        System.out.println(pairTokenListToString(expResult));
        System.out.println(pairTokenListToString(result));
        assertEquals(pairTokenListToString(expResult), pairTokenListToString(result));
    }

    /**
     * Test of encode method, of class LZ78Encoder.
     */
    @Test
    public void testEncode_String3() throws Exception {
        String source = "a date at a datea ";
        System.out.println("encode(\"" + source + "\")");
        LZ78Encoder instance = new LZ78Encoder();
        ArrayList expResult = new ArrayList<PairToken>();
        expResult.add(new PairToken(0, 'a'));
        expResult.add(new PairToken(0, ' '));
        expResult.add(new PairToken(0, 'd'));
        expResult.add(new PairToken(1, 't'));
        expResult.add(new PairToken(0, 'e'));
        expResult.add(new PairToken(2, 'a'));
        expResult.add(new PairToken(0, 't'));
        expResult.add(new PairToken(6, ' '));
        expResult.add(new PairToken(3, 'a'));
        expResult.add(new PairToken(7, 'e'));
        expResult.add(new PairToken(1, ' '));
        ArrayList result = instance.encode(source);
        System.out.println(pairTokenListToString(expResult));
        System.out.println(pairTokenListToString(result));
        assertEquals(pairTokenListToString(expResult), pairTokenListToString(result));
    }

    String pairTokenListToString(ArrayList<PairToken> tokens) {
        String result = "";
        for (PairToken token : tokens) {
            result += token + "\n";
        }

        return result;
    }
}
