package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public class RemoveBrickStrategy implements CollisionStrategy{
    public void onCollision(GameObject thisObj, GameObject otherObj,
                            GameObjectCollection gameObjects, Counter numOfBricks) {
        gameObjects.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
        numOfBricks.decrement();
    }
}
