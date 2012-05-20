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
        map.put("", 0);
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

        map.put(s, thisIndex);

        return thisIndex;
    }

    @Override
    public int getNextIndex() {
        return nextIndex;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Huom! Haku toimii ajassa <code>O(n)</code>, missä <code>n</code> on
     * sanojen määrä sanakirjassa.</p>
     *
     * @param index Haettava indeksi.
     * @return Merkkijono tai <code>null</code>, jos indeksiä ei löydy.
     */
    @Override
    public String lookup(int index) {
        for (String key : map.keySet()) {
            int thisIndex = map.get(key);
            if (thisIndex == index) {
                return key;
            }
        }

        return null;
    }
}
