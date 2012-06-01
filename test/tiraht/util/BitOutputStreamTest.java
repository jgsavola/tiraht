/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiraht.util;

import java.io.ByteArrayOutputStream;
import static org.junit.Assert.assertArrayEquals;
import org.junit.*;

/**
 *
 * @author jgsavola
 */
public class BitOutputStreamTest {
    public BitOutputStreamTest() {
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
     * Test of writeBit method, of class BitOutputStream.
     */
    @Test
    public void testWriteBit() throws Exception {
        System.out.println("writeBit");
        byte[] expected = {(byte)0xaa};
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BitOutputStream instance = new BitOutputStream(bos);

        instance.writeBit(true);
        instance.writeBit(false);
        instance.writeBit(true);
        instance.writeBit(false);
        instance.writeBit(true);
        instance.writeBit(false);
        instance.writeBit(true);
        instance.writeBit(false);
        instance.flush();

        assertArrayEquals("Bittien kirjoittaminen toimii.", expected, bos.toByteArray());
    }

    /**
     * Test of flush method, of class BitOutputStream.
     */
    @Test
    public void testFlush() throws Exception {
        System.out.println("flush");
        byte[] expected = {(byte)0xa0};
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BitOutputStream instance = new BitOutputStream(bos);

        instance.writeBit(true);
        instance.writeBit(false);
        instance.writeBit(true);
        instance.writeBit(false);
        instance.flush();

        assertArrayEquals("flush-metodi toimii.", expected, bos.toByteArray());
    }
}
