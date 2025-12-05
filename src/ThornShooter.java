import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.extensions.math.Vector2;
import java.util.List;
import java.util.Random;

public class ThornShooter extends WeaponItem{
    class ThornShooterPP extends Projectile {
        public static String path = "sprites/Weapon/ThornShooter/";

        ThornShooterPP(double dir, Vector2 startPos, double size, double damage){
            this.dir = dir;
            this.startPos = startPos;
            this.size = size;
            this.damage = damage;

            this.hitSize = HIT_SIZE * (size / 100);
            addCostume("thorn", path + "thorn.png");
        }

        @Override
        public void whenAddedToStage() {
            Initialize();
        }

        public void Initialize(){
            setDirection(dir);
            setPosition(startPos);
            setSize(size);

            speed = 150;
            deathFrame = 300;

            if(Player.Instance.nearestEnemy != null) {
                pointTowardsSprite(Player.Instance.nearestEnemy);
                setDirection(getDirection() + (Math.random() * 20 - 10));
            }
            goToBackLayer();
        }

        @Override
        public void run() {
            if (GameManager.isGamePaused) return;

            deathFrame--;
            if(deathFrame < 0) remove();

            move(speed * 0.016);
            nextCostume();

            List<Enemy> enemies = getTouchingSprites(Enemy.class);
            if(enemies != null){
                for (int i = 0; i < enemies.size(); i++) {
                    Enemy e = enemies.get(i);
                    if(distanceToSprite(e) < hitSize) {
                        e.getDamage(damage);
                        remove();
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
        level = 5; //임시 레벨 설정
    }

    public static final double HIT_SIZE = 12;

    public static final double[] damage = new double[] { 1, 2, 3, 4, 5 };
    public static final int[] attackDelay = new int[] { 60, 48, 36, 24, 12 };
    public static final int size = 100;

    public double attackTimer;

    @Override
    public void run() {
        if(level <= 0) return;

        if (GameManager.isGamePaused) return;

        attackTimer--;
        if(attackTimer < 1){
            attackTimer += attackDelay[level-1] / (1 + Player.Instance.bonusAttackSpeed);

            if(Player.Instance.nearestEnemy != null) {
                GameManager.Instance.add(new ThornShooterPP(
                        0,
                        Player.Instance.getPosition(),
                        size * (1 + Player.Instance.bonusAttackSize),
                        damage[level - 1] * (1 + Player.Instance.bonusDamage)));
            }
        }
    }
}