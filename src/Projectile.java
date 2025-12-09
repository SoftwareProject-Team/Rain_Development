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
    public double deathTime;

    public double speed = 50;

    public List<Enemy> alreadyHit = new ArrayList<>();
}

