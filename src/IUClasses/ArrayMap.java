package IUClasses;

import java.util.*;

public class ArrayMap<K, V> implements Map<K, V> {
    //#region Fields
    transient int size = 0;
    protected Entry<K, V>[] values;
    /**
     * This parameter is only here for completeness. It's neither used nor does it get instantiated.
     */
    protected Set<K> keySet;
    /**
     * This parameter is only here for completeness. It's neither used nor does it get instantiated.
     */
    protected Collection<V> vallueCollection;

    //#endregion Fields

    //#region Constructors
    public ArrayMap() {
        this.values = (Entry<K, V>[]) new Entry[8];
    }

    public ArrayMap(int size) {
        this.values = (Entry<K, V>[]) new Entry[size];
    }

    public ArrayMap(Map<? extends K, ? extends V> pMap) {
        // put map values into array
        putAll(pMap);
    }
    //#endregion Constructors

    //#region Subclasses
    static class Entry<K, V> implements Map.Entry<K, V> {
        protected K key;
        protected V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V pValue) {
            V oldValue = value;
            value = pValue;
            return oldValue;
        }

        public boolean equals(Object pObject) {
            // if it's not a Map.Entry there is no point in comparing the rest
            if (!(pObject instanceof Map.Entry<?, ?> lEntry)) {
                return false;
            }

            // first check for null values on key and value
            // then proceed with the more specific equals functions on the Objects
            return key == null ? lEntry.getKey() == null : key.equals(lEntry.getKey())
                    && value == null ? lEntry.getValue() == null : value.equals(lEntry.getValue());
        }

        public int hashCode() {
            int keyHash = key == null ? 0 : key.hashCode();
            int valueHash = value == null ? 0 : value.hashCode();
            return keyHash ^ valueHash;
        }

        public String toString() {
            return key + "=" + value;
        }
    }
    //#endregion Subclasses

    //#region Methods
    //#region publics

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object pKey) {
        return getEntry(pKey) != null;
    }

    @Override
    public boolean containsValue(Object pValue) {
        for (Entry<K, V> lEntry : values) {
            if (lEntry != null) {
                if (pValue.equals(lEntry.getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public V get(Object pKey) {
        Entry<K, V> lEntry = getEntry(pKey);

        return lEntry != null ? lEntry.getValue() : null;
    }

    @Override
    public V put(K pKey, V pValue) {
        // prepare hash key by which the entries are sorted
        int positionKey = getPositionKey(pKey);

        V lOldValue = null;

        if (values[positionKey] != null) {
            if (values[positionKey].equals(pKey)) {
                // safe old value for return
                lOldValue = values[positionKey].getValue();
                // update existing entry with new value
                values[positionKey].setValue(pValue);
            } else {
                // position already taken :(
                // enlarge the base
                grow();
                //try again
                put(pKey, pValue);
            }
        } else {
            // space empty, so we can just add the entry here
            addEntry(pKey, pValue);
            size++;
        }

        return lOldValue;
    }

    @Override
    public V remove(Object pKey) {
        V lValue = null;

        if (size == 0){
            return lValue;
        }

        if (containsKey(pKey)) {
            lValue = values[getPositionKey(pKey)].getValue();
            values[getPositionKey(pKey)] = null;
        }

        size--;

        return lValue;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> pMap) {
        bulkAdd(pMap);
    }

    @Override
    public void clear() {
        int lSize = values.length;
        values = (Entry<K, V>[]) new Entry[lSize];
        size = 0;
    }

    /**
     * Not implemented as the assignment would be bloated.
     * @throws UnsupportedOperationException Not Supported
     */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not implemented as the assignment would be bloated.
     * @throws UnsupportedOperationException Not Supported
     */
    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    /**
     * Not implemented as the assignment would be bloated.
     * @throws UnsupportedOperationException Not Supported
     */
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }

    public Entry<K, V>[] toArray() {
        Entry<K, V>[] lEntries = (Entry<K, V>[]) new Entry[size];
        int i = 0;

        for (Entry<K, V> lEntry : values) {
            if (lEntry != null) {
                lEntries[i] = lEntry;
                i++;
            }
        }

        return lEntries;
    }

    @Override
    public String toString() {
        StringBuffer lSB = new StringBuffer();

        Entry<K, V>[] lEntries = toArray();

        lSB.append("{");
        for (int i = 0; i < lEntries.length; i++) {
            if (i != 0) {
                lSB.append(", ");
            }

            lSB.append(lEntries[i].toString());
        }
        lSB.append("}");

        return lSB.toString();
    }
    //#endregion publics

    //#region privates

    private void bulkAdd(Map<? extends K, ? extends V> pMap) {
        int lSize = pMap.size();
        // don't have to do a thing if there are no elements to add
        if (lSize > 0) {
            // rather resize now in bulk than during the whole process
            while (values.length <= (size + lSize)) {
                grow();
            }

            for (Map.Entry<? extends K, ? extends V> lEntry : pMap.entrySet()) {
                K lKey = lEntry.getKey();
                V lValue = lEntry.getValue();
                put(lKey, lValue);
            }
        }
    }

    /**
     * {@summary Doubles the available space inside the "values" array.}
     */
    private void grow() {
        Entry<K, V>[] lOldValues = values;
        int lSize = values.length;
        // double the length, as performing the copying is rather expensive
        values = (Entry<K, V>[]) new Entry[lOldValues.length * 2];

        if (size > 0) {
            // sort existing entries in new size array
            for (Entry<K, V> lEntry : lOldValues) {
                if (lEntry != null) {
                    addEntry(lEntry.getKey(), lEntry.getValue());
                }
            }
        }
    }

    /**
     * Calculates the position in the array for given key pKey.
     *
     * @param pKey Key Value
     * @return int Position inside the array
     */
    private int getPositionKey(Object pKey) {
        int hashKey = pKey.hashCode();
        return (hashKey % values.length);
    }

    /**
     * Finds and returns the entry for a certain key pKey.
     *
     * @param pKey Key value
     * @return Entry with key pKey
     */
    private Entry<K, V> getEntry(Object pKey) {
        int positionKey = getPositionKey(pKey);

        Entry<K, V> lEntry = null;

        if (values[positionKey] != null && pKey.equals(values[positionKey].getKey())) {
            lEntry = values[positionKey];
        }

        return lEntry;
    }

    /**
     * Adds the Key Value pair into the array. This method should only be called when certain that the pKey value
     * does not exist in the array already
     *
     * @param pKey   K Value
     * @param pValue V Value
     */
    private void addEntry(K pKey, V pValue) {
        int positionKey = getPositionKey(pKey);

        values[positionKey] = new Entry<>(pKey, pValue);
    }
    //#endregion privates

    //#endregion Methods
}
