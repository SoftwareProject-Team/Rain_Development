import org.openpatch.scratch.Sprite;

public class XPOrb extends Sprite {

    public int expAmount;
    private static final String PATH = "sprites/Exp/";

    public XPOrb(double x, double y, int amount) {
        this.expAmount = amount;

        this.setX(x);
        this.setY(y);

        GameManager.AddCostumes(this, PATH, "exp", 4);

        if (amount < 5) {
            this.switchCostume("exp1");
        } else if (amount < 20) {
            this.switchCostume("exp2");
        } else if (amount < 50) {
            this.switchCostume("exp3");
        } else {
            this.switchCostume("exp4");
        }
    }

    @Override
    public void whenAddedToStage() {
        this.setSize(100);
    }
}