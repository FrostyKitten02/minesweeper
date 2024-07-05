package si.afridau.minesweeper.logic;

import lombok.Getter;

public class LogicTile implements ILogicTile {
    @Getter
    private final TileType type;

    public LogicTile(TileType type) {
        this.type = type;
    }

    @Override
    public void onTileOpen() {
        System.out.println("Tile opened action!");
    }
}
