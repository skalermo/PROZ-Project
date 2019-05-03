package engine;

import java.util.ArrayList;

public class Tile extends Hex{

    private boolean isAccessible;

    private boolean isSelected = false;

    private String type = "tileGrass";

    private int heightOfTile = 1;

    protected ArrayList<String> elements;

    public Tile(int q, int r){

        super(q, r);
        isAccessible = false;
        isSelected = false;
    }

    public Tile(Hex h){

        super(h.q, h.r);
        isAccessible = false;
        isSelected = false;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAccessible(){
        return isAccessible;
    }

    boolean isSelected(){
        return isSelected;
    }

    void setAccess(boolean access){
        isAccessible = access;
    }

    void select(){
        isSelected = true;
    }

    void unselect(){
        isSelected = false;
    }


}
