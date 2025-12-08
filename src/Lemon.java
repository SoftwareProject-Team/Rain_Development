public class Lemon extends EffectItem {
    public static final double[] attackSpeed = new double[] { 0.2, 0.4, 0.6, 0.8, 1 };

    public Lemon(String name, String iconPath) {
        super(name, iconPath);
    }

    @Override
    void levelup() {
        super.levelup();
        Player.Instance.bonusAttackSpeed = attackSpeed[level-1];
    }
}
