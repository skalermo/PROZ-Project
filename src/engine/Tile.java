package engine;

import java.util.ArrayList;
import java.util.List;

public class Tile extends Hex{

    public static int MAX_TILE_HEIGHT = 7;
    public static int MAX_ELEMENTS_AMOUNT = 5;
    public static double HEX_VERTICAL_OFFSET = 21;
    public static double HEX_WIDTH_SIZE = 65/Math.sqrt(3);
    public static double HEX_HEIGHT_SIZE = 31.5;

    private boolean isAccessible = false;

    private boolean isSelected = false;

    private String type = "empty";

    private int elementsAmount = 0;

    private int heightOfTile = 0;

    private List<String> tilesBeneath;

    private List<String> elementsTypes;
    private List<Point> elementsCoords;

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

    void pushTile(String tileType) {
        if (type.equals("empty")) {
            type = tileType;
            if (tileType.equals("tileWater") ||
            tileType.equals("tileWater_full"))
                setAccess(false);
            else
                setAccess(true);
            heightOfTile++;
            return;
        }
        if (tilesBeneath == null)
            tilesBeneath = new ArrayList<>();
        if (tilesBeneath.size() >= MAX_TILE_HEIGHT - 1)
            return;
        tilesBeneath.add(type);
        type = tileType;
        heightOfTile++;
    }

    void popTile() {
        if (tilesBeneath == null)
            tilesBeneath = new ArrayList<>();
        if (tilesBeneath.size() == 0) {
            type = "empty";
            setAccess(false);
            if (heightOfTile > 0)
                heightOfTile--;
            return;
        }
        type = tilesBeneath.get(tilesBeneath.size() - 1);
        if (type.equals("tileWater") ||
                type.equals("tileWater_full"))
            setAccess(false);
        else
            setAccess(true);
        tilesBeneath.remove(tilesBeneath.size() - 1);
        heightOfTile--;
    }

    void pushElement(String type, Point coords) {
        if (elementsAmount >= MAX_ELEMENTS_AMOUNT)
            return;
        if (elementsTypes == null && elementsCoords == null) {
            elementsTypes = new ArrayList<>(Tile.MAX_ELEMENTS_AMOUNT);
            elementsCoords = new ArrayList<>(Tile.MAX_ELEMENTS_AMOUNT);
        }
        elementsTypes.add(type);
        elementsCoords.add(coords);
        elementsAmount++;
    }

    void removeElements() {
        elementsAmount = 0;
        elementsTypes.clear();
        elementsCoords.clear();
    }



    public String getTypeFromTile(int index) {
        return tilesBeneath.get(index);
    }

    public int getHeightOfTile() {
        return heightOfTile;
    }

    public List<String> getElementsTypes() {
        return elementsTypes;
    }

    public List<Point> getElementsCoords() {
        return elementsCoords;
    }

    public int getElementsAmount() {
        return elementsAmount;
    }

    void select(){
        isSelected = true;
    }

    void unselect(){
        isSelected = false;
    }


}
