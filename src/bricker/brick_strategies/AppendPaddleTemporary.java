package bricker.brick_strategies;

import bricker.gameobjects.Paddle;
import bricker.gameobjects.PaddleTemporary;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

public class AppendPaddleTemporary extends RemoveBrickStrategy{
    private final float yLastRowBrickCoordinate;
    private final float yPaddleCoordinate;
    private Vector2 paddleDimensions;
    private Renderable paddleImage;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;
    private GameObject paddle;

    public AppendPaddleTemporary(float yLastRowBrickCoordinate, float yPaddleCoordinate,
                                 Vector2 paddleDimensions, Renderable paddleImage,
                                 UserInputListener inputListener,
                                 Vector2 windowDimensions, GameObject paddle) {
        this.yLastRowBrickCoordinate = yLastRowBrickCoordinate;
        this.yPaddleCoordinate = yPaddleCoordinate;
        this.paddleDimensions = paddleDimensions;
        this.paddleImage = paddleImage;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.paddle = paddle;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj,
                            GameObjectCollection gameObjects, Counter numOfBricks) {
        super.onCollision(thisObj, otherObj, gameObjects, numOfBricks);
        Random random = new Random();
        PaddleTemporary paddleTemporary =
                new PaddleTemporary(
                        new Vector2(paddle.getTopLeftCorner().x(),
                                yLastRowBrickCoordinate + random.nextFloat() *
                                        (yPaddleCoordinate - yLastRowBrickCoordinate)),
                        paddleDimensions, paddleImage, inputListener, windowDimensions,
                        gameObjects);
        gameObjects.addGameObject(paddleTemporary);

    }
}
