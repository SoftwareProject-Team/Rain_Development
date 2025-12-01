import org.openpatch.scratch.Sprite;

public class XPOrb extends Sprite {

    private int expAmount;
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

        this.setSize(50);
    }

    @Override
    public void run() {
        if (GameManager.isGamePaused) return;

        Player player = Player.Instance;

        if (player == null) return;

        // 무한 반복 -> 거리 체크 로직
        if (distanceToSprite(player) < player.magnetRange) {

            // 경험치 획득
            player.exp += this.expAmount;

            GameManager.Instance.remove(this);
        }
    }
}