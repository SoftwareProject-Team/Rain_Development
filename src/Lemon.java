public class Lemon extends EffectItem {
    public static final double[] attackSpeed = new double[] { 0.1, 0.2, 0.3, 0.4, 0.5 };

    public Lemon(String name, String iconPath) {
        super(name, iconPath);
    }

    @Override
    void levelup() {
        super.levelup();
        Player.Instance.bonusAttackSpeed = attackSpeed[level-1];
    }
}
