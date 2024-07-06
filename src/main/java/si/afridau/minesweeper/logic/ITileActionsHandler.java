package si.afridau.minesweeper.logic;

public interface ITileActionsHandler {
    void onTileOpen();
    void placeFlag();
    void hover();
    void hoverExit();
}
