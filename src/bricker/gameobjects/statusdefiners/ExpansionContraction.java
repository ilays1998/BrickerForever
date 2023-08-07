package bricker.gameobjects.statusdefiners;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class ExpansionContraction extends StatusDefiners{
    private final float changeSize;

    /**
     * Construct a new GameObject instance.
     *  @param topLeftCorner        Position of the object, in window coordinates (pixels).
     *                             Note that (0,0) is the top-left corner of the window.
     * @param renderable           The renderable representing the object. Can be null, in which case
     * @param center
     * @param speed
     * @param gameObjectCollection
     * @param windowDimensions
     * @param changeSize
     */
    public ExpansionContraction(Vector2 topLeftCorner,
                                Renderable renderable,
                                Vector2 center,
                                float speed,
                                GameObjectCollection gameObjectCollection,
                                Vector2 windowDimensions,
                                float changeSize) {
        super(topLeftCorner, renderable,
                center, speed, gameObjectCollection, windowDimensions);
        this.changeSize = changeSize;
    }

    @Override
    protected void collisionBehavior(GameObject other) {
        other.setDimensions(new Vector2(
                other.getDimensions().x() * changeSize,
                other.getDimensions().y()));
    }
}
