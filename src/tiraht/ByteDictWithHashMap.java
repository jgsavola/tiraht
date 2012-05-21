package tiraht;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author jgsavola
 */
public class ByteDictWithHashMap implements ByteDict {
    int nextIndex;
    HashMap<ByteArray, Integer> map;
    HashMap<Integer, ByteArray> reverseMap;
    boolean createReverseMap;

    public ByteDictWithHashMap() {
        this(false);
    }

    public ByteDictWithHashMap(boolean createReverseMap) {
        nextIndex = 1;
        map = new HashMap<ByteArray, Integer>();
        
        this.createReverseMap = createReverseMap;
        if (this.createReverseMap)
            reverseMap = new HashMap<Integer, ByteArray>();

        put(new ByteArray(new byte[0]), 0);
    }
    
    private void put(ByteArray bytes, int i) {
        map.put(bytes, i);
        if (createReverseMap)
            reverseMap.put(i, bytes);
    }

    @Override
    public int insert(byte[] b) {
        int thisIndex = nextIndex++;

        put(new ByteArray(b), thisIndex);

        return thisIndex;
    }

    @Override
    public int insert(Vector<Byte> key) {
        byte[] bytes = new byte[key.size()];
        for (int i = 0; i < key.size(); i++)
            bytes[i] = key.get(i);

        return insert(bytes);
    }

    @Override
    public int search(byte[] b) {
        Integer index = map.get(new ByteArray(b));
        if (index == null)
            return -1;

        return index;
    }

    @Override
    public int search(Vector<Byte> key) {
        byte[] bytes = new byte[key.size()];
        for (int i = 0; i < key.size(); i++)
            bytes[i] = key.get(i);

        return search(bytes);
    }

    @Override
    public int getNextIndex() {
        return nextIndex;
    }

    @Override
    public byte[] lookup(int index) {
        if (createReverseMap) {
            ByteArray ba = reverseMap.get(index);
            if (ba != null)
                return ba.getBytes();
        } else {
            for (ByteArray key : map.keySet()) {
                int thisIndex = map.get(key);
                if (thisIndex == index)
                    return key.bytes;
            }
        }

        return null;
    }

    /**
     * <code>ByteArray</code>-luokkaa tarvitaan, jotta tavutaulukoita
     * voisi tallettaa <code>HashMap</code>-olioon.
     */
    private class ByteArray {
        private byte[] bytes;

        public ByteArray(byte[] bytes) {
            this.bytes = bytes;
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(bytes);
        }

        @Override
        public boolean equals(Object o) {
            return Arrays.equals(bytes, ((ByteArray)o).bytes);
        }

        public byte[] getBytes() {
            return bytes;
        }
    }
}
    
