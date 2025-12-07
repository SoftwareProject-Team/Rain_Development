import org.openpatch.scratch.Sprite;

public class Background extends Sprite {
    public static String path = "sprites/Background/grassMap.png";

    Background(){
        addCostume("grass", path);
    }

    @Override
    public void whenAddedToStage() {
        setSize(150);
    }

    @Override
    public void run() {

    }
}
