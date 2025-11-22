import org.openpatch.scratch.Sprite;

public class Snail extends Enemy {
    public Snail(){
        super();
        this.name = "snail";
        this.speed = 30f;
        this.frame = 8;

        this.frameDelay = 4;

        this.hp = 3;
        this.setSize(15);
        this.exp = 1;
        GameManager.AddCostumes(this, this.path, name, this.frame);
    }
}
