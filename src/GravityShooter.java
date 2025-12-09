import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.extensions.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class GravityShooter extends WeaponItem {
    public GravityShooter(String name, String iconPath) {
        super(name, iconPath);
    }

    class GravityShooterPP extends Projectile {
        public static String path = "sprites/Weapon/GravityShooter/";

        GravityShooterPP(double dir, Vector2 startPos, double size, double damage){
            this.dir = dir;
            this.startPos = startPos;
            this.size = size;
            this.damage = damage;

            GameManager.AddCostumes(this, path, "GravityShooterPP_", 15);
        }

        @Override
        public void whenAddedToStage() {
            Initialize();
        }

        public void Initialize(){
            setDirection(dir);
            setPosition(startPos);
            setSize(bulletSize);

            speed = 250;
            deathTime = 1.5;

            if(Player.Instance.nearestEnemy != null) {
                pointTowardsSprite(Player.Instance.nearestEnemy);
                setDirection(getDirection() + (Math.random() * 20 - 10));
            }
        }

        @Override
        public void run() {
            if (GameManager.isGamePaused) return;

            deathTime -= GameManager.FRAME_TIME;
            if(deathTime < 0) summonField();

            move(speed * GameManager.FRAME_TIME);
            nextCostume();

            List<Enemy> enemies = getTouchingSprites(Enemy.class);
            if(enemies != null){
                for (int i = 0; i < enemies.size(); i++) {
                    Enemy e = enemies.get(i);
                    if(distanceToSprite(e) < BULLET_HIT_SIZE) {
                        summonField();
                    }
                }
            }
        }

        public void summonField(){
            GameManager.Instance.add(new GravityShooterObject(
                    getPosition(),
                    size,
                    damage));

            remove();
        }
    }

    class GravityShooterObject extends Sprite {
        public static String path = "sprites/Weapon/GravityShooter/";

        double size;
        Vector2 startPos;
        double damage;
        double hitSize;

        public double deathTime;

        GravityShooterObject(Vector2 startPos, double size, double damage){
            this.startPos = startPos;
            this.size = size;
            this.damage = damage;
            this.hitSize = HIT_SIZE * (size / 100);

            GameManager.AddCostumes(this, path, "GravityShooterObj_", 18);
        }

        @Override
        public void whenAddedToStage() {
            Initialize();
        }

        public void Initialize(){
            setPosition(startPos);
            setSize(size);

            deathTime = 0.5;
        }

        public List<Enemy> alreadyHit = new ArrayList<>();

        double animDelay = 0;
        int animCount = 0;

        @Override
        public void run() {
            if (GameManager.isGamePaused) return;

            deathTime -= GameManager.FRAME_TIME;
            if(deathTime < 0) remove();

            animDelay += GameManager.FRAME_TIME;
            if(animDelay > 0.03 && animCount < 18){
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
                        e.Knockback(15);

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

    public static final double HIT_SIZE = 40;

    public static final double[] damage = new double[] { 5, 6, 7, 8, 10 };
    public static final double bulletSize = 100;
    public static final double BULLET_HIT_SIZE = 25;

    public static final double[] attackDelay = new double[] { 6.5, 6, 5, 4, 3 };
    public static final int[] attackRepeat = new int[] { 1, 2, 3, 4, 5 };
    public static final double[] attackRepeatDelay = new double[] { 0.8, 0.8, 0.6, 0.4, 0.2 };
    public static final double[] attackSize = new double[] { 150, 170, 190, 230, 280 };

    public double attackTimer;
    public double repeatTimer;
    public double repeatCounter;

    @Override
    public void run() {
        if(level <= 0) return;

        if (GameManager.isGamePaused) return;

        attackTimer -= GameManager.FRAME_TIME ;
        if(attackTimer < 0){
            if(repeatCounter <= 0) {
                attackTimer += attackDelay[level - 1] / (1 + Player.Instance.bonusAttackSpeed);
                repeatCounter = attackRepeat[level-1];
            }
            else {
                repeatTimer -= GameManager.FRAME_TIME;
                if(repeatTimer < 0){
                    repeatCounter--;
                    repeatTimer = attackRepeatDelay[level-1] / (1 + Player.Instance.bonusAttackSpeed);
                    if(Player.Instance.nearestEnemy != null) {
                        GameManager.Instance.add(new GravityShooterPP(
                                0,
                                Player.Instance.getPosition(),
                                attackSize[level-1] * (1 + Player.Instance.bonusAttackSize),
                                damage[level - 1] * (1 + Player.Instance.bonusDamage)));
                    }
                }
            }
        }
    }
}
