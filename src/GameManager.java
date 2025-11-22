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
    boolean isGamePaused = false;

    public GameManager() {
        super(800, 600);

        Instance = this;

        Sprite player = new Player();
        this.add(player);

    }

    public static void main(String[] args) {
        new GameManager();
    }
    @Override
    public void run() {
        // Wait until G_Pause = False
        if (!isGamePaused) {
            waveTimer++;

            gameTimer -= (1.0 / 60.0);
            // 0초 이하로 내려가지 않게 고정
            if (gameTimer < 0) gameTimer = 0;
            System.out.println(gameTimer);

            if (waveTimer >= 900 || isFirstWave) {
                spawnWave();   // 몬스터 소환
                waveTimer = 0; // 0으로 초기화 (다시 15초 세기 시작)
                isFirstWave = false;
            }

        }
    }
    private void spawnWave() {
        for (int i = 0; i < 4; i++)
        {
            Snail s = new Snail();
            s.setPosition();
            this.add(s);
        }
        for (int i = 0; i < 3; i++)
        {
            Slime sl = new Slime();
            sl.setPosition();
            this.add(sl);
        }
        for (int i = 0; i < 2; i++) {
            Boar b = new Boar();
            b.setPosition();
            this.add(b);
        }
    }
    public static void AddCostumes(Sprite spr, String path, String name, int range){
        for (int i = 1; i <= range; i++) {
            spr.addCostume(name + i, path + name + i + ".png");
        }
    }
}



