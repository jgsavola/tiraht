/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiraht;

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
public class ByteArrayTest {
    private ByteArray byteArray16;
    private ByteArray byteArray17;

    public ByteArrayTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        byteArray16 = new ByteArray(16);
        byteArray17 = new ByteArray(17);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of hashCode method, of class ByteArray.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        assertEquals("Arrays with same content but different capacities hash to same code.",
                byteArray16.hashCode(), byteArray17.hashCode());
    }

    /**
     * Test of equals method, of class ByteArray.
     */
    @Test
    public void testEquals() {
        ByteArray byteArray32 = new ByteArray(32);
        ByteArray byteArray33 = new ByteArray(33);

        System.out.println("equals");
        assertTrue("Arrays with same content but different capacities are equal.",
                byteArray32.equals(byteArray33));
        byteArray32.add((byte)0x1);
        assertFalse("Arrays with different lengths are not equal.",
                byteArray32.equals(byteArray33));
        byteArray33.add((byte)0x1);
        assertTrue("Arrays with same content but different capacities are equal.",
                byteArray32.equals(byteArray33));
        byteArray32.add((byte)0x2);
        byteArray33.add((byte)0x3);
        assertFalse("Arrays with different contents are not equal.",
                byteArray32.equals(byteArray33));
    }
}
