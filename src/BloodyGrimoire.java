import org.openpatch.scratch.KeyCode;
import org.openpatch.scratch.RotationStyle;
import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.extensions.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class BloodyGrimoire extends WeaponItem {
    public BloodyGrimoire(String name, String iconPath) {
        super(name, iconPath);
    }

    class BloodyGrimoirePP extends Projectile {
        public static String path = "sprites/Weapon/BloodyGrimoire/";

        BloodyGrimoirePP(double dir, Vector2 startPos, double size, double damage){
            this.dir = dir;
            this.startPos = startPos;
            this.size = size;
            this.damage = damage;

            GameManager.AddCostumes(this, path, "bloodPP_", 15);
        }

        @Override
        public void whenAddedToStage() {
            Initialize();
        }

        public void Initialize(){
            setDirection(dir);
            setPosition(startPos);
            setSize(bulletSize);

            speed = 150;
            deathTime = 1;
        }

        @Override
        public void run() {
            if (GameManager.isGamePaused) return;

            deathTime -= GameManager.FRAME_TIME;
            if(deathTime < 0) summonField();



            move(speed * GameManager.FRAME_TIME);
            nextCostume();
        }

        public void summonField(){
            GameManager.Instance.add(new BloodyField(
                    getPosition(),
                    size,
                    damage));

            remove();
        }
    }

    class BloodyField extends Sprite {
        public static String path = "sprites/Weapon/BloodyGrimoire/";

        double size;
        Vector2 startPos;
        double damage;
        double hitSize;

        public double deathTime;

        BloodyField(Vector2 startPos, double size, double damage){
            this.startPos = startPos;
            this.size = size;
            this.damage = damage;
            this.hitSize = HIT_SIZE * (size / 100);

            GameManager.AddCostumes(this, path, "bloodField_", 30);
        }

        @Override
        public void whenAddedToStage() {
            Initialize();
        }

        public void Initialize(){
            setPosition(startPos);
            setSize(size);

            deathTime = 5;
        }

        public static final double HIT_DELAY = 0.2;
        public double hitTimer;

        public double distance;

        public double curDir;

        @Override
        public void run() {
            if (GameManager.isGamePaused) return;

            deathTime -= GameManager.FRAME_TIME;
            if(deathTime < 0) remove();

            nextCostume();

            hitTimer += GameManager.FRAME_TIME;
            if(hitTimer > HIT_DELAY) {
                hitTimer -= HIT_DELAY;
                List<Enemy> enemies = getTouchingSprites(Enemy.class);
                if (enemies != null) {
                    for (Enemy e : enemies) {
                        if (distanceToSprite(e) < hitSize) {
                            e.getDamage(damage);
                            e.Knockback(3);
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

    public static final double HIT_SIZE = 36;

    public static final double[] damage = new double[] { 1, 2, 3, 4, 6 };
    public static final double attackDelay = 5;
    public static final double bulletSize = 100;
    public static final int[] attackCount = new int[] { 1, 1, 2, 2, 3 };
    public static final double[] fieldSize = new double[] { 150, 160, 180, 200, 250 };

    public double attackTimer;

    @Override
    public void run() {
        if(level <= 0) return;

        if (GameManager.isGamePaused) return;

        attackTimer -= GameManager.FRAME_TIME;
        if(attackTimer < 0){
            attackTimer += attackDelay;

            int atkCount = attackCount[level-1] + Player.Instance.bonusProjectile;

            for (int i = 0; i < atkCount; i++) {
                GameManager.Instance.add(new BloodyGrimoirePP(
                        Math.random() * 360,
                        Player.Instance.getPosition(),
                        fieldSize[level - 1] * (1 + Player.Instance.bonusAttackSize),
                        damage[level - 1] * (1 + Player.Instance.bonusDamage)));
            }
        }
    }
}
