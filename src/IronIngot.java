public class IronIngot extends EffectItem {
    public static final int[] magnet = new int[] { 10, 15, 20, 25, 30 };

    public IronIngot(String name, String iconPath) {
        super(name, iconPath);
    }

    @Override
    void levelup() {
        super.levelup();
        Player.Instance.bonusMagnetRange = magnet[level-1];
    }
}
