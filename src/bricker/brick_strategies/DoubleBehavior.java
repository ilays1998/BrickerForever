package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

public class DoubleBehavior implements CollisionStrategy{

    private final BrickStrategyFactory brickStrategyFactory;

    public DoubleBehavior(BrickStrategyFactory brickStrategyFactory) {

        this.brickStrategyFactory = brickStrategyFactory;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj,
                            GameObjectCollection gameObjects, Counter numOfBricks) {
        brickStrategyFactory.getStrategy().onCollision(
                thisObj, otherObj, gameObjects, numOfBricks);
        brickStrategyFactory.getStrategy().onCollision(
                thisObj, otherObj, gameObjects, numOfBricks);
        numOfBricks.increment();
    }
}
