package si.afridau.minesweeper.logic;

public interface ILogicTile extends ITileActionsHandler {
    void setGraphicTile(IGraphicTile<?> gTile);
    TileType getType();
    TileState getState();
    void setNeighbours(ILogicTile[] neighbours);
    void onTileOpenInternal();
    void displayHover();
    void displayNormal();
}
