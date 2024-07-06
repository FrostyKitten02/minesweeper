package si.afridau.minesweeper.logic;

public interface ITileActionsHandler {
    void onTileOpen();
    void onTileRelease();
    void placeFlag();
    void hover();
    void hoverExit();
}
