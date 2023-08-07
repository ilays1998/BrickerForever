package bricker.main;

import bricker.brick_strategies.BrickStrategyFactory;
import bricker.gameobjects.Ball;
import bricker.gameobjects.Brick;
import bricker.gameobjects.Paddle;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.util.Counter;

import java.util.Random;

public class BrickerGameManager extends GameManager{

    public static final Vector2 PADDLE_PLACE = Vector2.ZERO;
    private static final float BORDER_WIDTH = 10;
    public static final Vector2 PADDLE_DIMENSIONS = new Vector2(100, 15);
    public static final Vector2 BALL_PLACE = Vector2.ZERO;
    public static final Vector2 BALL_DIMENSIONS = new Vector2(20, 20);
    public static final float BALL_SPEED = 170;
    public static final Vector2 WINDOW_DIMENSIONS = new Vector2(700, 500);
    public static final float BRICK_HEIGHT = 15;
    private static final float DISTANSE_BETWEEN_BRICK_TO_WALL = 5;
    private static final float DISTANSE_BETWEEN_BRICK_TO_BRICK = 1;
    private static final int NUM_OF_BRICK_IN_ROW = 8;
    private static final int NUM_OF_BRICK_IN_COL = 5;
    private Counter numOfBricks;
    private BrickStrategyFactory brickStrategyFactory;
    private Ball ball;
    private Vector2 windowDimensions;
    private WindowController windowController;
    private float yPaddleCoordinate;

    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    private void newBrick(Vector2 place, Vector2 size, Renderable brickImage,
                          GameObjectCollection gameObjects, Counter numOfBricks)
    {
        gameObjects().addGameObject(new Brick(place, size, brickImage,
                brickStrategyFactory.getStrategy(),
                gameObjects, numOfBricks), Layer.STATIC_OBJECTS);
    }

    private void newWall(Vector2 place, Vector2 size, Renderable wallImage)
    {
        gameObjects().addGameObject(new GameObject(
                //anchored at top-left corner of the screen
                place,
                //height of border is the height of the screen
                size,
                //this game object is invisible; it doesn’t have a Renderable
                wallImage));
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        this.windowController = windowController;
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        windowDimensions = windowController.getWindowDimensions();
        yPaddleCoordinate = windowDimensions.y() - 30;
        this.numOfBricks = new Counter(NUM_OF_BRICK_IN_COL
                * NUM_OF_BRICK_IN_ROW);
        float yLastRowBrickCoordinate =
                BORDER_WIDTH + DISTANSE_BETWEEN_BRICK_TO_WALL +
                        NUM_OF_BRICK_IN_COL * BRICK_HEIGHT +
                        NUM_OF_BRICK_IN_COL * DISTANSE_BETWEEN_BRICK_TO_BRICK;
//        windowController.setTargetFramerate(40);

        //creating ball
        Renderable ballImage =
                imageReader.readImage("assets/ball.png", true);

        Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        ball = new Ball(BALL_PLACE, BALL_DIMENSIONS, ballImage,
                collisionSound);
        ball.setVelocity(Vector2.DOWN.mult(BALL_SPEED));

        ball.setCenter(windowDimensions.mult(0.5f));

        this.gameObjects().addGameObject(ball);

        //create paddle
        Renderable paddleImage = imageReader.readImage(
                "assets/paddle.png", true);
        GameObject paddle = new Paddle(PADDLE_PLACE, PADDLE_DIMENSIONS, paddleImage,
                inputListener, windowDimensions);

        Renderable contractionImage =
                imageReader.readImage("assets/buffNarrow.png", false);
        Renderable expansionImage =
                imageReader.readImage("assets/buffWiden.png", false);
        Renderable slowImage =
                imageReader.readImage("assets/slow.png", false);
        Renderable fastImage =
                imageReader.readImage("assets/quicken.png", false);        ;

        brickStrategyFactory = new BrickStrategyFactory(
                yPaddleCoordinate, yLastRowBrickCoordinate, PADDLE_DIMENSIONS,
                paddleImage, inputListener, windowDimensions, paddle, contractionImage,
                expansionImage, slowImage, fastImage, windowController);

        paddle.setCenter(new Vector2(windowDimensions.x()/2, yPaddleCoordinate));

        gameObjects().addGameObject(paddle);

        //creating walls
        Renderable wallImage =
                imageReader.readImage("assets/red_color.png", false);

        newWall(Vector2.ZERO, new Vector2(BORDER_WIDTH, windowDimensions.y()), wallImage);
        newWall(Vector2.ZERO, new Vector2(windowDimensions.x(), BORDER_WIDTH), wallImage);
        newWall(new Vector2(windowDimensions.x() - BORDER_WIDTH, 0), new Vector2(BORDER_WIDTH, windowDimensions.y()),wallImage);

        //creating background
        GameObject background = new GameObject(
                Vector2.ZERO,
                windowController.getWindowDimensions(),
                imageReader.readImage("assets/DARK_BG2_small.jpeg", false));

        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        gameObjects().addGameObject(background, Layer.BACKGROUND);

        //creating random start of ball
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean())
            ballVelX *= -1;
        if (rand.nextBoolean())
            ballVelY *= -1;
        ball.setVelocity(new Vector2(ballVelX, ballVelY));

        //creating brick
        Renderable brickImage =
                imageReader.readImage("assets/brick.png", false);

        float brickWidth = (windowDimensions.x() - (BORDER_WIDTH * 2) -
                (DISTANSE_BETWEEN_BRICK_TO_WALL * 2) - 
                (DISTANSE_BETWEEN_BRICK_TO_BRICK * (NUM_OF_BRICK_IN_ROW - 1)))
                / NUM_OF_BRICK_IN_ROW;

        float brickRow =  BORDER_WIDTH + DISTANSE_BETWEEN_BRICK_TO_WALL;
        float brickCol = BORDER_WIDTH + DISTANSE_BETWEEN_BRICK_TO_WALL;
        for (int row = 0; row < NUM_OF_BRICK_IN_ROW; row++)
        {
            for (int col = 0; col < NUM_OF_BRICK_IN_COL; col++)
            {
                newBrick(new Vector2(brickRow, brickCol),
                        new Vector2(brickWidth, BRICK_HEIGHT),
                        brickImage, gameObjects(), numOfBricks);
                brickCol += DISTANSE_BETWEEN_BRICK_TO_BRICK + BRICK_HEIGHT;
            }
            brickCol = BORDER_WIDTH + DISTANSE_BETWEEN_BRICK_TO_WALL;
            brickRow += DISTANSE_BETWEEN_BRICK_TO_BRICK + brickWidth;
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
    }

    private void checkForGameEnd() {
        float ballHeight = ball.getCenter().y();
        String prompt = "";
        if (ballHeight > windowDimensions.y())
        {
            //we lost
            prompt = "You Lose!";
        }
        if (numOfBricks.value() == 0)
        {
            prompt = "You Win!";
        }
        if (!prompt.isEmpty())
        {
            prompt += " Play again?";
            if(windowController.openYesNoDialog(prompt))
                windowController.resetGame();
            else
                windowController.closeWindow();
        }
    }

    public static void main(String[] args) {
        new BrickerGameManager("Bricker", WINDOW_DIMENSIONS).run();
    }
}
