package si.afridau.minesweeper.logic;

public interface ILogicTile extends ITileActionsHandler {
    void setGraphicTile(IGraphicTile<?> gTile);
    TileType getType();
    void setNeighbours(ILogicTile[] neighbours);
    void onTileOpenInternal(boolean userInput);
}
