package tiraht;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 *
 * @author jonne
 */
public class ByteTrie<E> implements Trie {
    private byte nodeKey;
    private boolean isValid;
    private E value;

    private ArrayList<ByteTrie<E>> children;

    public ByteTrie(byte nodeKey) {
        this();
        this.nodeKey = nodeKey;
    }

    public ByteTrie() {
        this.isValid = false;
        this.children = new ArrayList<ByteTrie<E>>();
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
            children.add(child);
        }
        child.insert(key, value);
    }

    public void insert(List<Byte> key, E value) {
        insert(key.listIterator(), value);
    }

    private ByteTrie<E> findChild(Byte b) {
        for (ByteTrie<E> child : children)
            if (child.nodeKey == b)
                return child;

        return null;
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
}
