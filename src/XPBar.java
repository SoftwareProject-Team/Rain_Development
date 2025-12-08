import org.openpatch.scratch.Sprite;

public class XPBar extends Sprite {

    private int[] expNeedList;

    // 이미지를 저장할 경로
    private static final String GEN_PATH = "sprites/generated_xp/";

    public XPBar() {
        initializeExpList();
        GameManager.AddCostumes(this, GEN_PATH, "xp_", 100);

        // 위치 설정 (화면 상단)
        this.setX(0);
        this.setY(292);
    }

    private void initializeExpList() {
        expNeedList = new int[201];
        for (int i = 1; i <= 200; i++) {
            expNeedList[i] = 10 + i * i;
        }
    }

//    // === 자바 AWT로 이미지를 만들어서 파일로 저장하고 불러오는 메소드 ===
//    private void generateAndLoadCostumes() {
//        try {
//            File dir = new File(GEN_PATH);
//            if (!dir.exists()) {
//                dir.mkdirs(); // 폴더가 없으면 생성
//            }
//
//            int width = 800;
//            int height = 15;
//
//            // 0% 부터 100% 까지 이미지 101장 생성
//            for (int i = 1; i <= 101; i++) {
//                // 1. 메모리에 도화지(Image) 생성
//                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//                Graphics2D g = image.createGraphics();
//
//                // 2. 그림 그리기 (java.awt)
//                // 배경 (반투명 검정)
//                g.setColor(new Color(0, 0, 0, 150));
//                g.fillRect(0, 0, width, height);
//
//                // 게이지 (초록색)
//                int fillWidth = (int) (width * ((i-1) / 100.0));
//                g.setColor(new Color(135, 238, 135, 136));
//                g.fillRect(0, 0, fillWidth, height);
//
//                // 테두리 (흰색)
//                g.setColor(new Color(255, 255, 255, 100));
//                g.drawRect(0, 0, width - 1, height - 1);
//
//                // (텍스트는 이미지가 바뀌어도 계속 변할 수 있으므로,
//                //  여기서는 바(Bar) 모양만 그립니다. 레벨 숫자는 별도 스프라이트 권장)
//
//                g.dispose();
//
//                // 3. 파일로 저장 ("sprites/generated_xp/xp_1.png" 등)
//                File file = new File(GEN_PATH + "xp_" + i + ".png");
//                ImageIO.write(image, "png", file);
//
//                // 4. 스프라이트 코스튬으로 추가 (경로 사용)
//                this.addCostume(String.valueOf(i), file.getPath());
//            }
//
//            // 초기 코스튬 설정
//            this.switchCostume("0");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("XPBar 이미지 생성 중 오류 발생");
//        }
//    }

    @Override
    public void run() {
        if (GameManager.isGamePaused) return;

        Player player = Player.Instance;
        if (player == null) return;

        // 레벨업 로직
        if (player.level < expNeedList.length && player.exp >= expNeedList[player.level]) {
            player.exp -= expNeedList[player.level];
            player.level++;
            GameManager.Instance.OpenItemSelect();
        }

        // 바 모양 업데이트
        // (현재 경험치 / 필요 경험치) 비율 계산
        int maxExp = expNeedList[player.level];
        if (maxExp == 0) maxExp = 1;

        double ratio = (double) player.exp / (double) maxExp;
        int percent = (int) (ratio * 100);

        // 범위 제한 (0~100)
        if (percent < 0) percent = 0;
        if (percent > 100) percent = 100;

        // 해당하는 %의 코스튬으로 교체
        this.switchCostume("xp_"+ (percent+1) );
    }
}