package edu.cnm.deepdivecodebreaker;

import edu.cnm.deepdivecodebreaker.model.Game;
import edu.cnm.deepdivecodebreaker.service.WebServiceProxy;
import java.io.IOException;
import java.io.PrintStream;
import retrofit2.Call;
import retrofit2.Response;

public class Application {

  public static void main(String[] args) throws IOException {


    // TODO Read command-line arguments for pool and length.
  String pool = "ABCDEF";//FIXME Read from args
  int length = 3; // FIXME Read From args
  Game game = startGame(pool, length);
    System.out.printf("Game id = %s%n", game.getId());


    // TODO while code is not guessed:
    // 1.Read guess from user input.
    // 2.Submit guess to codebreaker service.
    // 3.Display guess results
  }

  private static Game startGame(String pool, int length) throws IOException {
    WebServiceProxy proxy = WebServiceProxy.getInstance();
    Game game = new Game();
    game.setPool(pool);
    game.setLength(length);
    Call<Game> call = proxy.startGame(game);
    Response<Game> response = call.execute();
    if (!response.isSuccessful()) {
      throw new RuntimeException(response.message());
    }
      return response.body();
  }
}