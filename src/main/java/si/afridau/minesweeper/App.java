package si.afridau.minesweeper;


import si.afridau.minesweeper.swingimpl.SwingGame;


public class App {
    public static void main( String[] args ) {
        SwingGame game = new SwingGame();
        game.startGame(10, 10, 10);
    }
}
