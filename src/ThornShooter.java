import org.openpatch.scratch.extensions.math.Vector2;
import java.util.List;

public class ThornShooter extends WeaponItem{
    public ThornShooter(String name, String iconPath) {
        super(name, iconPath);
    }

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
            deathTime = 2;

            if(Player.Instance.nearestEnemy != null) {
                pointTowardsSprite(Player.Instance.nearestEnemy);
                setDirection(getDirection() + (Math.random() * 20 - 10));
            }
        }

        @Override
        public void run() {
            if (GameManager.isGamePaused) return;

            deathTime -= GameManager.FRAME_TIME;
            if(deathTime < 0) remove();

            move(speed * GameManager.FRAME_TIME);

            List<Enemy> enemies = getTouchingSprites(Enemy.class);
            if(enemies != null){
                for (int i = 0; i < enemies.size(); i++) {
                    Enemy e = enemies.get(i);
                    if(distanceToSprite(e) < hitSize) {
                        e.getDamage(damage);
                        remove();
                        e.Knockback(10);
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

    public static final double HIT_SIZE = 15;

    public static final double[] damage = new double[] { 3, 6, 9, 12, 15 };
    public static final double[] attackDelay = new double[] { 0.8, 0.55, 0.4, 0.25, 0.15 };
    public static final int size = 100;

    public double attackTimer;

    @Override
    public void run() {
        if(level <= 0) return;

        if (GameManager.isGamePaused) return;

        attackTimer -= GameManager.FRAME_TIME;
        if(attackTimer < 0){
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