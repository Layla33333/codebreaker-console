package edu.cnm.deepdivecodebreaker;

import edu.cnm.deepdivecodebreaker.model.Game;
import edu.cnm.deepdivecodebreaker.model.Guess;
import edu.cnm.deepdivecodebreaker.service.GameRepository;
import edu.cnm.deepdivecodebreaker.service.GameRepository.BadGameException;
import edu.cnm.deepdivecodebreaker.service.GameRepository.BadGuessException;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Scanner;


public class Application {

  private static final String DEFAULT_POOL = "ABCDEF";
  private static final int DEFAULT_LENGTH = 3;

  // static non-final

  private final GameRepository repository;
  private final String pool;
  private final int length;
  private final Scanner scanner;
  private final ResourceBundle bundle;


  private Game game;

  // This is a constructor
  private Application(String[] args) {
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
    this.pool = pool;
    this.length = length;
    scanner = new Scanner(System.in);
    bundle = ResourceBundle.getBundle("strings");


  }


  public static void main(String[] args) {

    Application application = new Application(args);
    try {
      application.startGame();
      boolean solved = false;
      do {
        try {
          String text = application.getGuess();
          Guess guess = application.submitGuess(text);
          application.printGuessResults(guess);
          solved = guess.isSolution();

        } catch (BadGuessException e) {
          System.out.println(application.bundle.getString("invalid_guess"));
        }
      } while (!solved);
    } catch (IOException e) {
      System.out.println(application.bundle.getString("io_exception"));
    } catch (BadGameException e) {
      System.out.println(application.bundle.getString("invalid_game"));
    }
  }

  private void startGame() throws IOException, BadGameException {
    game = repository.startGame(pool, length);
  }

  private String getGuess() {
    System.out.printf(bundle.getString("guess_prompt"),
        game.getLength(), game.getPool());
    return scanner.next().trim();
  }

  private Guess submitGuess(String text) throws IOException, BadGuessException {
    return repository.submitGuess(game, text);
  }

  private void printGuessResults(Guess guess) {
    System.out.printf(bundle.getString("guess_results"),
        guess.getText(), guess.getExactMatches(), guess.getNearMatches());
    if (guess.isSolution()) {
      System.out.println(bundle.getString("solution"));
    }
  }
}