package engine;

interface Actions {
    void move();
    void attack();
    void die();
}

class Creature {
    private int HP;
    private int q;
    private int r;

    public Creature(int q, int r) {
        this.q = q;
        this.r = r;
    }

    public int getHP() {
        return HP;
    }

    public int getQcoord() {
        return q;
    }

    public int getRcoord() {
        return r;
    }
}

class Warrior extends Creature implements Actions{

    public Warrior(int q, int r) {
        super(q, r);
    }

    @Override
    public void move() {

    }

    @Override
    public void attack() {

    }

    @Override
    public void die() {

    }
}

class Archer extends Creature implements Actions{

    public Archer(int q, int r) {
        super(q, r);
    }

    @Override
    public void move() {

    }

    @Override
    public void attack() {

    }

    @Override
    public void die() {

    }
}

class Mage extends Creature implements Actions{

    public Mage(int q, int r) {
        super(q, r);
    }

    @Override
    public void move() {

    }

    @Override
    public void attack() {

    }

    @Override
    public void die() {

    }
}
