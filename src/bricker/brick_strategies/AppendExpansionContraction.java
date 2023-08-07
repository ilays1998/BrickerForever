package bricker.brick_strategies;

import bricker.gameobjects.statusdefiners.ExpansionContraction;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

public class AppendExpansionContraction extends RemoveBrickStrategy{
    private Renderable contractionImage;
    private Renderable expansionImage;
    private Random random;
    private static final float SPEED_DOWN = 100;
    private static final float EXPANSION = 2;
    private static final float CONTRACTION = 0.5f;
    private Vector2 windowDimensions;

    public AppendExpansionContraction(Renderable contractionImage,
                                      Renderable expansionImage,
                                      Vector2 windowDimensions) {
        this.windowDimensions = windowDimensions;
        random = new Random();
        this.contractionImage = contractionImage;
        this.expansionImage = expansionImage;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj,
                            GameObjectCollection gameObjects, Counter numOfBricks) {
        super.onCollision(thisObj, otherObj, gameObjects, numOfBricks);
        ExpansionContraction expansionContraction;
        if (random.nextBoolean())
        {
            expansionContraction =
                    new ExpansionContraction(
                            thisObj.getTopLeftCorner(),
                            contractionImage, thisObj.getCenter(),
                            SPEED_DOWN, gameObjects,
                            windowDimensions,CONTRACTION
                    );
        }
        else
        {
            expansionContraction =
                    new ExpansionContraction(
                            thisObj.getTopLeftCorner(),
                            expansionImage, thisObj.getCenter(),
                            SPEED_DOWN, gameObjects,
                            windowDimensions, EXPANSION
                    );
        }
        gameObjects.addGameObject(expansionContraction);
    }
}
