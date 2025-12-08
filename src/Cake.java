public class Cake extends EffectItem {
    public static final double[] exp = new double[] { 0.25, 0.5, 0.75, 1, 1.5 };

    public Cake(String name, String iconPath) {
        super(name, iconPath);
    }

    @Override
    void levelup() {
        super.levelup();
        Player.Instance.bonusExp = exp[level-1];
    }
}
