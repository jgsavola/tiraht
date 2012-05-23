package tiraht;

import java.util.HashMap;

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
        return insert(new ByteArray(b));
    }

    @Override
    public int insert(ByteArray b) {
        int thisIndex = nextIndex++;

        put(b, thisIndex);

        return thisIndex;
    }

    @Override
    public int search(byte[] b) {
        return search(new ByteArray(b));
    }

    @Override
    public int search(ByteArray b) {
        Integer index = map.get(b);
        if (index == null)
            return -1;

        return index;
    }

    @Override
    public int getNextIndex() {
        return nextIndex;
    }

    @Override
    public ByteArray lookup(int index) {
        if (createReverseMap) {
            ByteArray ba = reverseMap.get(index);
            if (ba != null)
                return ba;
        } else {
            for (ByteArray key : map.keySet()) {
                int thisIndex = map.get(key);
                if (thisIndex == index)
                    return key;
            }
        }

        return null;
    }
}
