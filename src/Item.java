import org.openpatch.scratch.Sprite;

public class Item extends Sprite {
    public int level;

    public String name;
    public String iconPath;
    public String descriptPath;

    public static String icPath = "sprites/Item/";
    public static String desPath = "sprites/Descript/";

    public Item(String name, String iconPath){
        this.name = name;
        this.iconPath = icPath + iconPath + ".png";
        this.descriptPath = desPath + iconPath + ".png";
    }


    void levelup() {
        if(level < 5) level++;
    }
}