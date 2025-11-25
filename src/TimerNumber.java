import org.openpatch.scratch.Sprite;

public class TimerNumber extends Sprite {

    private int timerPos;

    public TimerNumber(int index) {
        this.timerPos = index;

        // 이미지 추가
        for (int i = 0; i <= 9; i++) {
            this.addCostume("number" + i, "sprites/Number/number" + i + ".png");
        }
        this.addCostume("colon", "sprites/Number/colon.png");

        // 초기 위치 설정
        initializePosition();
    }

    private void initializePosition() {
        int yPos = 250; // Y 위치는 모든 문자가 동일 (화면 상단)

        if (timerPos == 0) {
            this.setX(-40);
            this.setY(yPos);
        } else if (timerPos == 1) {
            this.setX(-20);
            this.setY(yPos);
        } else if (timerPos == 2) { // 10초 단위
            this.setX(20);
            this.setY(yPos);
        } else if (timerPos == 3) { // 1초 단위
            this.setX(40);
            this.setY(yPos);
        } else if (timerPos == 4) { // 콜론(:), X축 정중앙 위치
            this.setX(0);
            this.setY(yPos);
            this.switchCostume("colon");
        }
    }

    @Override
    public void run() {
        // 콜론은 계산이 필요 없음
        if (timerPos == 4) return;

        double time = GameManager.Instance.gameTimer;
        int digit = 0;

        if (timerPos == 0) {
            digit = (int) (time / 60 / 10); // 분의 십의 자리
        } else if (timerPos == 1) {
            digit = (int) (time / 60) % 10; // 분의 일의 자리
        } else if (timerPos == 2) {
            digit = (int) (time % 60) / 10; // 초의 십의 자리
        } else if (timerPos == 3) {
            digit = (int) (time % 60) % 10; // 초의 일의 자리
        }

        this.switchCostume("number" + digit);
    }

}
