import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.extensions.math.Vector2;
import java.util.List;

public class FireAxe extends WeaponItem{
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
            deathFrame = 900;

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
                    if(distanceToSprite(e) < hitSize && !alreadyHit.contains(e)) {
                        e.getDamage(damage);
                        alreadyHit.add(e);
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

    public static final double HIT_SIZE = 20;

    public static final double[] damage = new double[] { 5, 8, 11, 15, 25 };
    public static final double[] attackSize = new double[] { 150, 170, 200, 230, 300 };
    public static final int[] attackCount = new int[] { 1, 1, 2, 2, 3 };
    public static final int[] attackDelay = new int[] { 360, 300, 240, 210, 180 };

    public double attackTimer;

    @Override
    public void run() {
        if(level <= 0) return;

        if (GameManager.isGamePaused) return;

        attackTimer--;
        if(attackTimer < 1){
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