package si.afridau.minesweeper.logic;


public class LogicTileFactory {
    LogicTile create(TileType type) {
        return new LogicTile(type);
    }
}
