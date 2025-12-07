import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.Stage;
import org.openpatch.scratch.extensions.timer.Timer;

import java.util.ArrayList;
import java.util.List;

public class GameManager extends Stage {

    public Player player;

    public static final ArrayList<ItemData> WEAPON_SLOT = new ArrayList<>();
    // 설명 이미지 설정
    public static final ArrayList<ItemData> SUPPORT_SLOT = new ArrayList<>();

    private ArrayList<ItemSelectButton> buttons = new ArrayList<>();

    public static final int ITEMMAX = 5;

    ItemSlot itemSlot;
    public static GameManager Instance;

    private XPBar xpBar;

    int waveTimer = 0;
    boolean isFirstWave = true;
    double gameTimer = 480.0;
    static boolean isGamePaused = false;

    public static Timer timer = new Timer();
    public int lastTime;

    public static double FRAME_TIME = 0;


    public GameManager() {
        super(800, 600);

        Instance = this;
        this.add(new Background());

        player = new Player();
        this.add(player);

        this.add(new FireAxe());
        this.add(new ThornShooter());
        this.add(new SwordDance());
        this.add(new IceGrave());
        this.add(new BoneStaff());
        this.add(new CycleSythe());
        this.add(new BloodyGrimoire());



        // 타이머 숫자 5개 생성, 화면에 붙이기
        for (int i = 0; i < 5; i++) {
            TimerNumber t = new TimerNumber(i);
            this.add(t);
        }

        // HPBar 생성, 화면에 붙이기
        HPBar hpBar = new HPBar();
        this.add(hpBar);

        xpBar = new XPBar();
        this.add(xpBar);
        itemSlot = new ItemSlot();
        this.add(itemSlot);

        timer = new Timer();
        lastTime = timer.millis();
    }

    public static void main(String[] args) {
        new GameManager();
    }
    @Override
    public void run() {
        //Stop on Pause
        if (isGamePaused) return;

        waveTimer++;

        FRAME_TIME = (double)(timer.millis() - lastTime) / 1000;
        System.out.println(FRAME_TIME);

        lastTime = timer.millis();


        gameTimer -= FRAME_TIME;
        // 0초 이하로 내려가지 않게 고정
        if (gameTimer < 0) gameTimer = 0;


        if (waveTimer >= 900 || isFirstWave) {
            spawnWave();   // 몬스터 소환
            waveTimer = 0; // 0으로 초기화 (다시 15초 세기 시작)
            isFirstWave = false;
        }

        separateAllEnemies(20);
        handleExpPickup();
    }

    public void GamePause(){
        isGamePaused = true;
        lastTime = timer.millis();
    }

    public void GamePlay(){
        isGamePaused = false;
        lastTime = timer.millis();
    }

    public void separateAllEnemies(double minDistance) {
        List<Enemy> enemies = findSpritesOf(Enemy.class);

        int n = enemies.size();
        for (int i = 0; i < n; i++) {
            Enemy a = enemies.get(i);
            for (int j = i + 1; j < n; j++) {   // i+1부터 -> 쌍을 한 번만 처리
                Enemy b = enemies.get(j);

                double dist = a.distanceToSprite(b);
                if (dist == 0) {
                    dist = 0.0001; // 완전 겹쳤을 때 NaN 방지용
                }

                if (dist < minDistance) {
                    double overlap = (minDistance - dist);

                    // 위치 가져오기
                    var posA = a.getPosition();  // 예: Vector2D, Point2D 등
                    var posB = b.getPosition();

                    double dx = posB.getX() - posA.getX();
                    double dy = posB.getY() - posA.getY();

                    double nx = dx / dist;
                    double ny = dy / dist;

                    double push = overlap / 2.0;

                    // 서로 반씩 밀어내기
                    a.setPosition(
                            posA.getX() - nx * push,
                            posA.getY() - ny * push
                    );
                    b.setPosition(
                            posB.getX() + nx * push,
                            posB.getY() + ny * push
                    );
                }
            }
        }
    }

    private void handleExpPickup() {
        // 필드에 존재하는 모든 경험치 오브젝트 가져오기
        List<XPOrb> orbs = findSpritesOf(XPOrb.class);

        double magnetRange = player.magnetRange;
        double magnetRangeSq = magnetRange * magnetRange;

        for (XPOrb orb : orbs) {
            // 거리 제곱으로 비교해 sqrt 비용 제거
            double dx = orb.getX() - player.getX();
            double dy = orb.getY() - player.getY();
            double distSq = dx * dx + dy * dy;

            if (distSq < magnetRangeSq) {
                player.exp += orb.expAmount;
                remove(orb); // GameManager.Instance.remove(orb) 등 네 구현에 맞게
            }
        }
    }

    private void spawnWave() {
        for (int i = 0; i < 40; i++)
        {
            this.add(new Enemy(Enemy.snail));
        }
        for (int i = 0; i < 30; i++)
        {
            this.add(new Enemy(Enemy.slime));
        }
        for (int i = 0; i < 20; i++) {
            this.add(new Enemy(Enemy.boar));
        }
        this.add(new ItemSelectButton(0,0,"Weapon","colon","sprites/Number/colon.png","sprites/Number/colon.png"));
        this.add(new ItemSelectButton(-250,0,"Weapon","colon","sprites/Number/colon.png","sprites/Number/colon.png"));
        this.add(new ItemSelectButton(250,0,"Weapon","colon","sprites/Number/colon.png","sprites/Number/colon.png"));
    }
    public static void AddCostumes(Sprite spr, String path, String name, int range){
        for (int i = 1; i <= range; i++) {
            spr.addCostume(name + i, path + name + i + ".png");
        }


    }

    public void addItem(ArrayList<ItemData> slot, String type, String imgPath) {

        // 이미 있는지 확인 → 있으면 레벨업
        for (ItemData data : slot) {
            if (data.imagePath.equals(imgPath)) {
                data.level++;
                itemSlot.refreshUI();
                return;
            }
        }

        // 없다 → 새로 추가
        if (slot.size() < ITEMMAX) {
            slot.add(new ItemData(type, imgPath, 1));
            itemSlot.refreshUI();
        }

    }
    public void registerButton(ItemSelectButton btn) {
        buttons.add(btn);
    }

    public void hideOtherButtons() {
        for (ItemSelectButton b : buttons)
        {
            b.remove();
        }
        buttons.clear();
    }
}


