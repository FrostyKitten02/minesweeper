package si.afridau.minesweeper.logic;

public interface IGraphicTileFactory<T> {
    IGraphicTile<T> createTile(ITileActionsHandler handler, TileType type, int row, int col);
}
