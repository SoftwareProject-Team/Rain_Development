import org.openpatch.scratch.KeyCode;
import org.openpatch.scratch.RotationStyle;
import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.extensions.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Projectile extends Sprite {
    public double dir;
    public Vector2 startPos;
    public double size;

    public double hitSize;

    public double damage;
    public int deathFrame;

    public double speed = 50;

    public List<Enemy> alreadyHit = new ArrayList<>();
}

