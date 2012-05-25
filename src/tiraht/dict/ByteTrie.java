package tiraht.dict;

import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

/**
 *
 *
 * @author jonne
 */
public class ByteTrie<E> implements Trie {
    private byte nodeKey;
    private boolean isValid;
    private E value;

    //private ArrayList<ByteTrie<E>> children;
    private TreeMap<Byte, ByteTrie<E>> children;

    public ByteTrie(byte nodeKey) {
        this();
        this.nodeKey = nodeKey;
    }

    public ByteTrie() {
        this.isValid = false;
        //this.children = new ArrayList<ByteTrie<E>>();
        this.children = new TreeMap<Byte, ByteTrie<E>>();
    }

    public E getValue() {
        return value;
    }

    public void insert(Iterator<Byte> key, E value) {
        if (!key.hasNext()) {
            this.value = value;
            this.isValid = true;
            return;
        }

        Byte b = key.next();
        ByteTrie<E> child = findChild(b);
        if (child == null) {
            child = new ByteTrie<E>(b);
            addChild(child);
        }
        child.insert(key, value);
    }

    public void insert(List<Byte> key, E value) {
        insert(key.listIterator(), value);
    }

    public void insert(Byte key, E value) {
        ByteTrie<E> child = findChild(key);
        if (child == null) {
            child = new ByteTrie<E>(key);
            addChild(child);
        } else {
            child.nodeKey = key;
        }
        child.isValid = true;
        child.value = value;
    }

//    private ByteTrie<E> findChild(Byte b) {
//        for (ByteTrie<E> child : children)
//            if (child.nodeKey == b)
//                return child;
//
//        return null;
//    }
//
//    private void addChild(ByteTrie<E> child) {
//        children.add(child);
//    }

    private ByteTrie<E> findChild(Byte b) {
        return children.get(b);
    }

    private void addChild(ByteTrie<E> child) {
        children.put(child.nodeKey, child);
    }

    public E search(List<Byte> key) {
        return search(key.listIterator());
    }

    public E search(Iterator<Byte> key) {
        if (!key.hasNext()) {
            if (isValid)
                return value;
            return null;
        }

        byte b = key.next();
        ByteTrie<E> child = findChild(b);
        if (child == null)
            return null;

        return child.search(key);
    }

    public ByteTrie<E> retrieve(Iterator<Byte> key) {
        if (!key.hasNext()) {
            if (isValid)
                return this;
            return null;
        }

        byte b = key.next();
        ByteTrie<E> child = findChild(b);
        if (child == null)
            return null;

        return child.retrieve(key);
    }

    public ByteTrie<E> retrieve(Byte key) {
        return findChild(key);
    }
}
