package engine;

import java.util.ArrayList;

public class Tile extends Hex{

    private boolean isOccupied = false;

    private boolean isSelected = false;

    private String type = "tileGrass";

    private ArrayList<String> elements;

    public Tile(int q, int r){
        super(q, r);
    }

    public Tile(Hex h){
        super(h.q, h.r);
        //return new Tile(h.q, h.r);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void occupy(){
        isOccupied = true;
    }

    public void leave(){
        isOccupied = false;
    }

    public boolean isOccupied(){
        return isOccupied;
    }

    boolean isSelected(){
        return isSelected;
    }

    void changeSelection(){
        isSelected = !isSelected;
    }


}
