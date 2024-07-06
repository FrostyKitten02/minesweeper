package si.afridau.minesweeper.logic;

import lombok.Getter;

@Getter
public enum TileType {
    EMPTY(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    BOMB(0);

    private final int numberOfBombsAround;
    TileType(int numberOfBombsAround) {
        this.numberOfBombsAround = numberOfBombsAround;
    }
}
