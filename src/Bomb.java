public class Bomb extends EffectItem {
    public static final double[] size = new double[] { 0.1, 0.2, 0.3, 0.4, 0.5 };

    public Bomb(String name, String iconPath) {
        super(name, iconPath);
    }


    @Override
    void levelup() {
        super.levelup();
        Player.Instance.bonusAttackSize = size[level-1];
    }
}