package si.afridau.minesweeper.logic;

public interface IGraphicTile<T> {
    T getTile();
    void displayOpenGraphic();
    void displayFlagGraphic();
    void removeGraphic();
    void displayHoverOpened();
    void displayHoverClosed();
    void displayNormalClosed();
    void displayNormalOpened();
}
