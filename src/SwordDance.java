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

        public int deathFrame;
        public List<Enemy> alreadyHit = new ArrayList<>();

        SwordDanceObject(double dir, Vector2 startPos, double size, double damage){
            this.dir = dir;
            System.out.println(dir);
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
            this.setRotationStyle(RotationStyle.LEFT_RIGHT);

            setDirection(dir);
            setPosition(startPos);
            setSize(size);

            deathFrame = 15;

            goToBackLayer();
        }

        int animDelay = 0;

        @Override
        public void run() {
            if (GameManager.isGamePaused) return;

            deathFrame--;
            if(deathFrame < 0) remove();

            animDelay++;
            if(animDelay % 4 == 0 && animDelay < 17){
                nextCostume();
            }

            List<Enemy> enemies = getTouchingSprites(Enemy.class);
            if(enemies != null){
                for (int i = 0; i < enemies.size(); i++) {
                    Enemy e = enemies.get(i);
                    if(!alreadyHit.contains(e)) {
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

    public static final double[] damage = new double[] { 2, 4, 6, 8, 10 };
    public static final int[] attackDelay = new int[] { 300, 270, 240, 210, 150 };
    public static final int[] attackRepeat = new int[] { 2, 3, 4, 5, 7 };
    public static final int[] attackRepeatDelay = new int[] { 30, 25, 20, 15, 10 };
    public static final double[] attackSize = new double[] { 150, 170, 200, 230, 300 };

    public double attackTimer;
    public double repeatTimer;
    public double repeatCounter;

    @Override
    public void run() {
        if(level <= 0) return;

        if (GameManager.isGamePaused) return;

        attackTimer--;
        if(attackTimer < 1){
            if(repeatCounter <= 0) {
                attackTimer += attackDelay[level - 1] / (1 + Player.Instance.bonusAttackSpeed);
                repeatCounter = attackRepeat[level-1];
            }
            else {
                repeatTimer--;
                if(repeatTimer < 1){
                    repeatCounter--;
                    repeatTimer = attackRepeatDelay[level-1];
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