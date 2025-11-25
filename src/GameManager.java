import org.openpatch.scratch.KeyCode;
import org.openpatch.scratch.RotationStyle;
import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.Stage;
import org.openpatch.scratch.extensions.math.Vector2;

public class GameManager extends Stage {

    public static GameManager Instance;
    int waveTimer = 0;
    boolean isFirstWave = true;
    double gameTimer = 480.0;
    static boolean isGamePaused = false;

    public GameManager() {
        super(800, 600);

        Instance = this;

        Sprite player = new Player();
        this.add(player);

        // 타이머 숫자 5개 생성, 화면에 붙이기
        for (int i = 0; i < 5; i++) {
            TimerNumber t = new TimerNumber(i);
            this.add(t);
        }

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
    }
    public static void AddCostumes(Sprite spr, String path, String name, int range){
        for (int i = 1; i <= range; i++) {
            spr.addCostume(name + i, path + name + i + ".png");
        }
    }
}



