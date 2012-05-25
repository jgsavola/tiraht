/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiraht.lz78;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Yksinkertainen tavukooderi LZ78-kompressoidulle datalle.
 *
 * @author jonne
 */
public class LZ78ByteStreamEncoder implements LZ78TokenWriter {
    private DataOutputStream dos;
    private int largestIndexSeen;

    public LZ78ByteStreamEncoder(OutputStream os) {
        this.dos = new DataOutputStream(os);
        largestIndexSeen = 0;
    }

    @Override
    public void writeLZ78Token(LZ78Token token) throws IOException {
        if (token.getPrefixIndex() > largestIndexSeen)
            largestIndexSeen = token.getPrefixIndex();

        /**
         * Hyvin suoraviivainen strategia: vaihdetaan pidempään koodiin
         * heti kun pakko.
         */
        if (largestIndexSeen < 0x100) {
            dos.writeByte(largestIndexSeen);
        } else if (largestIndexSeen < 0x10000) {
            dos.writeShort(largestIndexSeen);
        } else {
            dos.writeInt(token.getPrefixIndex());
        }
        dos.writeByte(token.getSuffixByte());
    }
}
