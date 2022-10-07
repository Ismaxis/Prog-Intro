import java.util.Arrays;

class IntList { 
    private int[] storage;
    private int actualSize;

    public IntList(int initSize) {
        storage = new int[initSize];
        actualSize = 0;
    }

    public IntList() {
        this(0);
    }

    public IntList(IntList that) {
        this.storage = Arrays.copyOf(that.storage, that.storage.length);
        this.actualSize = that.actualSize; 
    }

    public void set(int index, int value) {
        if (isValidIndex(index)) {
            storage[index] = value;
        } else {
            throw new ArrayIndexOutOfBoundsException(index);
        }
    }

    public void append(int value) {
        adjustLength();
        storage[actualSize++] = value;
    }

    private void adjustLength() {
        if (actualSize == storage.length) {
            storage = Arrays.copyOf(storage, actualSize * 2);
        }
    }

    public int get(int index) {
        if (isValidIndex(index)) {
            return storage[index];
        } else {
            throw new ArrayIndexOutOfBoundsException(index);
        }
    }

    private boolean isValidIndex(int index) {
        return index > 0 && index < actualSize;
    }

    public int[] toIntArray() {
        return Arrays.copyOf(storage, actualSize);
    }
}