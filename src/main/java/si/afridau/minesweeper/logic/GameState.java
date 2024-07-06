package si.afridau.minesweeper.logic;

import lombok.Getter;

@Getter
public enum GameState {
    IN_PROGRESS(true),
    WON(false),
    LOST(false);
    private final boolean canContinue;
    GameState(boolean canContinue) {
        this.canContinue = canContinue;
    }
}
