import org.openpatch.scratch.KeyCode;
import org.openpatch.scratch.RotationStyle;
import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.Stage;
import org.openpatch.scratch.extensions.math.Vector2;


public class Slime extends Enemy {

    public Slime(){
        super();
        this.name = "redSlime";

        this.speed = 20f;
        this.frame = 6;

        this.frameDelay = 5;

        this.hp = 5;
        this.setSize(25);
        this.exp = 10;
        GameManager.AddCostumes(this, this.path, name, this.frame);
    }

}
