import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.extensions.math.Vector2;
import java.util.List;

public class BoneStaff extends WeaponItem{
    class BoneStaffPP extends Projectile {
        public static String path = "sprites/Weapon/BoneStaff/";

        BoneStaffPP(double dir, Vector2 startPos, double size, double damage){
            this.dir = dir;
            this.startPos = startPos;
            this.size = size;
            this.damage = damage;

            this.hitSize = HIT_SIZE * (size / 100);
            addCostume("bone", path + "bone.png");
        }

        @Override
        public void whenAddedToStage() {
            Initialize();
        }

        public void Initialize(){
            setPosition(startPos);
            setSize(size);
            setDirection(dir);

            speed = 150;
            deathTime = 1;
        }

        @Override
        public void run() {
            if (GameManager.isGamePaused) return;

            deathTime -= GameManager.FRAME_TIME;
            if(deathTime < 0) remove();

            move(speed * GameManager.FRAME_TIME);

            List<Enemy> enemies = getTouchingSprites(Enemy.class);
            if(enemies != null){
                for (Enemy e : enemies) {
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

    public static final double HIT_SIZE = 30;

    public static final double[] damage = new double[] { 3, 6, 9, 12, 15 };
    public static final double[] attackDelay = new double[] { 1, 0.85, 0.7, 0.5, 0.3 };
    public static final int[] attackCount = new int[] { 2, 3, 4, 5, 6 };
    public static final int size = 50;

    public double attackTimer;

    boolean changeAngle;

    @Override
    public void run() {
        if(level <= 0) return;

        if (GameManager.isGamePaused) return;

        attackTimer-=GameManager.FRAME_TIME;
        if(attackTimer < 0){
            attackTimer += attackDelay[level-1] / (1 + Player.Instance.bonusAttackSpeed);

            int ac = attackCount[level-1] + Player.Instance.bonusProjectile;
            double angleScale = 360 / ac;
            double change = 0;
            if(changeAngle) change = angleScale / 2;
            changeAngle = !changeAngle;
            for (int i = 0; i < ac; i++) {
                GameManager.Instance.add(new BoneStaffPP(
                        angleScale * i + change,
                        Player.Instance.getPosition(),
                        size * (1 + Player.Instance.bonusAttackSize),
                        damage[level - 1] * (1 + Player.Instance.bonusDamage)));
            }
        }
    }
}