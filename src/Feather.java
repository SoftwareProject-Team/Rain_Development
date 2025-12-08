public class Feather extends EffectItem {
    public static final double[] speed = new double[] { 0.06, 0.12, 0.18, 0.24, 0.3 };

    public Feather(String name, String iconPath) {
        super(name, iconPath);
    }

    @Override
    void levelup() {
        super.levelup();
        Player.Instance.bonusSpeed = speed[level-1];
    }
}