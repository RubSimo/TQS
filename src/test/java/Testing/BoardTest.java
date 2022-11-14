package Testing;
import junit.MockJSON;
import org.json.JSONObject;
import junit.Controlador.Board;
import junit.Main;
import junit.Model.JSON;
import junit.Vista.Apple;
import org.testng.annotations.Test;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testng.Assert.*;

public class BoardTest {

    public BoardTest() throws InterruptedException {
    }

    @Test
    public void testgetStartedGame() throws InterruptedException, IOException {
    Board board = new Board();
    assertEquals(board.running, true);
    board.stop();
    assertEquals(board.running, false);

    }

    @Test
    public void testUpdateFunction() throws InterruptedException, IOException {
        Board board = new Board();
        int ticks_before = board.ticks;
        board.update();
        assertNotEquals(ticks_before, board.ticks);
    }


    @Test
    public void testCreateApple() {
        Apple apple_test = new Apple();
        assertNotNull(apple_test);
    }

    @Test
    public void testCreateNewApple() throws InterruptedException {
        Board board = new Board();

        int size_beforeCreateApple = board.getApples().size();
        assertEquals(size_beforeCreateApple, 0);

        board.comproveApplesInBoard();

        int size_afterCreateApple = board.getApples().size();
        assertEquals(size_afterCreateApple, 1);
    }

    @Test //DecisionCoverage y PathCoverage.
    public void testcomproveLimits() throws InterruptedException, IOException
    {

        //True
        Board board_true = new Board();
        board_true.CoordSnake_X = 30;
        board_true.CoordSnake_Y = 30;
        boolean limits_test = board_true.comproveLimits();
        assertEquals(limits_test, false);

        Board board_false_limit = new Board();
        board_false_limit.CoordSnake_X = 49;
        board_false_limit.CoordSnake_Y = 49;
        limits_test = board_false_limit.comproveLimits();
        assertEquals(limits_test, false);


        //False
        Board board_false = new Board();
        board_false.CoordSnake_X = 60;
        board_false.CoordSnake_Y = 60;
        limits_test = board_false.comproveLimits();
        assertEquals(limits_test, true);


        //True limit
        Board board_true_limit = new Board();
        board_true_limit.CoordSnake_X = 50;
        board_true_limit.CoordSnake_Y = 50;
        limits_test = board_true_limit.comproveLimits();
        assertEquals(limits_test, true);

        //True Y
        Board board_Y = new Board();
        board_Y.CoordSnake_X = 25;
        board_Y.CoordSnake_Y = 50;
        limits_test = board_Y.comproveLimits();
        assertEquals(limits_test, true);


        //True X
        Board board_X= new Board();
        board_X.CoordSnake_X = 50;
        board_X.CoordSnake_Y = 25;
        limits_test = board_X.comproveLimits();
        assertEquals(limits_test, true);

    }

    @Test
    public void testComproveLimitsStartingGame() throws InterruptedException, IOException {

        Board board = new Board();
        board.CoordSnake_X = 50;
        board.CoordSnake_Y = 50;
        boolean limits_test = board.comproveLimits();
        assertEquals(limits_test, true);

        board.CoordSnake_X = 30;
        board.CoordSnake_Y = 50;
        limits_test = board.comproveLimits();
        assertEquals(limits_test, true);

        board.CoordSnake_X = 50;
        board.CoordSnake_Y = 30;
        limits_test = board.comproveLimits();
        assertEquals(limits_test, true);

        board.CoordSnake_X = -1;
        board.CoordSnake_Y = -100;
        limits_test = board.comproveLimits();
        assertEquals(limits_test, true);

        board.CoordSnake_X = 30;
        board.CoordSnake_Y = 30;
        limits_test = board.comproveLimits();
        assertEquals(limits_test, false);

    }

    @Test
    public void testComproveApplesInBoard() throws InterruptedException {
        Board board = new Board();
        boolean exists = board.comproveApplesInBoard();
        assertEquals(exists, true);
    }


    @Test
    public void testLose_hitHimself() throws InterruptedException {
        Board board = new Board();
        board.setCoordSnake_X(10);
        board.setCoordSnake_Y(10);
        boolean exists = board.lose_hitHimself();
        assertEquals(exists, false);

    }


    @Test //False case.
    public void testEat() throws InterruptedException
    {
        Board board = new Board();
        int size = board.SnakeSize;
        board.eat();
        assertEquals(size,board.SnakeSize);
    }


    @Test
    public void testGrowApples() throws InterruptedException
    {
        Board board = new Board();

        int numberApplesBeforeEat = board.getApples().size();
        board.grow();
        int numberApplesAfterEat = board.getApples().size();
        assertEquals(numberApplesBeforeEat, numberApplesAfterEat-1);
    }

    @Test
    public void testBoardInitialFeatures() throws InterruptedException {
        Board board = new Board();

        assertEquals(board.HEIGHT, 500);
        assertEquals(board.WIDTH, 500);
        assertEquals(board.getSnake().size(), 0);
        assertEquals(board.getApples().size(), 0);

    }
    @Test
    public void testInitialPuntuation() throws InterruptedException {
        Board board = new Board();
        assertEquals(board.puntuation, 0);

    }


    @Test
    public void testGrownPuntuation() throws InterruptedException {
        Board board = new Board();
        board.grow();
        assertEquals(board.puntuation, 1);
    }


    @Test
    public void testInitialSnake() throws InterruptedException {
        Board board = new Board();
        assertEquals(board.SnakeSize, 5);
    }


    @Test
    public void testGrowSnake() throws InterruptedException {
        Board board = new Board();
        board.grow();
        assertEquals(board.SnakeSize, 6);
    }

    @Test
    public void testStop() throws InterruptedException, IOException {
        Board board = new Board();
        board.stop();
        assertEquals(board.running, false);
    }

    @Test
    public void testInitialBoard() throws InterruptedException
    {
        Board board = new Board();
        assertNotNull(board);

    }
    @Test
    public void testSaveJSONFile() throws InterruptedException, IOException {

        JSON json = new JSON();
        json.saveJSONFile("User1",15);
        json.saveJSONFile("User2",2);
        json.saveJSONFile("User3",3);
        String json_information ="";
        BufferedReader file = new BufferedReader(new FileReader("database.json"));
        json_information = file.readLine();
        file.close();
        System.out.println(json_information);
    }

    @Test
    public void testLoadJSONFile() throws InterruptedException, IOException {

        JSON json = new JSON();
        JSONObject JSONLoaded = json.loadJSONFile();
        JSONObject jsonType = new JSONObject();
        System.out.println(JSONLoaded);
        assertEquals(JSONLoaded.getClass(), jsonType.getClass());
    }


    @Test
    public void testKeyPressedUp() throws InterruptedException {

        Board board = new Board();

        KeyEvent keyUp = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_UP,'Z');
        board.getKeyListeners()[0].keyPressed(keyUp);

        assertEquals(board.up,true);
        assertEquals(board.down,false);
        assertEquals(board.left,false);
        assertEquals(board.right,false);

    }


    @Test
    public void testKeyPressedDown() throws InterruptedException {

        Board board = new Board();
        KeyEvent keyDown = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_DOWN,'Z');
        board.getKeyListeners()[0].keyPressed(keyDown);

        assertEquals(board.up,false);
        assertEquals(board.down,true);
        assertEquals(board.left,false);
        assertEquals(board.right,false);



    }


    @Test
    public void testKeyPressedRight() throws InterruptedException {

        Board board = new Board();
        KeyEvent keyRight = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_RIGHT,'Z');
        board.getKeyListeners()[0].keyPressed(keyRight);

        assertEquals(board.up,false);
        assertEquals(board.down,false);
        assertEquals(board.left,false);
        assertEquals(board.right,true);
    }

    @Test
    public void testLoadJSONMOCK() throws InterruptedException, FileNotFoundException {
        JSONObject JsonToTest = new JSONObject();
        JsonToTest = MockJSON.addUserPuntuation();
        Main main = new Main("Testing Mock Object JSON - LOAD");
        main.showRanking(JsonToTest);
    }

    @Test
    public void testSaveJSONMOCK() throws InterruptedException, FileNotFoundException {
        JSONObject JsonToTest = new JSONObject();
        JsonToTest = MockJSON.getUsersPuntuations();
        String json_information = "{\"Test_Get\":10}";
        JSONObject Actual = new JSONObject(json_information);
        System.out.println(Actual);
        System.out.println(JsonToTest);
    }

    @Test
    public void testLoopingEvitar() throws InterruptedException, FileNotFoundException {
        //Evitar el loop
        Main main = new Main("Test looping");
        main.tries_to_use = 5;
        assertEquals((main.maximum_tries - main.tries_to_use), 0);
        main.showMenu();
    }

    @Test
    public void testLoopingEvitar_una_passada() throws InterruptedException, IOException {

        //Una passada pel loop
        Main main = new Main("Test looping");
        Board board = new Board();
        main.tries_to_use = 4;
        board.stop();
        assertEquals((main.maximum_tries - main.tries_to_use), 1);


    }

    @Test
    public void testLoopingEvitar_dues_passades() throws InterruptedException, IOException {

        //Dues passades pel loop
        Main main = new Main("Test looping");
        Board board = new Board();
        main.tries_to_use = 3;
        board.stop();
        assertEquals((main.maximum_tries - main.tries_to_use), 2);

    }

    @Test
    public void testLoopingEvitar_m_passades() throws InterruptedException, IOException {

        //m passades pel loop m<n
        Main main = new Main("Test looping");
        Board board = new Board();
        main.tries_to_use = 2;
        board.stop();
        assertEquals((main.maximum_tries - main.tries_to_use), 3);
    }

    @Test
    public void testLooping_n_passades() throws InterruptedException, IOException {
        //(n-1) passades pel loop
        Main main = new Main("Test looping");
        Board board = new Board();
        main.tries_to_use = 1;
        board.stop();
        assertEquals((main.maximum_tries - main.tries_to_use), 4);

    }
}