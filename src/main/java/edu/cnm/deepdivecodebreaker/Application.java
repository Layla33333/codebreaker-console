package edu.cnm.deepdivecodebreaker;

import edu.cnm.deepdivecodebreaker.model.Game;
import edu.cnm.deepdivecodebreaker.service.GameRepository;
import edu.cnm.deepdivecodebreaker.service.WebServiceProxy;
import java.io.IOException;
import java.io.PrintStream;
import retrofit2.Call;
import retrofit2.Response;

public class Application {

  private static final String DEFAULT_POOL = "ABCDEF";
  private static final int DEFAULT_LENGTH = 3;

  // static non-final

  private final GameRepository repository;
  private Game game;

  // This is a constructor
  private Application(String[] args) throws IOException {
    String pool = DEFAULT_POOL;
    int length = DEFAULT_LENGTH;

    switch (args.length) {
      default:
        // Deliberate fall through!!
      case 2:
        length = Integer.parseInt(args[1]);
        // Deliberate fall through!
      case 1:
        pool = args[0];
      case 0:
        // Do Nothing!!!

    }

    repository = new GameRepository();
    startGame(pool, length);


  }

  private void startGame(String pool, int length) throws IOException {
    game = repository.startGame(pool, length);
  }


  public static void main(String[] args) throws IOException {

    Application application = new Application(args);


    // TODO while code is not guessed:
    // 1.Read guess from user input.
    // 2.Submit guess to codebreaker service.
    // 3.Display guess results
  }

}