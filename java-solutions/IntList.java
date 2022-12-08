import java.util.Arrays;

class IntList {
    private int[] storage;
    private int actualSize;

    public IntList(int initSize) {
        storage = new int[initSize];
        actualSize = 0;
    }

    public IntList() {
        this(1);
    }

    public IntList(IntList that) {
        this.storage = Arrays.copyOf(that.storage, that.storage.length);
        this.actualSize = that.actualSize;
    }

    public int size() {
        return actualSize;
    }

    public void append(int value) {
        adjustLength();
        storage[actualSize++] = value;
    }

    public void set(int index, int value) {
        if (isValidIndex(index)) {
            storage[index] = value;
        }
    }

    public int[] toIntArray() {
        return Arrays.copyOf(storage, actualSize);
    }

    public int get(int index) {
        if (!isValidIndex(index)) {
            throw new ArrayIndexOutOfBoundsException(index);
        }

        return storage[index];
    }

    private void adjustLength() {
        if (actualSize == storage.length) {
            storage = Arrays.copyOf(storage, actualSize * 2);
        }
    }

    private boolean isValidIndex(int index) {
        return 0 <= index && index < actualSize;
    }
}
