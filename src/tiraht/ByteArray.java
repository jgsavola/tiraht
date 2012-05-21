package tiraht;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 *
 * @author jonne
 */
public class ByteArray {
    private int capacity;
    private int length;
    private byte[] bytes;

    public ByteArray() {
        this(16);
    }

    public ByteArray(int capacity) {
        this.capacity = capacity;
        this.length = 0;
        bytes = new byte[capacity];
    }

    public void add(byte b) {
        if (length == capacity)
            grow();
        bytes[length++] = b;
    }

    private void grow() {
        capacity = capacity*2;
        bytes = Arrays.copyOf(bytes, capacity);
    }

    public byte[] getBytes() {
        return bytes;
    }

    public int getCapacity() {
        return capacity;
    }

    public int length() {
        return length;
    }

    @Override
    public int hashCode() {
        ByteBuffer bb = ByteBuffer.wrap(bytes, 0, length);
        return bb.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ByteArray other = (ByteArray) obj;
        if (this.length != other.length) {
            return false;
        }
        ByteBuffer thisbb = ByteBuffer.wrap(bytes, 0, length);
        ByteBuffer otherbb = ByteBuffer.wrap(other.bytes, 0, other.length);
        if (thisbb.compareTo(otherbb) != 0) {
            return false;
        }
        return true;
    }
}
