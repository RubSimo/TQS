package junit;

import junit.Controlador.Board;
import org.json.JSONArray;
import org.json.JSONObject;
import junit.Model.JSON;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
  public static int tries = 0;
  public static int tries_to_use = 0;
  public static int maximum_tries = 5;
  public static int choiceentry= -1;

  public Main(String name) throws InterruptedException {

    JFrame window = new JFrame();
    Board board = new Board();
    board.username=name;
    window.add(board);
    window.setTitle("Snake");
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setLocationRelativeTo(null);
    window.pack();
    window.setVisible(true);
  }

  public static void Play() throws InterruptedException {
    System.out.println("Username");
    Scanner s = new Scanner(System.in);
    String name = s.nextLine();
    new Main(name);
  }

  public static void showRanking(JSONObject jsonRanking) throws InterruptedException{
    System.out.println(jsonRanking);
  }

  public static void stopGameMenu() throws InterruptedException, IOException {
    Board board = new Board();
    board.stop();

  }

  public static void showMenu() throws InterruptedException {

    System.out.println("--------MENU-------");
    System.out.println(" ");
    System.out.println("Select one option: ");
    System.out.println("1. Play");
    System.out.println("2. Show Ranking");


    for (tries = tries_to_use; tries < maximum_tries; tries++) {
      System.out.println("Enter '1' or '2'");
      System.out.println("You have " + (maximum_tries - tries) + " tries to insert an option. ");
      Scanner s2 = new Scanner(System.in);
      choiceentry = Integer.parseInt(s2.nextLine());
      if (choiceentry == 1 || choiceentry == 2) {
        switch (choiceentry) {
          case 1:
            Play();
            break;
          case 2:
            JSON json = new JSON();
            JSONObject jsonRanking = new JSONObject();
            jsonRanking = json.loadJSONFile();
            showRanking(jsonRanking);
            break;
        }
      }
    }
  }

  public static void main(String[] args) throws IOException, InterruptedException {
      showMenu();
  }

}
