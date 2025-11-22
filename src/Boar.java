import org.openpatch.scratch.Sprite;

public class Boar extends Enemy{
    public Boar(){
        super();
        this.name = "boar";
        this.speed = 22f;
        this.frame = 8;

        this.frameDelay = 4;

        this.hp = 5;
        this.setSize(15);
        this.exp = 50;
        GameManager.AddCostumes(this, this.path,this.name, this.frame);
    }
}
