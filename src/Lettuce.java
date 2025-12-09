public class Lettuce extends EffectItem {
    public static final double[] hp = new double[] { 30, 60, 90, 120, 150 };

    public Lettuce(String name, String iconPath) {
        super(name, iconPath);
    }

    @Override
    void levelup() {
        super.levelup();
        Player.Instance.bonusMaxHp = hp[level-1];
        Player.Instance.Heal(0.3);
    }
}
