package tiraht.util;

/**
 * Yksinkertainen hajautustaulukko.
 *
 * Käytetään hajautusluku on i (mod 547567).
 * 547567 on alkuluku.
 *
 * @author jgsavola
 */
public class HashTable {
    private int tableSize = 547567;
    private SList[] table;

    private int hashFunc(int key) {
        return key % tableSize;
    }
    
    private class IntNode {
        int value;
        Object data;
        IntNode next;

        public IntNode(int value, Object data) {
            this.value = value;
            this.data = data;
            this.next = null;
        }
    }

    private class SList {
        IntNode head;
        
        public SList() {
            head = null;
        }

        public void put (int value, Object data) {
            if (head == null) 
                head = new IntNode(value, data);
            else {
                IntNode oldHead = head;
                head = new IntNode(value, data);
                head.next = oldHead;
            }
        }
        
        public Object get(int value) {
            IntNode node = head;
            while (node != null) {
                if (node.value == value) {
                    return node.data;
                }
                node = node.next;
            }
            
            return null;
        }
    }

    /**
     * Luo uusi hajautustaulukko.
     */
    public HashTable() {
        table = new SList[tableSize];
    }
    
    /**
     * Lisää arvo hajautustaulukkoon avaimella key.
     * @param key Avain.
     * @param data Arvo.
     */
    public void put(int key, Object data) {
        SList list = table[hashFunc(key)];
        if (list == null) {
            list = new SList();
            table[hashFunc(key)] = list;
        }
        list.put(key, data);
    }
    
    /**
     * Hae arvo avaimella key.
     * @param key Avain.
     * @return Arvo.
     */
    public Object get(int key) {
        SList list = table[hashFunc(key)];

        return list.get(key);
    }
}
