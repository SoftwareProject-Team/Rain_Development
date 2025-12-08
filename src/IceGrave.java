import org.openpatch.scratch.RotationStyle;
import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.extensions.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IceGrave extends WeaponItem{
    public IceGrave(String name, String iconPath) {
        super(name, iconPath);
    }

    class IceGraveObject extends Sprite {
        public static String path = "sprites/Weapon/IceGrave/";

        double size;
        Vector2 startPos;
        double damage;
        double hitSize;

        public double deathTimer;
        public List<Enemy> alreadyHit = new ArrayList<>();

        IceGraveObject(Vector2 startPos, double size, double damage){
            this.startPos = startPos;
            this.size = size;
            this.damage = damage;
            this.hitSize = HIT_SIZE * (size / 100);

            GameManager.AddCostumes(this, path, "IceGrave_", 15);
        }

        @Override
        public void whenAddedToStage() {
            Initialize();
        }

        public void Initialize(){
            setPosition(startPos);
            setSize(size);

            deathTimer = 0.5;
        }

        double animDelay = 0;
        int animCount = 0;

        @Override
        public void run() {
            if (GameManager.isGamePaused) return;

            deathTimer -= GameManager.FRAME_TIME;
            if(deathTimer < 0) remove();

            animDelay += GameManager.FRAME_TIME;
            if(animDelay > 0.03 && animCount < 15){
                animCount++;
                animDelay -= 0.03;
                nextCostume();
            }

            List<Enemy> enemies = getTouchingSprites(Enemy.class);
            if(enemies != null){
                for (int i = 0; i < enemies.size(); i++) {
                    Enemy e = enemies.get(i);
                    if(distanceToSprite(e) < hitSize && !alreadyHit.contains(e)) {
                        e.getDamage(damage);
                        alreadyHit.add(e);
                        e.Knockback(30);

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

    public static final double HIT_SIZE = 43;

    public static final double[] damage = new double[] { 4, 7, 10, 16, 24 };
    public static final double[] attackDelay = new double[] { 4, 3.5, 3, 2, 1.5 };
    public static final double[] attackSize = new double[] { 200, 230, 260, 290, 350 };

    public double attackTimer;

    @Override
    public void run() {
        if(level <= 0) return;

        if (GameManager.isGamePaused) return;

        attackTimer -= GameManager.FRAME_TIME;
        if(attackTimer < 0){
            attackTimer += attackDelay[level - 1] / (1 + Player.Instance.bonusAttackSpeed);
            GameManager.Instance.add(new IceGraveObject(
                    Player.Instance.getPosition(),
                    attackSize[level-1] * (1 + Player.Instance.bonusAttackSize),
                    damage[level - 1] * (1 + Player.Instance.bonusDamage)));
        }
    }
}