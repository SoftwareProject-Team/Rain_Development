public class Meat extends EffectItem {
    public static final double[] damage = new double[] { 0.1, 0.2, 0.3, 0.4, 0.5 };

    public Meat(String name, String iconPath) {
        super(name, iconPath);
    }

    @Override
    void levelup() {
        super.levelup();
        Player.Instance.bonusDamage = damage[level-1];
    }
}