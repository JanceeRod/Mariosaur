package Objects;

import java.awt.*;

public abstract class BadGuy {

    public abstract Rectangle enemyHitbox();
    public abstract void draw(Graphics g);
    public abstract void update();
    public abstract boolean isOutOfScreen();
    public abstract boolean hoppedOverKalaban();
    public abstract boolean isScoreAdded();
    public abstract void scoreAddedYesOrNo(boolean YesOrNo);

}
