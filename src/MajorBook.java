public class MajorBook extends EffectItem {
    public static final int[] projectile = new int[] { 1, 1, 2, 2, 3 };

    public MajorBook(String name, String iconPath) {
        super(name, iconPath);
    }

    @Override
    void levelup() {
        super.levelup();
        Player.Instance.bonusProjectile = projectile[level-1];
    }
}
