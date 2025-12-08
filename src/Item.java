import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.extensions.math.Vector2;
import java.util.List;

public class Item extends Sprite {
    public int level;
    public String name;

    public Item(String name) {
        this.name = name;
    }

    void levelup() {
        if(level < 5) level++;
    }
}