package junit.Controlador;
import junit.Model.JSON;
import junit.Vista.Apple;
import junit.Vista.SnakePart;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;


public class Board extends JPanel implements Runnable, KeyListener {

  private static final long serialVersionUID = 1L;
  private Thread thread;
  private Random r = new Random();
  private SnakePart snakePiece;
  public Color color;

  public int WIDTH = 500, HEIGHT = 500;
  public int puntuation = 0;
  public int CoordSnake_X = 10, CoordSnake_Y = 10, SnakeSize = 5, ticks = 0, miliseconds = 800000;

  public String username = "";

  private ArrayList<SnakePart> snake;
  private ArrayList<Apple> apples;

  public boolean snakeUpdated, Lost_outLimit;
  public boolean boardDraw = false;
  public boolean running, updatingGame, firstSnake, createNewApple = false, isOut, right = true, left = false, up = false, down = false;
  public boolean snakeDrawn = false;

  public int getWidth(){return WIDTH;}
  public int getHeight(){return HEIGHT;}
  boolean applesInBoard = false;

  public void setColor(Color c){ color=c;}
  public ArrayList<Apple> getApples(){ return apples;}
  public ArrayList<SnakePart> getSnake(){ return snake;}
  public void setCoordSnake_X(int cord){
    CoordSnake_X =cord;}
  public void setCoordSnake_Y(int cord){
    CoordSnake_Y =cord;}



  @Override
  public void run() {
    while(running) {
      try {
        update();
      } catch (IOException e) {
        e.printStackTrace();
      }
      repaint();
    }

  }

  //Function which creates the board and the key listener to be able to insert keys.
  //It creates two arrays. One of them is the snake and the other the Apples.
  //Finally it starts the game.

  public Board() throws InterruptedException {

    setFocusable(true);
    setPreferredSize(new Dimension(WIDTH, HEIGHT));
    addKeyListener(this);
    snake = new ArrayList<SnakePart>();
    apples = new ArrayList<Apple>();
    start();
  }


  public void paint(Graphics g) {
    g.clearRect(0, 0, WIDTH, HEIGHT);

    g.setColor(Color.black);
    setColor(Color.BLACK);

    g.fillRect(0, 0, WIDTH, HEIGHT);


    for(int i = 0; i < snake.size() ; i++){
      snakeDrawn = false;
      if(!snakeDrawn)
        snake.get(i).draw(g);
        snakeDrawn = true;
    }


    for(int i = 0; i < apples.size(); i++) {
      apples.get(i).draw(g);
    }

    g.setColor(Color.ORANGE);
    g.drawString("PUNTUATION:  " + puntuation, 10,20);
    g.drawString("USER:  " + username, 10,40);
    boardDraw = true;

  }

  //Start function starts the game by creating a Thread and inicializing.
  //the variable running.

  public void start(){
    running = true;
    thread = new Thread(this);
    thread.start();
  }

  //Function where we stop the full game. Running is now false and
  //We create a JSON to save the user player and his/her puntuation.
  //It stops the Thread created in start function.

  public void stop() throws IOException {
    running=false;
    JSON json = new JSON();
    json.saveJSONFile(username,puntuation);
    try {
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  //Function which updates the full game. Every time the tick increase,
  //It comproves if the size snake is 0, if there is apples in the board, if we are inside the
  //board limits and if we have hit ourselves.

  public void update() throws IOException {

    if(snake.size() == 0){
      snakePiece = new SnakePart(CoordSnake_X, CoordSnake_Y, 10);
      snake.add(snakePiece);
      firstSnake = true;
      snakeDrawn = false;
    }
    ticks++;

    updateSnake(right,left,down,up);

    applesInBoard =comproveApplesInBoard();

    if(applesInBoard == true){
      eat();
      updatingGame = true;
    }

    if(comproveLimits() == true){
      stop();
    } else {
      updatingGame = true;
    }

    if(lose_hitHimself()==true){
      stop();
    }else{
      updatingGame = true;
    }

  }

  //Function where we update the snake direction / move according to the
  //values of the pressed key.

  public void updateSnake(boolean right, boolean left, boolean down, boolean up){

    snakeUpdated = false;
    if(ticks > miliseconds) {
      if (right)
        CoordSnake_X++;
      if (left)
        CoordSnake_X--;
      if (up)
        CoordSnake_Y--;
      if (down)
        CoordSnake_Y++;

      ticks = 0;

      snakePiece = new SnakePart(CoordSnake_X, CoordSnake_Y, 10);
      snake.add(snakePiece);

      moveSnake();

    }

  }

  //Function where we move the snake. We delete the first position (the tail).

  public void moveSnake(){
    if (snake.size() > SnakeSize) {
      snake.remove(0);
      snakeUpdated = true;
    }else{
      snakeUpdated = false;
    }
  }

  //Function where we comprove if we are going out the limits of the board (It has
  // to be greater or equal than 0 and lower than 50.

  public boolean comproveLimits() throws IOException {
    if((CoordSnake_X >= 0 && CoordSnake_X <= 49) && (CoordSnake_Y >= 0 && CoordSnake_Y <= 49)) {
      Lost_outLimit = false;
    }else{
      Lost_outLimit = true;
    }
    return Lost_outLimit;
  }

  //Function where we comprove if there is some aple in the board.
  //If it does not exist, it adds an Apple to the list.

  public boolean comproveApplesInBoard(){
    if(apples.size() == 0){
      Apple apple = new Apple();
      apples.add(apple);
      createNewApple = true;
    }

    return createNewApple;
  }

  //Function where we see if we are in the same position of the Apple. (First square)
  //If it is, it calls grow() function.

  public void eat(){
    for (int i=0; i< apples.size();i++){
      if(snake.get(snake.size() - 1).getCoord_X() == apples.get(i).getCoord_X() && snake.get(snake.size()-1).getCoord_Y() == apples.get(i).getCoord_Y())
        grow();
    }
  }

  //Function which make the snake grows if we eat a Apple.It
  //updates the SnakeSize, the puntuation and decrease the velocity.

  public void grow(){
    SnakeSize++;
    puntuation++;
    miliseconds = miliseconds + 6500;
    if(apples.size() != 0){
      apples.remove(0);
    }
    Apple apple = new Apple();
    apples.add(apple);
  }

  //Function where we see if we have hit ourselves. If it is
  //true we die. And the game is stopped.

  public boolean lose_hitHimself(){
    for (int i = 0; i < snake.size(); i++){
      if(CoordSnake_X == snake.get(i).getCoord_X() && CoordSnake_Y == snake.get(i).getCoord_Y()){
        if(i != snake.size() -1 ){
          return true;
        }
      }
    }
    return false;
  }

  //Function where we update the boolean of the pressed key to true.All the other are set False.

  @Override
  public void keyPressed(KeyEvent e) {
    int pressed_Key = e.getKeyCode();

    if(pressed_Key == KeyEvent.VK_UP && !down){
      left = false;
      up = true;
      right = false;
    }

    if(pressed_Key == KeyEvent.VK_LEFT && !right){
      left = true;
      up = false;
      down = false;
    }

    if(pressed_Key == KeyEvent.VK_DOWN && !up){
      down = true;
      left = false;
      right = false;
    }

    if(pressed_Key == KeyEvent.VK_RIGHT && !left){
      right = true;
      up = false;
      down = false;
    }
  }



  //Needed functions to use KeyListener Library.

  @Override
  public void keyTyped(KeyEvent e) {//Do nothing
  }

  @Override
  public void keyReleased(KeyEvent e) {//Do nothing
  }

}

