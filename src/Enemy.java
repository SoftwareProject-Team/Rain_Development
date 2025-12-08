import org.openpatch.scratch.RotationStyle;
import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.extensions.math.Vector2;
import org.openpatch.scratch.extensions.timer.Timer;
import org.openpatch.scratch.KeyCode;
import java.util.List;

public class Enemy extends Sprite {

    public static class EnemyData{
        public String   anim;
        public int      frameCount;
        public int      frameDelay;

        public double   hp;
        public double   speed;
        public double   exp;
        public double   size;

        public EnemyData(
                String anim,
                int frameCount,
                int frameDelay,
                double hp,
                double speed,
                double exp,
                double size){
            this.anim = anim;
            this.frameCount = frameCount;
            this.frameDelay = frameDelay;
            this.hp = hp;
            this.speed = speed;
            this.exp = exp;
            this.size = size;
        }
    }

    public EnemyData data;
    public double hp;

    public static String path = "sprites/Enemy/";

    Enemy(EnemyData data){
        this.data = data;
    }

    public static EnemyData snail = new EnemyData(
            "snail",
            8,
            8,
            3,
            15f,
            1,
            25
    );

    public static EnemyData slime = new EnemyData(
            "slime",
            6,
            10,
            5,
            20f,
            2,
            25
    );

    public static EnemyData bee = new EnemyData(
            "bee",
            4,
            15,
            15,
            30f,
            5,
            20
    );

    public static EnemyData boar = new EnemyData(
            "boar",
            8,
            8,
            30,
            20f,
            5,
            25
    );

    public static EnemyData flower = new EnemyData(
            "giantFlower",
            4,
            15,
            150,
            30f,
            30,
            28
    );

    public static EnemyData redSnail = new EnemyData(
            "redSnail",
            8,
            8,
            35,
            40f,
            3,
            25
    );

    public static EnemyData redSlime = new EnemyData(
            "redSlime",
            6,
            10,
            50,
            35f,
            4,
            25
    );

    public static EnemyData redBee = new EnemyData(
            "redBee",
            4,
            15,
            100,
            45f,
            10,
            20
    );

    public static EnemyData blackBoar = new EnemyData(
            "blackBoar",
            8,
            8,
            250,
            40f,
            20,
            25
    );

    public static EnemyData bat = new EnemyData(
            "bat",
            5,
            15,
            450,
            80f,
            200,
            28
    );

    public static EnemyData poisonFlower = new EnemyData(
            "poisonGiantFlower",
            4,
            15,
            700,
            55f,
            200,
            28
    );

    public static EnemyData undead = new EnemyData(
            "snail",
            8,
            4,
            999999999,
            150f,
            1,
            25
    );


    boolean state = false;

    Timer spawnDelay;

    public int frame;
    public int frameTimer;

    @Override
    public void run() {
        // 일시정지 아닐 때만 실행 (GameManager에서 확인)
        if (GameManager.isGamePaused) return;

        if (!state && spawnDelay.afterMillis(1000)) {
            state = true;
        }

        if(state) {
            Move(); // 이동
            Animation();   // 애니메이션
            Damage();  // 피격 판정 (Damage)
        }
    }

    protected void Move() {
        pointTowardsSprite(Player.Instance);
        move(data.speed * GameManager.FRAME_TIME);
    }

    protected void Animation() {
        frameTimer--;
        if(frameTimer <= 0){
            frameTimer = data.frameDelay;
            if(frame < data.frameCount){
                frame++;
            }
            else frame = 1;

            switchCostume(data.anim+frame);
        }
    }
    protected void Damage() {
        if(distanceToSprite(Player.Instance) < data.size) {
            Player.Instance.getDamage();
            Knockback(5);
        }
    }

    public void getDamage(double damage){
        if(!state) return;

        hp -= damage;
        if(hp <= 0){
            Die();
        }
    }

    public void Knockback(double multiplier){
        if(!state) return;

        move(multiplier * -1 * GameManager.FRAME_TIME * data.speed);
    }

    private void Die() {
        GameManager.Instance.add(new XPOrb(this.getX(), this.getY(), (int)this.data.exp));
        GameManager.Instance.remove(this);
    }

    public void setPosition() {
        this.setX((Math.random() - 0.5) * 780); // 오른쪽 끝
        this.setY((Math.random() - 0.5) * 580); // 위아래 랜덤 위치
        do {
            this.setX((Math.random() - 0.5) * 780);
            this.setY((Math.random() - 0.5) * 580);
        } while(distanceToSprite(Player.Instance) < 150);
    }

    @Override
    public void whenAddedToStage() {
        super.whenAddedToStage();
        Initialize();
    }

    public void Initialize(){
        this.setRotationStyle(RotationStyle.LEFT_RIGHT);

        GameManager.AddCostumes(this, path, data.anim, data.frameCount);
        addCostume("spawn", path+"spawn.png");
        setSize(75);

        frame = 1;
        frameTimer = 0;

        setPosition();
        switchCostume("spawn");
        spawnDelay = new Timer();

        hp = data.hp;
    }
}