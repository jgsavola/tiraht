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
    private HashMap<Integer, String> reverseMap;
    private boolean createReverseMap;

    /**
     * Luo uusi tyhjä sanakirja.
     *
     * Ei luoda käänteistä indeksointia (indeksi ==> merkkijono).
     */
    public StringDictWithHashMap() {
        this(false);
    }

    /**
     * Luo uusi tyhjä sanakirja.
     *
     * @param createReverseMap Jos <code>true</code>, niin luodaan myös
     *        käänteinen indeksointi (indeksi ==> merkkijono).
     */
    public StringDictWithHashMap(boolean createReverseMap) {
        nextIndex = 1;
        map = new HashMap<String, Integer>();
        
        this.createReverseMap = createReverseMap;
        if (this.createReverseMap)
            reverseMap = new HashMap<Integer, String>();

        put("", 0);
    }

    private void put(String s, int i) {
        map.put(s, i);
        if (createReverseMap)
            reverseMap.put(i, s);
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
        if (createReverseMap)
            return reverseMap.get(index);
        else {
            for (String key : map.keySet()) {
                int thisIndex = map.get(key);
                if (thisIndex == index)
                    return key;
            }
        }

        return null;
    }
}
