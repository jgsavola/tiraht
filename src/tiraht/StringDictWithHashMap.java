package tiraht;

import java.util.HashMap;

/**
 * Toteutus <code>StringDict</code>-rajapinnasta HashMap-luokan avulla.
 *
 * @author jonne
 */
public class StringDictWithHashMap implements StringDict {
    private int nextIndex;
    private HashMap<String, Integer> map;

    /**
     * Luo uusi tyhjä sanakirja.
     */
    public StringDictWithHashMap() {
        nextIndex = 1;
        map = new HashMap<String, Integer>();
    }

    private void put(String s, int i) {
        map.put(s, i);
    }

    @Override
    public int search(String s) {
        Integer index = map.get(s);
        if (index == null)
            return -1;

        return index;
    }

    @Override
    public int insert(String s) {
        int thisIndex = nextIndex++;

        put(s, thisIndex);

        return thisIndex;
    }

    @Override
    public int getNextIndex() {
        return nextIndex;
    }
}
