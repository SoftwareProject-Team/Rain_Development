import org.openpatch.scratch.RotationStyle;
import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.extensions.timer.Timer;
import org.openpatch.scratch.KeyCode;

public class Enemy extends Sprite {

    public Player player;

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

    public static String path = "sprites/Enemy/";

    Enemy(EnemyData data){
        this.data = data;
        player = new Player();
    }


    public static EnemyData boar = new EnemyData(
            "boar",
            8,
            8,
            5,
            22f,
            50,
            15
    );

    public static EnemyData slime = new EnemyData(
            "redSlime",
            6,
            10,
            5,
            20f,
            10,
            25
    );

    public static EnemyData snail = new EnemyData(
            "snail",
            8,
            8,
            3,
            30f,
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

        // 테스트용 코드, K 누르면 올킬
        if (isKeyPressed(KeyCode.VK_K)) {
            Die();
            return; // 이미 죽었으므로 이후 로직(이동, 애니메이션 등)은 실행하지 않음
        }

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
        move(data.speed * 0.016);
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
        if(distanceToSprite(player) < getSize()) player.getDamage();
    }

    private void Die() {
        GameManager.Instance.add(new XPOrb(this.getX(), this.getY(), (int)this.data.exp));
        GameManager.Instance.remove(this);
    }

    public void setPosition() {
        this.setX((Math.random() - 0.5) * 800); // 오른쪽 끝
        this.setY((Math.random() - 0.5) * 600); // 위아래 랜덤 위치
        do {
            this.setX((Math.random() - 0.5) * 800);
            this.setY((Math.random() - 0.5) * 600);
        } while(distanceToSprite(player) < 150);
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
    }
}