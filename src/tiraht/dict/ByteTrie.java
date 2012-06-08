package tiraht.dict;

import java.util.Iterator;
import java.util.List;

/**
 * Trie-rakenne, joka käyttää avaimena tavujonoa.
 *
 * Tämä toteutus käyttää lapsisolmujen tallentamiseen järjestettyä taulukkoa.
 *
 * @author jonne
 */
public class ByteTrie implements Trie {
    private byte nodeKey;
    private Integer value;

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
        this.children = new ByteTrie[1];
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
        ByteTrie node = this;

        while (true) {
            if (!key.hasNext()) {
                node.value = value;
                return;
            }

            byte b = key.next();
            ByteTrie child = node.findChild(b);
            if (child == null) {
                child = new ByteTrie(b);
                node.addChild(child);
            }
            node = child;
        }
    }

    /**
     * Lisää arvo <code>value</code> avaimella <code>key</code>.
     *
     * @param key Avain tavutaulukkona.
     * @param value Lisättävä arvo.
     */
    public void insert(byte[] key, Integer value) {
        ByteTrie node = this;

        for (byte b : key) {
            ByteTrie child = node.findChild(b);
            if (child == null) {
                child = new ByteTrie(b);
                node.addChild(child);
            }
            node = child;
        }
        node.value = value;
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
    public void insert(byte key, Integer value) {
        ByteTrie child = findChild(key);
        if (child == null) {
            child = new ByteTrie(key);
            addChild(child);
        } else {
            child.nodeKey = key;
        }
        child.value = value;
    }

    /**
     * Hae lapsisolmun indeksi avaimella <code>key</code> käyttäen binäärihakua.
     *
     * @param key Avain.
     * @return Lapsisolmun indeksi tai -1, jos solmua ei löydy.
     */
    private int binarySearch(byte key) {
        int left = 0;
        int right = children.length - 1;

        while (right >= left) {
            int i = (left + right) / 2;

            if (children[i] == null || key < children[i].nodeKey) {
                right = i - 1;
            } else if (key > children[i].nodeKey) {
                left = i + 1;
            } else {
                return i;
            }
        }

        return -1;
    }

    /**
     * Hae solmun lapsi avaimella <code>key</code>.
     *
     * @param key Avain tavumuodossa.
     * @return Lapsisolmu tai <code>null</code>.
     */
    private ByteTrie findChild(byte key) {
        int i = binarySearch(key);
        if (i == -1)
            return null;
        return children[i];
    }

    /**
     * Lisää solmuun lapsisolmu <code>child</code>.
     *
     * Avaimena käytetään lapsisolmun attribuuttia <code>nodeKey</code>.
     *
     * Jos taulukko täyttyy, luodaan uusi isompi taulukko, johon
     * vanhan taulun alkiot kopioidaan.
     *
     * Lisääminen tehdään taulukon loppuun, josta lisättävä alkio kuljetetaan
     * oikealle paikalle käyttäen <code>nodeKey</code>-kenttää vertailuarvona.
     *
     * Näin lapsisolmut pysyvät järjestyksessä, mikä mahdollistaa binäärihaun
     * käyttämisen.
     *
     * Optimointimahdollisuus: haetaan oikea paikka binäärihaulla, sijoitetaan
     * uusi alkio ja siirretään lopputaulukon alkioita yksi paikka oikealle.
     *
     * @param child Lisättävä lapsisolmu.
     */
    private void addChild(ByteTrie child) {
        int firstFreeIndex = 0;
        for (int i = children.length - 1; i >= 0; i--) {
            if (children[i] != null) {
                firstFreeIndex = i + 1;
                break;
            }
        }

        if (firstFreeIndex == children.length) {
            int newLength = children.length * 2;
            ByteTrie[] newArray = new ByteTrie[newLength];
            System.arraycopy(children, 0, newArray, 0, children.length);
            children = newArray;
        }
        children[firstFreeIndex] = child;
        for (int i = firstFreeIndex; i > 0 && children[i].nodeKey < children[i - 1].nodeKey; i--) {
            ByteTrie tmp = children[i];
            children[i] = children[i - 1];
            children[i - 1] = tmp;
        }
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
        ByteTrie node = this;

        while (true) {
            if (!key.hasNext()) {
                return node.value;
            }

            byte b = key.next();
            ByteTrie child = node.findChild(b);
            if (child == null) {
                return null;
            }
            node = child;
        }
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
            if (value != null)
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
    public ByteTrie retrieve(byte key) {
        return findChild(key);
    }
}
