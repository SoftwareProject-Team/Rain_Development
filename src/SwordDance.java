import org.openpatch.scratch.RotationStyle;
import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.extensions.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SwordDance extends WeaponItem{
    class SwordDanceObject extends Sprite {
        public static String path = "sprites/Weapon/SwordDance/";

        double size;
        double dir;
        Vector2 startPos;
        double damage;

        public double deathTimer;
        public List<Enemy> alreadyHit = new ArrayList<>();

        SwordDanceObject(double dir, Vector2 startPos, double size, double damage){
            this.dir = dir;
            if(dir > 180){
                this.startPos = new Vector2(startPos.getX() - 30, startPos.getY());
            }
            else {
                this.startPos = new Vector2(startPos.getX() + 30, startPos.getY());
            }

            this.size = size;
            this.damage = damage;

            GameManager.AddCostumes(this, path, "SwordDance_", 4);
        }

        @Override
        public void whenAddedToStage() {
            Initialize();
        }

        public void Initialize(){
            setDirection(dir + (Math.random() - 0.5) * 20);

            setPosition(startPos);
            setSize(size);

            deathTimer = 0.20;
        }

        double animDelay = 0;

        @Override
        public void run() {
            if (GameManager.isGamePaused) return;

            deathTimer -= GameManager.FRAME_TIME;
            if(deathTimer < 0) remove();

            animDelay += GameManager.FRAME_TIME;
            double time = 0.05;
            if(animDelay > time){
                nextCostume();
                animDelay -= time;
            }

            List<Enemy> enemies = getTouchingSprites(Enemy.class);
            if(enemies != null){
                for (int i = 0; i < enemies.size(); i++) {
                    Enemy e = enemies.get(i);
                    if(!alreadyHit.contains(e)) {
                        e.getDamage(damage);
                        alreadyHit.add(e);
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
        level = 1; //임시 레벨 설정
    }

    public static final double[] damage = new double[] { 2, 4, 6, 8, 10 };
    public static final double[] attackDelay = new double[] { 5, 4.5, 4, 3.5, 2.5 };
    public static final int[] attackRepeat = new int[] { 2, 3, 4, 5, 7 };
    public static final double[] attackRepeatDelay = new double[] { 0.5, 0.4, 0.3, 0.2, 0.1 };
    public static final double[] attackSize = new double[] { 40, 45, 50, 55, 75 };

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
                    GameManager.Instance.add(new SwordDanceObject(
                            Player.Instance.getDirection(),
                            Player.Instance.getPosition(),
                            attackSize[level-1] * (1 + Player.Instance.bonusAttackSize),
                            damage[level - 1] * (1 + Player.Instance.bonusDamage)));
                }
            }
        }
    }
}