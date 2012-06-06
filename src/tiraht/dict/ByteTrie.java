package tiraht.dict;

import java.util.Iterator;
import java.util.List;

/**
 * Trie-rakenne, joka käyttää avaimena tavujonoa.
 *
 * Tämä toteutus käyttää lapsisolmujen tallentamiseen TreeMap-rakennetta.
 *
 * @author jonne
 */
public class ByteTrie implements Trie {
    private byte nodeKey;
    private boolean isValid;
    private Integer value;

    int numChildren;
    int childrenArraySize;
    private ByteTrie[] children;

    /**
     * Luo uusi <code>ByteTrie</code> tulokaaren koodilla <code>nodeKey</code>.
     *
     * @param nodeKey Avaimen osa (tavu), jolla tähän solmuun on tultu.
     */
    public ByteTrie(byte nodeKey) {
        this();
        this.nodeKey = nodeKey;
    }

    /**
     * Luo uusi <code>ByteTrie</code>.
     */
    public ByteTrie() {
        this.isValid = false;
        this.numChildren = 0;
        this.childrenArraySize = 1;
        this.children = new ByteTrie[childrenArraySize];
    }

    /**
     * Anna solmun arvo.
     *
     * Jos solmulla ei ole arvoa, palauta <code>null</code>.
     *
     * @return Solmun arvo.
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Lisää arvo <code>value</code> avaimella <code>key</code>.
     *
     * @param key Avain tavuiteraattorina.
     * @param value Lisättävä arvo.
     */
    public void insert(Iterator<Byte> key, Integer value) {
        if (!key.hasNext()) {
            this.value = value;
            this.isValid = true;
            return;
        }

        Byte b = key.next();
        ByteTrie child = findChild(b);
        if (child == null) {
            child = new ByteTrie(b);
            addChild(child);
        }
        child.insert(key, value);
    }

    /**
     * Lisää arvo <code>value</code> avaimella <code>key</code>.
     *
     * @param key Avain tavulistana.
     * @param value Lisättävä arvo.
     */
    public void insert(List<Byte> key, Integer value) {
        insert(key.listIterator(), value);
    }

    /**
     * Lisää arvo <code>value</code> avaimella <code>key</code>.
     *
     * @param key Avain tavuna.
     * @param value Lisättävä arvo.
     */
    public void insert(Byte key, Integer value) {
        ByteTrie child = findChild(key);
        if (child == null) {
            child = new ByteTrie(key);
            addChild(child);
        } else {
            child.nodeKey = key;
        }
        child.isValid = true;
        child.value = value;
    }

    /**
     * Hae solmun lapsi avaimella <code>key</code>.
     *
     * @param key Avain tavumuodossa.
     * @return Lapsisolmu tai <code>null</code>.
     */
    private ByteTrie findChild(Byte key) {
        for (int i = 0; i < numChildren; i++) {
            ByteTrie trie = children[i];
            if (trie.nodeKey == key)
                return trie;
        }

        return null;
    }

    /**
     * Lisää solmuun lapsisolmu <code>child</code>.
     *
     * Avaimena käytetään lapsisolmun attribuuttia <code>nodeKey</code>.
     *
     * @param child Lisättävä lapsisolmu.
     */
    private void addChild(ByteTrie child) {
        if (numChildren == childrenArraySize) {
            childrenArraySize *= 2;
            ByteTrie[] newArray = new ByteTrie[childrenArraySize];
            System.arraycopy(children, 0, newArray, 0, numChildren);
            children = newArray;
        }
        children[numChildren] = child;
        numChildren++;
    }

    /**
     * Etsi solmu avaimella <code>key</code> ja palauta löydetyn solmun arvo.
     *
     * @param key Avain tavulistana.
     * @return Löydetty arvo tai <code>null</code>.
     */
    public Integer search(List<Byte> key) {
        return search(key.listIterator());
    }

    /**
     * Etsi solmu avaimella <code>key</code> ja palauta löydetyn solmun arvo.
     *
     * @param key Avain tavuiteraattorina.
     * @return Löydetty arvo tai <code>null</code>.
     */
    public Integer search(Iterator<Byte> key) {
        if (!key.hasNext()) {
            if (isValid)
                return value;
            return null;
        }

        byte b = key.next();
        ByteTrie child = findChild(b);
        if (child == null)
            return null;

        return child.search(key);
    }

    /**
     * Etsi solmu avaimella <code>key</code> ja palauta löydetty solmu.
     *
     * Tämä metodi soveltuu iteratiiviseen etsimiseen ja lisäämiseen.
     *
     * @param key Avain tavuiteraattorina.
     * @return Löydetty solmu tai <code>null</code>.
     */
    public ByteTrie retrieve(Iterator<Byte> key) {
        if (!key.hasNext()) {
            if (isValid)
                return this;
            return null;
        }

        byte b = key.next();
        ByteTrie child = findChild(b);
        if (child == null)
            return null;

        return child.retrieve(key);
    }

    /**
     * Etsi solmu avaimella <code>key</code> ja palauta löydetty solmu.
     *
     * Tämä metodi soveltuu iteratiiviseen etsimiseen ja lisäämiseen.
     *
     * @param key Avain tavuna.
     * @return Löydetty solmu tai <code>null</code>.
     */
    public ByteTrie retrieve(Byte key) {
        return findChild(key);
    }
}
