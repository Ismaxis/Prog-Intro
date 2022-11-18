package md2html;

public class MPair<F, S> {
    private F first;
    private S second;

    public MPair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F first() {
        return first;
    }

    public S second() {
        return second;
    }
}
