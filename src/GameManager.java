import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.Stage;
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

    public GameManager() {
        super(800, 600);

        Instance = this;

        player = new Player();
        this.add(player);

        this.add(new FireAxe());
        this.add(new ThornShooter());
        this.add(new SwordDance());

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
    }
    public static void main(String[] args) {
        new GameManager();
    }
    @Override
    public void run() {
        //Stop on Pause
        if (isGamePaused) return;

        waveTimer++;

        gameTimer -= (1.0 / 60.0);
        // 0초 이하로 내려가지 않게 고정
        if (gameTimer < 0) gameTimer = 0;


        if (waveTimer >= 900 || isFirstWave) {
            spawnWave();   // 몬스터 소환
            waveTimer = 0; // 0으로 초기화 (다시 15초 세기 시작)
            isFirstWave = false;
        }
    }

    private void spawnWave() {
        for (int i = 0; i < 4; i++)
        {
            this.add(new Enemy(Enemy.snail));
        }
        for (int i = 0; i < 3; i++)
        {
            this.add(new Enemy(Enemy.slime));
        }
        for (int i = 0; i < 2; i++) {
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


