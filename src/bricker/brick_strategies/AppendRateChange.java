package bricker.brick_strategies;

import bricker.gameobjects.statusdefiners.RateChange;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

public class AppendRateChange extends RemoveBrickStrategy{
    private static final float FAST = 2f;
    private static final float SLOW = 0.5f;
    private final Random random;
    private Renderable slowImage;
    private Renderable fastImage;
    private Vector2 windowDimension;
    private WindowController windowController;

    public AppendRateChange(Renderable slowImage,
                            Renderable fastImage,
                            Vector2 windowDimension,
                            WindowController windowController) {
        this.slowImage = slowImage;
        this.fastImage = fastImage;
        this.windowDimension = windowDimension;
        this.random = new Random();
        this.windowController = windowController;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, GameObjectCollection gameObjects, Counter numOfBricks) {
        super.onCollision(thisObj, otherObj, gameObjects, numOfBricks);
        if (windowController.getTimeScale() < 1)
        {
            gameObjects.addGameObject(
                    new RateChange(thisObj.getTopLeftCorner(),
                            this.fastImage, thisObj.getCenter(),
                            gameObjects, windowDimension,
                            windowController, FAST)
            );
        }
        else if (windowController.getTimeScale() > 1)
        {
            gameObjects.addGameObject(
                    new RateChange(thisObj.getTopLeftCorner(),
                            this.slowImage, thisObj.getCenter(),
                            gameObjects, windowDimension,
                            windowController, SLOW)
            );
        }
        else
        {
            if (random.nextBoolean())
            {
                gameObjects.addGameObject(
                        new RateChange(thisObj.getTopLeftCorner(),
                                this.slowImage, thisObj.getCenter(),
                                gameObjects, windowDimension,
                                windowController, SLOW)
                );
            }
            else
            {
                gameObjects.addGameObject(
                        new RateChange(thisObj.getTopLeftCorner(),
                                this.fastImage, thisObj.getCenter(),
                                gameObjects, windowDimension,
                                windowController, FAST)
                );
            }
        }
    }
}
