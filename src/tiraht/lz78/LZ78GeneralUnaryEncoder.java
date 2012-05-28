package tiraht.lz78;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import tiraht.util.GeneralUnaryEncoder;

/**
 * Testaa koodaamista "General Unary" -koodilla.
 *
 * Lähde: Salomon, David: Data Compression. The Complete Reference.
 *
 * @author jgsavola
 */
public class LZ78GeneralUnaryEncoder implements LZ78TokenWriter {
    private GeneralUnaryEncoder unaryEncoder;
    private OutputStream os;
    private long sumBits = 0;
    
    public LZ78GeneralUnaryEncoder(OutputStream os, int start, int step, int stop) {
        this.os = os;
        unaryEncoder = new GeneralUnaryEncoder(start, step, stop);
    }

    @Override
    public void writeLZ78Token(LZ78Token token) throws IOException {
        String u = unaryEncoder.encode(token.getPrefixIndex());
        sumBits += u.length() - 1 + 8;
        String s = u + "\t(" + token.getSuffixByte() + ")\t" + token.getPrefixIndex() + " len: " + (u.length() - 1) + ", sum: " + (sumBits / 8) + "\n";
        try {
            os.write(s.getBytes("US-ASCII"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LZ78GeneralUnaryEncoder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
