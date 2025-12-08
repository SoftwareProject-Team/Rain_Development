import org.openpatch.scratch.RotationStyle;
import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.extensions.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class CycleSythe extends WeaponItem{
    public CycleSythe(String name, String iconPath) {
        super(name, iconPath);
    }

    class CycleSytheObject extends Sprite {
        public static String path = "sprites/Weapon/CycleSythe/";

        double dir;
        double size;
        Vector2 startPos;
        double damage;
        double hitSize;

        public double deathTime;

        CycleSytheObject(double dir, Vector2 startPos, double size, double damage){
            this.dir = dir;
            this.startPos = startPos;
            this.size = size;
            this.damage = damage;
            this.hitSize = HIT_SIZE * (size / 100);

            distance = size / 2 + 30;

            addCostume("sythe", path + "CycleSythe.png");
        }

        @Override
        public void whenAddedToStage() {
            Initialize();
        }

        public void Initialize(){
            setPosition(startPos);
            setSize(size);

            SetPos();

            deathTime = 6;
        }

        public static final double HIT_DELAY = 0.2;
        public double hitTimer;

        public double distance;

        public double curDir;

        public void SetPos(){
            double sumDir = dir + curDir;

            double dirRad = Math.toRadians(sumDir);
            double dx = Math.cos(dirRad) * distance;
            double dy = Math.sin(dirRad) * distance;

            setPosition(
                    Player.Instance.getX() + dx,
                    Player.Instance.getY() + dy
            );
        }

        @Override
        public void run() {
            if (GameManager.isGamePaused) return;

            deathTime -= GameManager.FRAME_TIME;
            if(deathTime < 0) remove();

            SetPos();

            curDir += 60 * GameManager.FRAME_TIME;
            setDirection(getDirection() - 360 * GameManager.FRAME_TIME);

            hitTimer += GameManager.FRAME_TIME;
            if(hitTimer > HIT_DELAY) {
                hitTimer -= HIT_DELAY;
                List<Enemy> enemies = getTouchingSprites(Enemy.class);
                if (enemies != null) {
                    for (Enemy e : enemies) {
                        if (distanceToSprite(e) < hitSize) {
                            e.getDamage(damage);
                            e.Knockback(8);
                        }
                    }
                }
            }
        }


    }

    @Override
    public void whenAddedToStage() {
        Initialize();
    }

    void Initialize(){
        level = 0; //임시 레벨 설정
    }

    public static final double HIT_SIZE = 35;

    public static final double[] damage = new double[] { 1, 2, 3, 4, 5 };
    public static final double attackDelay = 6;
    public static final double[] attackSize = new double[] { 80, 100, 130, 160, 200 };
    public static final int[] attackCount = new int[] { 1, 1, 2, 2, 3 };

    public double attackTimer;

    @Override
    public void run() {
        if(level <= 0) return;

        if (GameManager.isGamePaused) return;

        attackTimer-=GameManager.FRAME_TIME;
        if(attackTimer < 0) {
            attackTimer += attackDelay;

            int ac = attackCount[level - 1] + Player.Instance.bonusProjectile;
            double angleScale = 360 / ac;
            for (int i = 0; i < ac; i++) {
                GameManager.Instance.add(new CycleSytheObject(
                        angleScale * i,
                        Player.Instance.getPosition(),
                        attackSize[level - 1] * (1 + Player.Instance.bonusAttackSize),
                        damage[level - 1] * (1 + Player.Instance.bonusDamage)));
            }
        }
    }
}