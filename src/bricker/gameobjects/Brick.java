package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import bricker.brick_strategies.RemoveBrickStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class Brick extends GameObject {

    private CollisionStrategy collisionStrategy;
    private GameObjectCollection gameObjects;
    private Counter numOfBricks;

    /**
     * Construct a new GameObject instance.
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param gameObjects
     * @param numOfBricks
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy,
                 GameObjectCollection gameObjects, Counter numOfBricks) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        this.gameObjects = gameObjects;
        this.numOfBricks = numOfBricks;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionStrategy.onCollision(this, other, this.gameObjects,
                this.numOfBricks);
    }
}
