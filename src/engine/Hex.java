package engine;

public class Hex {
    public final int q;
    public final int r;
    public final int s;

    public Hex(int q, int r, int s)
    {
        this.q = q;
        this.r = r;
        this.s = s;
        if (q + r + s != 0) throw
        new IllegalArgumentException("q+r+s must be 0");
    }

    public Hex add(Hex b) {
        return new Hex(q + b.q, r + b.r, s + b.s);
    }

    public  Hex subtract(Hex b) {
        return new Hex(q - b.q, r - b.r, s - b.s);
    }

    public Hex scale(int k)
}
