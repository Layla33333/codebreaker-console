package edu.cnm.deepdivecodebreaker.service;

import edu.cnm.deepdivecodebreaker.model.Game;
import edu.cnm.deepdivecodebreaker.model.Guess;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;

public class GameRepository {

  private final WebServiceProxy proxy;

  public GameRepository() {
    proxy = WebServiceProxy.getInstance();
  }

  public Game startGame(String pool, int length) throws IOException, BadGameException {
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

  public Guess submitGuess(Game game, String text) throws IOException, BadGuessException {
    Guess guess = new Guess();
    guess.setText(text);
    Response<Guess> response = proxy.submitGuess(guess, game.getId())
        .execute();
    if (!response.isSuccessful()) {
      throw new BadGameException(response.message());
    }
    return response.body();
  }

  public static class BadGuessException extends IllegalArgumentException {

    public BadGuessException(String message) {
      super(message);
    }
  }

 public static class BadGameException extends IllegalArgumentException {

    public BadGameException(String message) {
      super(message);
    }
  }

}



