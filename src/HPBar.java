import org.openpatch.scratch.Sprite;

public class HPBar extends Sprite {

    public HPBar() {
        for (int i = 0; i <= 50; i++) {
            this.addCostume(String.valueOf(i), "sprites/HPBar/hpbar_" + i + ".png");
        }

        // 초기 코스튬 설정
        this.switchCostume("0");
    }

    @Override
    public void run() {

        Player player = GameManager.Instance.player;

        if (player != null) {
            this.setX(player.getX());
            this.setY(player.getY() + 19); // 플레이어 머리 위로 띄우기
        }

        double currentHP = player.hp;
        double maxHP = player.maxHp;

        if (currentHP < 0) {
            this.switchCostume("50");
        } else {
            if (maxHP == 0) maxHP = 1; // division-by-zero 방지

            int costumeIndex = 50 - (int) Math.round((currentHP / maxHP) * 50);

            // index-out-of-range 방지
            if (costumeIndex < 0) costumeIndex = 0;
            if (costumeIndex > 50) costumeIndex = 50;

            this.switchCostume(String.valueOf(costumeIndex));

        }

        // 딜레이 (컴퓨터 작동 중지 방지)
        try {
            Thread.sleep(16); // 약 60프레임 (1000ms / 60)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}