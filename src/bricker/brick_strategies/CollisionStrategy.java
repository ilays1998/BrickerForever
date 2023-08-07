package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

public interface CollisionStrategy {
    public void onCollision(GameObject thisObj, GameObject otherObj,
                            GameObjectCollection gameObjects, Counter numOfBricks);
}
