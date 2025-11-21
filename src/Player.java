import org.openpatch.scratch.KeyCode;
import org.openpatch.scratch.RotationStyle;
import org.openpatch.scratch.Sprite;

class Player extends Sprite {

    public static String path = "sprites/Player/";

    Player() {
        GameManager.AddCostumes(this, path, "Idle", 2);
        GameManager.AddCostumes(this, path, "Run", 8);

        this.setOnEdgeBounce(true);
        this.setDirection(0);

        //Initialize
        Initialize();
    }

    private void Initialize(){
        this.setRotationStyle(RotationStyle.LEFT_RIGHT);
        this.setSize(150);
        //this.move(new Vector2(0, 0));

        frame = 1;
    }

    public void whenKeyPressed(int keyCode) {
        if (keyCode == KeyCode.VK_SPACE) {
            //예시 키 입력 스페이스바
        }
    }

    public int frame;

    //매 프레임마다 실행됨 (60프레임), 기존 코드 DeltaTime 사이클 대체
    public void run() {
        this.switchCostume("Idle" + (int)(frame / 32f));
    }
}