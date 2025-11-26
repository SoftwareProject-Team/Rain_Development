import org.openpatch.scratch.Sprite;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class ItemSlot extends Sprite {

    private final int SLOT_SIZE = 80;
    private final int ICON_SIZE = 50;
    private final int GAP = 10;

    public ItemSlot() {
        this.setX(0);
        this.setY(0);
        refreshUI();
    }

    public void refreshUI() {
        BufferedImage buff = drawItemSlot();
        try {
            String uniqueName = "ui_" + System.nanoTime();
            File tempFile = File.createTempFile(uniqueName, ".png");
            ImageIO.write(buff, "png", tempFile);

            this.addCostume(uniqueName, tempFile.getAbsolutePath());
            this.switchCostume(uniqueName);

            tempFile.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage drawItemSlot() {
        int w = (SLOT_SIZE + GAP) * GameManager.ITEMMAX + 20;
        int h = (SLOT_SIZE * 2) + 40;

        BufferedImage buff = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buff.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.WHITE);
        g2.drawRoundRect(0, 0, w, h, 20, 20);

        int currentX = 10;

        for (int i = 0; i < GameManager.ITEMMAX; i++) {
            g2.setStroke(new BasicStroke(3));

            // -------------------------
            // 1. 무기 슬롯 (윗줄)
            // -------------------------
            g2.setColor(Color.BLACK);
            g2.drawRoundRect(currentX, 10, SLOT_SIZE, SLOT_SIZE, 20, 20);

            if (i < GameManager.WEAPON_SLOT.size()) {
                try {
                    ItemData item = GameManager.WEAPON_SLOT.get(i);
                    BufferedImage img = ImageIO.read(new File(item.imagePath));

                    int offset = (SLOT_SIZE - ICON_SIZE) / 2;
                    g2.drawImage(img, currentX + offset, 10 + offset, ICON_SIZE, ICON_SIZE, null);

                    // 레벨 표시
                    g2.drawString("Lv" + item.level, currentX + 52, 10 + 65);

                } catch (Exception e) {
                    g2.drawString("X", currentX + 30, 50);
                }
            }
            // 2. 서포트 슬롯 (아랫줄)

            int secondRowY = 10 + SLOT_SIZE + 10;
            g2.setColor(Color.BLACK);
            g2.drawRoundRect(currentX, secondRowY, SLOT_SIZE, SLOT_SIZE, 20, 20);

            if (i < GameManager.SUPPORT_SLOT.size()) {
                try {
                    ItemData support = GameManager.SUPPORT_SLOT.get(i);
                    BufferedImage img = ImageIO.read(new File(support.imagePath));

                    int offset = (SLOT_SIZE - ICON_SIZE) / 2;
                    g2.drawImage(img, currentX + offset, secondRowY + offset, ICON_SIZE, ICON_SIZE, null);

                    // 레벨 표시
                    g2.drawString("Lv" + support.level, currentX + 52, secondRowY + 65);

                } catch (Exception e) {
                    g2.drawString("X", currentX + 30, secondRowY + 40);
                }
            }

            currentX += (SLOT_SIZE + GAP);
        }

        g2.dispose();
        return buff;
    }
}
