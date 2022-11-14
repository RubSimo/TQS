package junit.Vista;

import java.awt.*;

public class SnakePart{
  public int x_Coord;
  public int y_Coord;
  public int width;
  public int height;
  public boolean drawnSnake;

  public SnakePart(int x_Coord, int y_Coord, int Size){
    this.x_Coord = x_Coord;
    this.y_Coord = y_Coord;
    this.width = Size;
    this.height = Size;
  }

  public int getCoord_X(){
    return x_Coord;
  }
  public int getCoord_Y(){ return y_Coord; }


  public void draw(Graphics g) {

    g.setColor(Color.GREEN);
    int x_position;
    int y_position;
    x_position = x_Coord * width;
    y_position = y_Coord * height;
    g.fillRect(x_position, y_position, width, height);
    drawnSnake = true;
  }

}
