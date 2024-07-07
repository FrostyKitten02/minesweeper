package si.afridau.minesweeper.swingimpl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.awt.Color;

@Getter
@RequiredArgsConstructor
public class TileColorSet {
    private final Color opened;
    private final Color closed;

    private final Color hoverOpened;
    private final Color hoverClosed;

    private final Color openedUserPressed;
    private final Color hoverOpenedUserPressed;
}
