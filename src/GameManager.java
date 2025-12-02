import org.openpatch.scratch.KeyCode;
import org.openpatch.scratch.RotationStyle;
import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.Stage;

public class GameManager extends Stage {

    public static GameManager Instance;
    int waveTimer = 0;
    boolean isFirstWave = true;
    double gameTimer = 480.0;
    static boolean isGamePaused = false;
    private int waveCounter = 0;
    private final int[] snails = {10, 10, 15, 15, 10, 10};
    private final int[] slimes = {0, 3, 0, 3, 5, 5};
    private final int[] boars = {0, 0, 0, 0, 0, 2};

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

        spawnWave();
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

        if (isFirstWave || waveTimer > 900) {
            isFirstWave = false;
            waveTimer = 0;
            spawnWave();
        }
    }


    private void spawnWave() {
        if (waveCounter >= snails.length) {
            waveCounter = 0;
        }

        for (int i = 0; i < snails[waveCounter]; i++) {
            this.add(new Enemy(Enemy.snail));
        }
        for (int i = 0; i < slimes[waveCounter]; i++) {
            this.add(new Enemy(Enemy.slime));
        }
        for (int i = 0; i < boars[waveCounter]; i++) {
            this.add(new Enemy(Enemy.boar));
        }

        waveCounter++;
    }

    public static void AddCostumes(Sprite spr, String path, String name, int range){
        for (int i = 1; i <= range; i++) {
            spr.addCostume(name + i, path + name + i + ".png");
        }
    }
}



