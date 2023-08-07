package bricker.brick_strategies;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

public class BrickStrategyFactory {
    private final int numOfStrategy;
    private float yPaddleCoordinate;
    private float yLastRowBrickCoordinate;
    private Vector2 paddleDimensions;
    private Renderable paddleImage;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;
    private GameObject paddle;
    private Renderable contractionImage;
    private Renderable expansionImage;
    private Renderable slowImage;
    private Renderable fastImage;
    private WindowController windowController;

    public BrickStrategyFactory(float yPaddleCoordinate,
                                float yLastRowBrickCoordinate,
                                Vector2 paddleDimensions,
                                Renderable paddleImage,
                                UserInputListener inputListener,
                                Vector2 windowDimensions, GameObject paddle,
                                Renderable contractionImage, Renderable expansionImage,
                                Renderable slowImage, Renderable fastImage,
                                WindowController windowController) {
        this.yPaddleCoordinate = yPaddleCoordinate;
        this.yLastRowBrickCoordinate = yLastRowBrickCoordinate;
        this.paddleDimensions = paddleDimensions;
        this.paddleImage = paddleImage;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.paddle = paddle;
        this.contractionImage = contractionImage;
        this.expansionImage = expansionImage;
        this.slowImage = slowImage;
        this.fastImage = fastImage;
        this.windowController = windowController;
        this.numOfStrategy = BrickStrategy.values().length;
    }

    public CollisionStrategy getStrategy() {
        //choose randomly between the possible brick strategies
        Random random = new Random();

        BrickStrategy choose = BrickStrategy.values()[random.nextInt(numOfStrategy)];
        switch (choose)
        {
            case REMOVE_BRICK_STRATEGY:
                return new RemoveBrickStrategy();
            case APPEND_PADDLE_TEMPORARY:
                return new AppendPaddleTemporary(
                        yPaddleCoordinate, yLastRowBrickCoordinate, paddleDimensions,
                        paddleImage, inputListener, windowDimensions, paddle);
            case EXPANSION_CONTRACTION:
                return new AppendExpansionContraction(contractionImage,
                        expansionImage, windowDimensions);
            case RATE_CHANGE:
                return new AppendRateChange(slowImage, fastImage, windowDimensions,
                        windowController);
            case DOUBLE_BEHAVIOR:
                return new DoubleBehavior(this);
            default:
                return null;
        }
    }
}
