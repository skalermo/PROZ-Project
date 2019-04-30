package engine;

import java.util.ArrayList;

public class Hex {
    public final int q;
    public final int r;
    public final int s;

    static public ArrayList<Hex> directions = new ArrayList<Hex>(){
        {
            add(new Hex(1, 0, -1));
            add(new Hex(1, -1, 0));
            add(new Hex(0, -1, 1));
            add(new Hex(-1, 0, 1));
            add(new Hex(-1, 1, 0));
            add(new Hex(0, 1, -1));
        }
    };

    static public Hex direction(int direction /* 0 to 5 */){
        assert(0 <= direction && direction <= 5);
        return Hex.directions.get(direction);
    }

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

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Hex))
            return false;
        Hex b = (Hex) obj;
        return q == b.q &&
                r == b.r &&
                s == b.s;
//        return super.equals(obj);
    }

    public Hex scale(int k){
        return new Hex(q*k, r*k, s*k);
    }

    public Hex rotateLeft(){
        return new Hex(-s, -q, -r);
    }

    public Hex rotateRight(){
        return new Hex(-r, -s, -q);
    }

    public Hex neighbor(int direction)
    {
        return add(Hex.direction(direction));
    }

    static public ArrayList<Hex> diagonals = new ArrayList<Hex>(){
        {
            add(new Hex(2,-1, -1));
            add(new Hex(1, -2, 1));
            add(new Hex(-1, -1, 2));
            add(new Hex(-2, 1, 1));
            add(new Hex(-1, 2, -1));
            add(new Hex(1, 1, -2));
        }
    };

    public Hex diagonalHeighbor(int direction){
        return add(Hex.diagonals.get(direction));
    }

    public int length(){
        return (Math.abs(q)+Math.abs(r)+Math.abs(s))/2;
    }

    public int distance(Hex b)
    {
        return subtract(b).length();
    }

}
