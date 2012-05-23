package tiraht;

import java.util.HashMap;

/**
 *
 * @author jgsavola
 */
public class ByteDictWithHashMap implements ByteDict {
    int nextIndex;
    HashMap<ByteArray, Integer> map;

    public ByteDictWithHashMap() {
        nextIndex = 1;
        map = new HashMap<ByteArray, Integer>();
    }

    private void put(ByteArray bytes, int i) {
        map.put(bytes, i);
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
}
