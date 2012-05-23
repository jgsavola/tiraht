package tiraht;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author jonne
 */
public class ByteArray {
    private int capacity;
    private int length;
    private byte[] bytes;

    public class ByteArrayIterator implements Iterator {
        private ByteArray byteArray;
        private int position;

        public ByteArrayIterator(ByteArray byteArray) {
            position = 0;
            this.byteArray = byteArray;
        }

        public ByteArrayIterator(ByteArray byteArray, int position) {
            this.position = position;
            this.byteArray = byteArray;
        }

        @Override
        public boolean hasNext() {
            return position < byteArray.length();
        }

        @Override
        public Object next() throws NoSuchElementException {
            if (position >= byteArray.length())
                throw new NoSuchElementException();

            return bytes[position++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove() not supported.");
        }
    }

    public ByteArray() {
        this(16);
    }

    public ByteArray(int capacity) {
        this.capacity = capacity;
        this.length = 0;
        bytes = new byte[capacity];
    }

    public ByteArray(byte[] bytes) {
        this.capacity = bytes.length;
        this.length = bytes.length;
        this.bytes = bytes;
    }

    public ByteArray(byte[] bytes, int capacity) {
        if (capacity < bytes.length)
            throw new ArrayIndexOutOfBoundsException("ByteArray: capacity < bytes.length");

        this.capacity = capacity;
        this.length = bytes.length;
        this.bytes = Arrays.copyOf(bytes, capacity);
    }

    public void add(byte b) {
        if (length == capacity)
            grow();
        bytes[length++] = b;
    }

    private void grow() {
        if (capacity < 4)
            capacity = 4;
        else
            capacity = capacity*2;
        bytes = Arrays.copyOf(bytes, capacity);
    }

    public byte[] getBytes() {
        /*
         * Palauta kopio tavutaulukosta, jos taulukko ei ole täynnä.
         * (Tämä voi olla epäviisasta: käyttäjän käytännössä oletettava,
         * että taulukko on jaettu, kun parempaakaan tietoa ei ole.)
         */
        if (length == capacity)
            return bytes;
        else
            return Arrays.copyOf(bytes, length);
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

    public ByteArrayIterator iterator() {
        return new ByteArrayIterator(this, 0);
    }

    public ByteArrayIterator iterator(int position) {
        return new ByteArrayIterator(this, position);
    }
}
