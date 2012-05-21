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
     * Test of add method, of class ByteArray.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        byte b = 0;
        ByteArray instance = new ByteArray();
        instance.add(b);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBytes method, of class ByteArray.
     */
    @Test
    public void testGetBytes() {
        System.out.println("getBytes");
        ByteArray instance = new ByteArray();
        byte[] expResult = null;
        byte[] result = instance.getBytes();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCapacity method, of class ByteArray.
     */
    @Test
    public void testGetCapacity() {
        System.out.println("getCapacity");
        ByteArray instance = new ByteArray();
        int expResult = 0;
        int result = instance.getCapacity();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of length method, of class ByteArray.
     */
    @Test
    public void testLength() {
        System.out.println("length");
        ByteArray instance = new ByteArray();
        int expResult = 0;
        int result = instance.length();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
