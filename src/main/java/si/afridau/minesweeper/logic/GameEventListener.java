package si.afridau.minesweeper.logic;

//split this interface into 2 interfaces, one for mines (which will use gameOver method) and one for game win (which will use checkGameWin method)
public interface GameEventListener {
    void gameOver();
    void checkGameWin();
}
