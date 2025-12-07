import org.openpatch.scratch.Sprite;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class ItemSelectButton extends Sprite {
    private String itemName;
    private String itemImgPath;
    private String descImgPath;
    private String type;
    private static final Map<String, String> TEXTURE_CACHE = new HashMap<>();
    // 아이템 이미지 설정
    private static final Map<String, Object> ITEM_CONFIG = new LinkedHashMap<>();
    // 설명 이미지 설정
    private static final Map<String, Object> DESC_CONFIG = new LinkedHashMap<>();
    static {
        // 아이템 이미지 값 설정
        ITEM_CONFIG.put("width", 80);
        ITEM_CONFIG.put("height", 80);
        ITEM_CONFIG.put("y", 30);
        ITEM_CONFIG.put("arc", 20);
        ITEM_CONFIG.put("borderColor", Color.BLACK);

        // 설명 이미지 값 설정
        DESC_CONFIG.put("width", 140);
        DESC_CONFIG.put("height", 100);
        DESC_CONFIG.put("y", 160);
        DESC_CONFIG.put("arc", 20);
        DESC_CONFIG.put("borderColor", Color.BLACK);
    }
    public ItemSelectButton(int x, int y, String type, String name, String itemImgPath, String descImgPath) {
        GameManager.Instance.GamePause();
        this.itemName = name;
        this.itemImgPath = itemImgPath;
        this.descImgPath = descImgPath;
        this.type = type;

        // 위치 설정
        this.setX(x);
        this.setY(y);

        // 코스튬 추가a
        try {
            String finalPath;

            if (TEXTURE_CACHE.containsKey(name)) {
                finalPath = TEXTURE_CACHE.get(name);
            }
            else {
                BufferedImage buttonSkin = drawButtonSkin(name, itemImgPath, descImgPath);
                File tempFile = File.createTempFile("temp_btn_" + name, ".png");
                ImageIO.write(buttonSkin, "png", tempFile);
                tempFile.deleteOnExit();
                finalPath = tempFile.getAbsolutePath();
                TEXTURE_CACHE.put(name, finalPath);
            }

            this.addCostume(name, finalPath);
            this.switchCostume(name);
        } catch (IOException e) {
            e.printStackTrace();
        }

        GameManager.Instance.registerButton(this);
    }


    // 버튼 그림
    private BufferedImage drawButtonSkin(String text, String path1, String path2) {
        int w = 200;
        int h = 300;
        //  아이템 이미지 설정
        int itemW = (int) ITEM_CONFIG.get("width");
        int itemH = (int) ITEM_CONFIG.get("height");
        int itemY = (int) ITEM_CONFIG.get("y");
        int itemArc = (int) ITEM_CONFIG.get("arc");
        Color itemBorderColor = (Color) ITEM_CONFIG.get("borderColor");
        int itemX = (w - itemW) / 2; // 중앙 정렬 계산

        // 설명 이미지 설정
        int descW = (int) DESC_CONFIG.get("width");
        int descH = (int) DESC_CONFIG.get("height");
        int descY = (int) DESC_CONFIG.get("y");
        int descArc = (int) DESC_CONFIG.get("arc");
        Color descBorderColor = (Color) DESC_CONFIG.get("borderColor");
        int descX = (w - descW) / 2; // 중앙 정렬 계산

        BufferedImage buff = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buff.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 배경
        g2.setColor(new Color(186, 184, 151, 255));
        g2.fillRoundRect(0, 0, w, h, 20, 20);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(0, 0, w, h, 20, 20);

        // 아이템 이미지 테두리
        g2.setColor(itemBorderColor);
        g2.drawRoundRect(itemX, itemY, itemW, itemH, itemArc, itemArc);

        // 설명 이미지 테두리
        g2.setColor(descBorderColor);
        g2.drawRoundRect(descX, descY, descW, descH, descArc, descArc);

        try {
            BufferedImage img1 = ImageIO.read(new File(path1));
            g2.drawImage(img1, itemX, itemY, itemW, itemH, null);
        } catch (Exception e) { g2.drawString("Img1 Error", itemX, itemY + 20); }

        // 텍스트
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Malgun Gothic", Font.BOLD, 18));
        FontMetrics fm = g2.getFontMetrics();
        int textX = (w - fm.stringWidth(text)) / 2;

        // 텍스트 위치도 아이템과 설명 사이 적절한 곳으로 조정하고 싶다면 변수화 가능
        g2.drawString(text, textX, 140);

        try {
            BufferedImage img2 = ImageIO.read(new File(path2));
            g2.drawImage(img2, descX, descY, descW, descH, null);
        } catch (Exception e) { g2.drawString("Img2 Error", descX, descY + 20); }

        g2.dispose();
        return buff;
    }

    @Override
    public void whenClicked() {
        GameManager.Instance.GamePlay();
        GameManager.Instance.addItem(GameManager.WEAPON_SLOT,type,itemImgPath);
        GameManager.Instance.hideOtherButtons();
    }
}