import org.openpatch.scratch.KeyCode;
import org.openpatch.scratch.RotationStyle;
import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.extensions.math.Vector2;

import java.util.List;

class Player extends Sprite {

    public static String path = "sprites/Player/";

    public static Player Instance;

    public int exp;
    public int hp;
    public int maxHp;
    public int level;
    public Vector2 pos;

    public double speed;
    public int magnetRange;


    public double   bonusAttackSpeed;
    public double   bonusDamage;
    public double   bonusAttackSize;
    public double   bonusSpeed;
    public int      bonusMaxHp;
    public int      bonusProjectile;
    public double   bonusExp;
    public int      bonusMagnetRange;

    //이동
    public double inputX;
    public double inputY;

    //애니메이션
    public String anim;
    public int frameCount;
    public int frameDelay;

    public int frame;
    public int frameTimer;

    //피격
    public static final int INVINCIBLE_TIME = 2;
    public int damageTimer;

    Player() {
        GameManager.AddCostumes(this, path, "Idle", 2);
        GameManager.AddCostumes(this, path, "Run", 8);

        this.setOnEdgeBounce(true);
        this.setDirection(0);
    }

    @Override
    public void whenAddedToStage() {
        super.whenAddedToStage();
        Initialize();
    }

    private void Initialize(){
        this.setRotationStyle(RotationStyle.LEFT_RIGHT);
        this.setSize(150);

        frame = 1;

        move(new Vector2(0, 0));

        bonusAttackSpeed = 0;
        bonusDamage = 0;
        bonusAttackSize = 0;
        bonusSpeed = 0;
        bonusMaxHp = 0;
        bonusProjectile = 0;
        bonusExp = 0;
        bonusMagnetRange = 0;


        speed = 50;
        hp = 20;
        maxHp = 20;
        exp = 0;
        level = 1;
        magnetRange = 15;

        Instance = this;
    }

    //매 프레임마다 실행됨 (60프레임), 기존 코드 DeltaTime 사이클 대체
    public void run() {
        if (GameManager.isGamePaused) return;

        move();
        animation();
        cooldown();

        checkNearestEnemy();
    }

    public Enemy nearestEnemy;

    public void checkNearestEnemy(){
        nearestEnemy = null;
        double min = 1000;

        List<Enemy> enemies = GameManager.Instance.findSpritesOf(Enemy.class);

        for (int i = 0; i < enemies.size(); i++) {
            double distance = distanceToSprite(enemies.get(i));
            if(distance < min){
                nearestEnemy = enemies.get(i);
                min = distance;
            }
        }
    }

    public void move(){
        inputX = 0.0; inputY = 0.0;
        if(isKeyPressed(KeyCode.VK_D)) inputX += 1.0;
        if(isKeyPressed(KeyCode.VK_A)) inputX += -1.0;
        if(isKeyPressed(KeyCode.VK_W)) inputY += 1.0;
        if(isKeyPressed(KeyCode.VK_S)) inputY += -1.0;


        double scalar = Math.sqrt(inputX * inputX + inputY * inputY);
        if(scalar == 0) scalar = 0.1;

        double dirX = inputX / scalar;
        double dirY = inputY / scalar;

        changePosition(new Vector2(dirX * speed * 0.016, dirY * speed * 0.016));
        pos = getPosition();

        if(scalar > 0.9) playAnim("Run", 8, 5);
        else playAnim("Idle", 2, 20);

        if(dirX > 0) pointInDirection(90);
        if(dirX < 0) pointInDirection(-90);
    }

    public void playAnim(String anim, int frameCount, int frameDelay){
        if(this.anim == anim) return;

        this.anim = anim;
        this.frameCount = frameCount;
        this.frameDelay = frameDelay;

        this.frame = 1;
        this.frameTimer = 0;
    }

    public void animation(){
        frameTimer--;
        if(frameTimer <= 0){
            frameTimer = frameDelay;
            if(frame < frameCount){
                frame++;
            }
            else frame = 1;

            switchCostume(anim+frame);
        }
    }

    public void cooldown(){
        damageTimer--;
    }

    public void getDamage() {
        if(damageTimer < 0) {
            hp -= 1;
            damageTimer = INVINCIBLE_TIME;
        }
    }
}
