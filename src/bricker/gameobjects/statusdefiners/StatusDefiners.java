package bricker.gameobjects.statusdefiners;

import bricker.gameobjects.Paddle;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public abstract class StatusDefiners extends GameObject {

    private static final float SPEED = 100;
    private static final Vector2 DIMENSION = new Vector2(30, 15);;
    private GameObjectCollection gameObjectCollection;
    private Vector2 windowDimensions;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public StatusDefiners(Vector2 topLeftCorner,
                          Renderable renderable,
                          Vector2 center,
                          float speed, GameObjectCollection gameObjectCollection,
                          Vector2 windowDimensions) {
        super(topLeftCorner, DIMENSION, renderable);
        this.gameObjectCollection = gameObjectCollection;
        this.windowDimensions = windowDimensions;
        this.setCenter(center);
        this.setVelocity(Vector2.DOWN.mult(speed));
    }

    public StatusDefiners(Vector2 topLeftCorner,
                          Renderable renderable,
                          Vector2 center, GameObjectCollection gameObjectCollection,
                          Vector2 windowDimensions) {
        super(topLeftCorner, DIMENSION, renderable);
        this.gameObjectCollection = gameObjectCollection;
        this.windowDimensions = windowDimensions;
        this.setCenter(center);
        this.setVelocity(Vector2.DOWN.mult(SPEED));
    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other instanceof Paddle;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionBehavior(other);
        gameObjectCollection.removeGameObject(this);
    }

    protected abstract void collisionBehavior(GameObject other);

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.getDimensions().y() > windowDimensions.y())
            gameObjectCollection.removeGameObject(this);
    }
}
