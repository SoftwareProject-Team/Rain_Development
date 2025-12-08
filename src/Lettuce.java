public class Lettuce extends EffectItem {
    public static final int[] hp = new int[] { 20, 40, 60, 80, 100 };

    public Lettuce(String name, String iconPath) {
        super(name, iconPath);
    }

    @Override
    void levelup() {
        super.levelup();
        Player.Instance.bonusMaxHp = hp[level-1];
    }
}
