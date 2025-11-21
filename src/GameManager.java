import org.openpatch.scratch.KeyCode;
import org.openpatch.scratch.RotationStyle;
import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.Stage;
import org.openpatch.scratch.extensions.math.Vector2;

public class GameManager extends Stage {

    public static GameManager Instance;

    public GameManager() {
        super(800, 600);

        Instance = this;

        Sprite player = new Player();
        this.add(player);
    }

    public static void main(String[] args) {
        new GameManager();
    }

    public static void AddCostumes(Sprite spr, String path, String name, int range){
        for (int i = 1; i <= range; i++) {
            spr.addCostume(name + i, path + name + i + ".png");
        }
    }
}



