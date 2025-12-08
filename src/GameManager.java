import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.Stage;
import org.openpatch.scratch.extensions.timer.Timer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameManager extends Stage {

    public Player player;

    public static final ArrayList<Item> WEAPON_SLOT = new ArrayList<>();
    // 설명 이미지 설정
    public static final ArrayList<Item> SUPPORT_SLOT = new ArrayList<>();

    public List<Item> WeaponItems = new ArrayList<>();
    public List<Item> EffectItems = new ArrayList<>();

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

    private boolean startGame = false;

    public GameManager() {
        super(800, 600);

        Instance = this;
        this.add(new Background());

        player = new Player();
        this.add(player);

        WeaponItems.add(new FireAxe("Item1", "Item1"));
        WeaponItems.add(new SwordDance("Item2", "Item2"));
        WeaponItems.add(new ThornShooter("Item3", "Item3"));
        WeaponItems.add(new IceGrave("Item4", "Item4"));
        WeaponItems.add(new BloodyGrimoire("Item5", "Item5"));
        WeaponItems.add(new BoneStaff("Item6", "Item6"));
        WeaponItems.add(new CycleSythe("Item7", "Item7"));
        WeaponItems.add(new GravityShooter("Item8", "Item8"));

        EffectItems.add(new Lemon("Item9", "Item9"));
        EffectItems.add(new Meat("Item10", "Item10"));
        EffectItems.add(new Bomb("Item11", "Item11"));
        EffectItems.add(new Feather("Item12", "Item12"));
        EffectItems.add(new Lettuce("Item13", "Item13"));
        EffectItems.add(new MajorBook("Item14", "Item14"));
        EffectItems.add(new Cake("Item15", "Item15"));
        EffectItems.add(new IronIngot("Item16", "Item16"));

        for (Sprite spr : WeaponItems){
            this.add(spr);
        }
        for (Sprite spr : EffectItems){
            this.add(spr);
        }

        // HPBar 생성, 화면에 붙이기
        HPBar hpBar = new HPBar();
        this.add(hpBar);

        xpBar = new XPBar();
        this.add(xpBar);

        // 타이머 숫자 5개 생성, 화면에 붙이기
        for (int i = 0; i < 5; i++) {
            TimerNumber t = new TimerNumber(i);
            this.add(t);
        }

        itemSlot = new ItemSlot();
        this.add(itemSlot);

        timer = new Timer();
        lastTime = timer.millis();

        millis = 0;

        waveState = 0;

        OpenWeaponSelect();

        startGame = true;
        lastTime = timer.millis();
    }

    public int waveState = 0;


    public static void main(String[] args) {
        new GameManager();
    }

    @Override
    public void run() {
        if(!startGame){
            return;
        }

        //Stop on Pause
        if (isGamePaused) return;

        FRAME_TIME = (double)(timer.millis() - lastTime) / 1000;
        millis += timer.millis() - lastTime;

        lastTime = timer.millis();

        gameTimer -= FRAME_TIME;

        // 0초 이하로 내려가지 않게 고정
        if (gameTimer < 0) gameTimer = 0;

        Wave();

        separateAllEnemies(20);
        handleExpPickup();
    }

    public int millis;



    CustomTimer waveTimer1 = new CustomTimer();
    CustomTimer waveTimer2 = new CustomTimer();
    CustomTimer waveTimer3 = new CustomTimer();
    CustomTimer waveTimer4 = new CustomTimer();
    CustomTimer waveTimer5 = new CustomTimer();
    CustomTimer waveTimer6 = new CustomTimer();

    void ResetWaveTimer() {
        waveTimer1.reset();
        waveTimer2.reset();
        waveTimer3.reset();
        waveTimer4.reset();
        waveTimer5.reset();
        waveTimer6.reset();
    }

    void Wave(){
        switch (waveState) {
            case 0:
                if(waveTimer1.everyMillis(400)) {
                    this.add(new Enemy(Enemy.snail));
                }

                if(waveTimer1.afterMillis(30000)){
                    ResetWaveTimer();

                    waveState = 1;

                    for (int i = 0; i < 8; i++)
                    {
                        this.add(new Enemy(Enemy.snail));
                    }
                    for (int i = 0; i < 6; i++)
                    {
                        this.add(new Enemy(Enemy.slime));
                    }
                }
                break;
            case 1:
                if(waveTimer1.everyMillis(500)) {
                    this.add(new Enemy(Enemy.snail));
                }
                if(waveTimer2.everyMillis(700)) {
                    this.add(new Enemy(Enemy.slime));
                }

                if(waveTimer1.afterMillis(30000)){
                    ResetWaveTimer();
                    waveState = 2;

                    for (int i = 0; i < 9; i++)
                    {
                        this.add(new Enemy(Enemy.slime));
                    }
                    for (int i = 0; i < 12; i++)
                    {
                        this.add(new Enemy(Enemy.snail));
                    }
                }
                break;
            case 2:
                if(waveTimer1.everyMillis(400)) {
                    this.add(new Enemy(Enemy.slime));
                }
                if(waveTimer2.everyMillis(3000)) {
                    this.add(new Enemy(Enemy.boar));
                }
                if(waveTimer3.everyMillis(500)) {
                    this.add(new Enemy(Enemy.snail));
                }

                if(waveTimer1.afterMillis(30000)){
                    ResetWaveTimer();
                    waveState = 3;

                    for (int i = 0; i < 10; i++)
                    {
                        this.add(new Enemy(Enemy.snail));
                    }
                    for (int i = 0; i < 15; i++)
                    {
                        this.add(new Enemy(Enemy.slime));
                    }
                    for (int i = 0; i < 5; i++)
                    {
                        this.add(new Enemy(Enemy.bee));
                    }
                    for (int i = 0; i < 3; i++)
                    {
                        this.add(new Enemy(Enemy.boar));
                    }
                }
                break;
            case 3:
                if(waveTimer1.everyMillis(200)) {
                    this.add(new Enemy(Enemy.slime));
                }
                if(waveTimer2.everyMillis(1000)) {
                    this.add(new Enemy(Enemy.bee));
                }
                if(waveTimer3.everyMillis(500)) {
                    this.add(new Enemy(Enemy.snail));
                }
                if(waveTimer4.everyMillis(2000)) {
                    this.add(new Enemy(Enemy.boar));
                }

                if(waveTimer1.afterMillis(30000)){
                    ResetWaveTimer();
                    waveState = 4;

                    for (int i = 0; i < 20; i++)
                    {
                        this.add(new Enemy(Enemy.slime));
                    }
                    for (int i = 0; i < 10; i++)
                    {
                        this.add(new Enemy(Enemy.boar));
                    }
                    for (int i = 0; i < 3; i++)
                    {
                        this.add(new Enemy(Enemy.flower));
                    }
                }
                break;
            case 4:
                if(waveTimer1.everyMillis(500)) {
                    this.add(new Enemy(Enemy.slime));
                }
                if(waveTimer2.everyMillis(400)) {
                    this.add(new Enemy(Enemy.bee));
                }
                if(waveTimer3.everyMillis(900)) {
                    this.add(new Enemy(Enemy.snail));
                }
                if(waveTimer4.everyMillis(1000)) {
                    this.add(new Enemy(Enemy.boar));
                }

                if(waveTimer1.afterMillis(30000)){
                    ResetWaveTimer();
                    waveState = 5;

                    for (int i = 0; i < 10; i++)
                    {
                        this.add(new Enemy(Enemy.bee));
                    }
                    for (int i = 0; i < 10; i++)
                    {
                        this.add(new Enemy(Enemy.boar));
                    }
                    for (int i = 0; i < 10; i++)
                    {
                        this.add(new Enemy(Enemy.flower));
                    }
                }
                break;
            case 5:
                if(waveTimer1.everyMillis(500)) {
                    this.add(new Enemy(Enemy.slime));
                }
                if(waveTimer2.everyMillis(400)) {
                    this.add(new Enemy(Enemy.bee));
                }
                if(waveTimer3.everyMillis(900)) {
                    this.add(new Enemy(Enemy.snail));
                }
                if(waveTimer4.everyMillis(1000)) {
                    this.add(new Enemy(Enemy.boar));
                }

                if(waveTimer1.afterMillis(30000)){
                    ResetWaveTimer();
                    waveState = 6;

                    for (int i = 0; i < 10; i++)
                    {
                        this.add(new Enemy(Enemy.bee));
                    }
                    for (int i = 0; i < 20; i++)
                    {
                        this.add(new Enemy(Enemy.flower));
                    }
                }
                break;
            case 6:
                if(waveTimer1.everyMillis(1000)) {
                    this.add(new Enemy(Enemy.slime));
                }
                if(waveTimer2.everyMillis(400)) {
                    this.add(new Enemy(Enemy.bee));
                }
                if(waveTimer3.everyMillis(600)) {
                    this.add(new Enemy(Enemy.boar));
                }
                if(waveTimer4.everyMillis(3000)) {
                    this.add(new Enemy(Enemy.flower));
                }

                if(waveTimer1.afterMillis(30000)){
                    ResetWaveTimer();
                    waveState = 7;

                    for (int i = 0; i < 20; i++)
                    {
                        this.add(new Enemy(Enemy.bee));
                    }
                    for (int i = 0; i < 2; i++)
                    {
                        this.add(new Enemy(Enemy.bat));
                    }
                }
                break;
            case 7:
                if(waveTimer1.everyMillis(1000)) {
                    this.add(new Enemy(Enemy.slime));
                }
                if(waveTimer2.everyMillis(400)) {
                    this.add(new Enemy(Enemy.bee));
                }
                if(waveTimer3.everyMillis(700)) {
                    this.add(new Enemy(Enemy.boar));
                }
                if(waveTimer4.everyMillis(800)) {
                    this.add(new Enemy(Enemy.flower));
                }

                if(waveTimer1.afterMillis(30000)){
                    ResetWaveTimer();
                    waveState = 8;

                    for (int i = 0; i < 20; i++)
                    {
                        this.add(new Enemy(Enemy.boar));
                    }
                    for (int i = 0; i < 5; i++)
                    {
                        this.add(new Enemy(Enemy.flower));
                    }
                }
                break;
            case 8:
                if(waveTimer1.everyMillis(800)) {
                    this.add(new Enemy(Enemy.redSnail));
                }
                if(waveTimer2.everyMillis(1000)) {
                    this.add(new Enemy(Enemy.redSlime));
                }
                if(waveTimer3.everyMillis(5000)) {
                    this.add(new Enemy(Enemy.bat));
                }

                if(waveTimer1.afterMillis(30000)){
                    ResetWaveTimer();
                    waveState = 9;

                    for (int i = 0; i < 20; i++)
                    {
                        this.add(new Enemy(Enemy.redSnail));
                    }
                    for (int i = 0; i < 20; i++)
                    {
                        this.add(new Enemy(Enemy.redSlime));
                    }
                }
                break;
            case 9:
                if(waveTimer1.everyMillis(600)) {
                    this.add(new Enemy(Enemy.redSnail));
                }
                if(waveTimer2.everyMillis(800)) {
                    this.add(new Enemy(Enemy.redSlime));
                }
                if(waveTimer3.everyMillis(2000)) {
                    this.add(new Enemy(Enemy.redBee));
                }
                if(waveTimer4.everyMillis(5000)) {
                    this.add(new Enemy(Enemy.bat));
                }

                if(waveTimer1.afterMillis(30000)){
                    ResetWaveTimer();
                    waveState = 10;

                    for (int i = 0; i < 20; i++)
                    {
                        this.add(new Enemy(Enemy.redSnail));
                    }
                    for (int i = 0; i < 20; i++)
                    {
                        this.add(new Enemy(Enemy.redSlime));
                    }
                    for (int i = 0; i < 10; i++)
                    {
                        this.add(new Enemy(Enemy.redBee));
                    }
                    for (int i = 0; i < 5; i++)
                    {
                        this.add(new Enemy(Enemy.bat));
                    }
                }
                break;
            case 10:
                if(waveTimer1.everyMillis(400)) {
                    this.add(new Enemy(Enemy.redSnail));
                }
                if(waveTimer2.everyMillis(600)) {
                    this.add(new Enemy(Enemy.redSlime));
                }
                if(waveTimer3.everyMillis(1500)) {
                    this.add(new Enemy(Enemy.redBee));
                }
                if(waveTimer4.everyMillis(3000)) {
                    this.add(new Enemy(Enemy.blackBoar));
                }
                if(waveTimer5.everyMillis(5000)) {
                    this.add(new Enemy(Enemy.bat));
                }

                if(waveTimer1.afterMillis(30000)){
                    ResetWaveTimer();
                    waveState = 11;

                    for (int i = 0; i < 20; i++)
                    {
                        this.add(new Enemy(Enemy.redSnail));
                    }
                    for (int i = 0; i < 20; i++)
                    {
                        this.add(new Enemy(Enemy.redSlime));
                    }
                    for (int i = 0; i < 10; i++)
                    {
                        this.add(new Enemy(Enemy.redBee));
                    }
                    for (int i = 0; i < 10; i++)
                    {
                        this.add(new Enemy(Enemy.blackBoar));
                    }
                }
                break;
            case 11:
                if(waveTimer1.everyMillis(500)) {
                    this.add(new Enemy(Enemy.redSnail));
                }
                if(waveTimer2.everyMillis(600)) {
                    this.add(new Enemy(Enemy.redSlime));
                }
                if(waveTimer3.everyMillis(1000)) {
                    this.add(new Enemy(Enemy.redBee));
                }
                if(waveTimer4.everyMillis(2000)) {
                    this.add(new Enemy(Enemy.blackBoar));
                }
                if(waveTimer5.everyMillis(5000)) {
                    this.add(new Enemy(Enemy.bat));
                }

                if(waveTimer1.afterMillis(30000)){
                    ResetWaveTimer();
                    waveState = 12;

                    for (int i = 0; i < 20; i++)
                    {
                        this.add(new Enemy(Enemy.redSlime));
                    }
                    for (int i = 0; i < 20; i++)
                    {
                        this.add(new Enemy(Enemy.blackBoar));
                    }
                    for (int i = 0; i < 10; i++)
                    {
                        this.add(new Enemy(Enemy.poisonFlower));
                    }
                }
                break;
            case 12:
                if(waveTimer1.everyMillis(800)) {
                    this.add(new Enemy(Enemy.redSnail));
                }
                if(waveTimer2.everyMillis(800)) {
                    this.add(new Enemy(Enemy.redSlime));
                }
                if(waveTimer3.everyMillis(400)) {
                    this.add(new Enemy(Enemy.redBee));
                }
                if(waveTimer4.everyMillis(1000)) {
                    this.add(new Enemy(Enemy.blackBoar));
                }
                if(waveTimer5.everyMillis(5000)) {
                    this.add(new Enemy(Enemy.bat));
                }

                if(waveTimer1.afterMillis(30000)){
                    ResetWaveTimer();
                    waveState = 13;

                    for (int i = 0; i < 20; i++)
                    {
                        this.add(new Enemy(Enemy.bat));
                    }
                }
                break;
            case 13:
                if(waveTimer1.everyMillis(300)) {
                    this.add(new Enemy(Enemy.redBee));
                }
                if(waveTimer2.everyMillis(800)) {
                    this.add(new Enemy(Enemy.blackBoar));
                }
                if(waveTimer3.everyMillis(2000)) {
                    this.add(new Enemy(Enemy.bat));
                }

                if(waveTimer1.afterMillis(30000)){
                    ResetWaveTimer();
                    waveState = 14;

                    for (int i = 0; i < 30; i++)
                    {
                        this.add(new Enemy(Enemy.poisonFlower));
                    }
                }
                break;
            case 14:
                if(waveTimer1.everyMillis(600)) {
                    this.add(new Enemy(Enemy.bat));
                }

                if(waveTimer1.afterMillis(30000)){
                    ResetWaveTimer();
                    waveState = 14;

                    for (int i = 0; i < 30; i++)
                    {
                        this.add(new Enemy(Enemy.bat));
                    }
                    for (int i = 0; i < 30; i++)
                    {
                        this.add(new Enemy(Enemy.poisonFlower));
                    }
                }
                break;
            case 15:
                if(waveTimer1.everyMillis(700)) {
                    this.add(new Enemy(Enemy.bat));
                }
                if(waveTimer1.everyMillis(800)) {
                    this.add(new Enemy(Enemy.poisonFlower));
                }

                if(waveTimer1.afterMillis(30000)){
                    ResetWaveTimer();
                    waveState = 16;

                    for (int i = 0; i < 30; i++)
                    {
                        this.add(new Enemy(Enemy.undead));
                    }
                }
                break;
        }
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
            if(!a.state) continue;
            for (int j = i + 1; j < n; j++) {   // i+1부터 -> 쌍을 한 번만 처리
                Enemy b = enemies.get(j);
                if(!b.state) continue;
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

        double magnetRange = player.magnetRange + player.bonusMagnetRange;
        double magnetRangeSq = magnetRange * magnetRange;

        for (XPOrb orb : orbs) {
            // 거리 제곱으로 비교해 sqrt 비용 제거
            double dx = orb.getX() - player.getX();
            double dy = orb.getY() - player.getY();
            double distSq = dx * dx + dy * dy;

            if (distSq < magnetRangeSq) {
                player.exp += orb.expAmount * (1 + player.bonusExp);
                System.out.println(player.level + ", " + player.exp);
                remove(orb); // GameManager.Instance.remove(orb) 등 네 구현에 맞게
            }
        }
    }

    public static void AddCostumes(Sprite spr, String path, String name, int range){
        for (int i = 1; i <= range; i++) {
            spr.addCostume(name + i, path + name + i + ".png");
        }
    }


    public void OpenWeaponSelect(){
        ArrayList<Item> ItemSelectable = new ArrayList<>();
        ItemSelectable.addAll(WeaponItems);

        List<Item> three = new ArrayList<>(ItemSelectable);

        Collections.shuffle(three);
        List<Item> picked = three.subList(0, Math.min(3, three.size()));

        if(picked.size() == 3) {
            this.add(new ItemSelectButton(0, 0, picked.get(0)));
            this.add(new ItemSelectButton(-250, 0, picked.get(1)));
            this.add(new ItemSelectButton(250, 0, picked.get(2)));
        }
        else if(picked.size() == 2) {
            this.add(new ItemSelectButton(-125, 0, picked.get(0)));
            this.add(new ItemSelectButton(125, 0, picked.get(1)));
        }
        else if(picked.size() == 1) {
            this.add(new ItemSelectButton(0, 0, picked.get(0)));
        }
    }


    public void OpenItemSelect(){
        ArrayList<Item> ItemSelectable = new ArrayList<>();
        ItemSelectable.addAll(WeaponItems);
        ItemSelectable.addAll(EffectItems);

        List<Item> three = new ArrayList<>(ItemSelectable);

        Collections.shuffle(three);
        List<Item> picked = three.subList(0, Math.min(3, three.size()));

        if(picked.size() == 3) {
            this.add(new ItemSelectButton(0, 0, picked.get(0)));
            this.add(new ItemSelectButton(-250, 0, picked.get(1)));
            this.add(new ItemSelectButton(250, 0, picked.get(2)));
        }
        else if(picked.size() == 2) {
            this.add(new ItemSelectButton(-125, 0, picked.get(0)));
            this.add(new ItemSelectButton(125, 0, picked.get(1)));
        }
        else if(picked.size() == 1) {
            this.add(new ItemSelectButton(0, 0, picked.get(0)));
        }
    }




    public void addItem(Item item) {
        ArrayList<Item> slot = WEAPON_SLOT;
        List<Item> selectable = WeaponItems;

        if (item instanceof EffectItem) {
            slot = SUPPORT_SLOT;
            selectable = EffectItems;
        }


        boolean check = true;

        // 이미 있는지 확인 → 있으면 레벨업
        for (Item data : slot) {
            if (data == item) {
                check = false;
                item.levelup();
                itemSlot.refreshUI();
            }
        }

        // 없다 → 새로 추가
        if (check && slot.size() < ITEMMAX) {
            slot.add(item);
            item.levelup();
            itemSlot.refreshUI();
        }

        if (slot.size() == ITEMMAX) {
            List<Item> remove = new ArrayList<>();

            for (Item i : selectable) {
                if (i.level <= 0 || i.level >= 5) {
                    remove.add(i);
                }
            }

            for (Item ri : remove){
                selectable.remove(ri);
            }

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


