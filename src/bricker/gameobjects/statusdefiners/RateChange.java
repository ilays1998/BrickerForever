package bricker.gameobjects.statusdefiners;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class RateChange extends StatusDefiners{
    private final WindowController windowController;
    private final float changeRate;

    public RateChange(Vector2 topLeftCorner, Renderable renderable,
                      Vector2 center, GameObjectCollection gameObjectCollection,
                      Vector2 windowDimensions,
                      WindowController windowController, float changeRate) {
        super(topLeftCorner, renderable, center, gameObjectCollection, windowDimensions);
        this.windowController = windowController;
        this.changeRate = changeRate;
    }

    @Override
    protected void collisionBehavior(GameObject other) {
        windowController.setTimeScale(changeRate);
    }
}
