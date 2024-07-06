package si.afridau.minesweeper.logic;


public class LogicTileFactory {
    LogicTile create(TileType type, Minesweeper<?> game) {
        return new LogicTile(type, game, game);
    }
}
