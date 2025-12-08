import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.extensions.math.Vector2;
import java.util.List;

public class FireAxe extends WeaponItem{
    public FireAxe(String name, String iconPath) {
        super(name, iconPath);
    }

    class FireAxePP extends Projectile {
        public static String path = "sprites/Weapon/FlameAxe/";

        FireAxePP(double dir, Vector2 startPos, double size, double damage){
            this.dir = dir;
            this.startPos = startPos;
            this.size = size;
            this.damage = damage;

            this.hitSize = HIT_SIZE * (size / 100);
            GameManager.AddCostumes(this, path, "FlameAxe_", 60);
        }

        @Override
        public void whenAddedToStage() {
            Initialize();
        }

        public void Initialize(){
            setDirection(dir);
            setPosition(startPos);
            setSize(size);

            speed = 50;
            deathTime = 15;
        }

        public static final double HIT_DELAY = 0.1;
        public double hitTimer;

        @Override
        public void run() {
            if (GameManager.isGamePaused) return;

            deathTime -= GameManager.FRAME_TIME;
            if(deathTime < 0) remove();

            move(speed * GameManager.FRAME_TIME);
            nextCostume();

            hitTimer += GameManager.FRAME_TIME;
            if(hitTimer > HIT_DELAY) {
                hitTimer -= HIT_DELAY;
                List<Enemy> enemies = getTouchingSprites(Enemy.class);
                if (enemies != null) {
                    for (Enemy e : enemies) {
                        if (distanceToSprite(e) < hitSize) {
                            e.getDamage(damage);
                            e.Knockback(5);
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

    public static final double HIT_SIZE = 15;

    public static final double[] damage = new double[] { 2, 4, 6, 8, 11 };
    public static final double[] attackSize = new double[] { 250, 270, 300, 340, 390 };
    public static final int[] attackCount = new int[] { 1, 1, 2, 2, 3 };
    public static final double[] attackDelay = new double[] { 6, 5, 4, 3.5, 3 };

    public double attackTimer;

    @Override
    public void run() {
        if(level <= 0) return;

        if (GameManager.isGamePaused) return;

        attackTimer -= GameManager.FRAME_TIME;
        if(attackTimer < 0){
            attackTimer += attackDelay[level-1] / (1 + Player.Instance.bonusAttackSpeed);

            int atkCount = attackCount[level-1] + Player.Instance.bonusProjectile;
            double baseAngle = Math.random() * 360;

            for (int i = 0; i < atkCount; i++) {
                // 시작 각과 끝 각 지정 (예: 3개는 -20~20, 4개는 -30~30)
                double startAngle = baseAngle + -10 * (atkCount - 1);
                double angle = startAngle + (i * 20);

                GameManager.Instance.add(new FireAxePP(
                        angle,
                        Player.Instance.getPosition(),
                        attackSize[level-1] * (1 + Player.Instance.bonusAttackSize),
                        damage[level-1] * (1 + Player.Instance.bonusDamage)));
            }
        }
    }
}