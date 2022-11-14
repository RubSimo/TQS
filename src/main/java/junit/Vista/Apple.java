package junit.Vista;
import java.awt.*;
import java.util.Random;

public class Apple {
  public int x_Coord;
  public int y_Coord;
  public int width;
  public int height;
  public boolean drawnApple;

  public int getCoord_X(){return x_Coord;}
  public int getCoord_Y(){return y_Coord;}

  public Apple(){
    Random random = new Random(System.currentTimeMillis());
    double random_X = Math.floor(50 * random.nextDouble());
    double random_Y = Math.floor(50 * random.nextDouble());
    this.x_Coord = (int) random_X;
    this.y_Coord =(int) random_Y;
    this.width = 10;
    this.height = 10;
    this.drawnApple = false;
  }


  public void draw(Graphics apple) {
    int x_pos;
    int y_pos;
    x_pos = x_Coord * width;
    y_pos = y_Coord * height;
    apple.setColor(Color.RED);
    apple.fillRect(x_pos, y_pos, width, height);
    drawnApple = true;
  }


}
