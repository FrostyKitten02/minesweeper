package si.afridau.minesweeper.logic;

import lombok.Getter;
import lombok.Setter;

public class LogicTile implements ILogicTile {
    @Getter
    private final TileType type;
    @Setter
    private ILogicTile[] neighbours;
    @Setter()
    private IGraphicTile<?> graphicTile = null;
    private TileState state = TileState.CLOSED;
    private final GameEventListener gameEventListener;
    private final IGameState gameState;

    public LogicTile(TileType type, GameEventListener gameEventListener, IGameState gameState) {
        this.type = type;
        this.gameEventListener = gameEventListener;
        this.gameState = gameState;
    }

    //this is used when user actually clicks to open the tile
    @Override
    public void onTileOpen() {
       onTileOpenInternal(true);
       gameEventListener.checkGameWin();
    }

    @Override
    public void placeFlag() {
        if (graphicTile == null) {
            throw new RuntimeException("No graphic tile set on logic tile!");
        }

        if (!canPlaceOrRemoveFlag()) {
            return;
        }

        if (state == TileState.FLAGGED) {
            state = TileState.CLOSED;
            graphicTile.removeGraphic();
            return;
        }

        if (state == TileState.CLOSED) {
            state = TileState.FLAGGED;
            graphicTile.displayFlagGraphic();
        }
    }

    //this is then used for recursive calls so we can differentiate between user click and internal call
    @Override
    public void onTileOpenInternal(boolean userInput) {
        if (graphicTile == null) {
            throw new RuntimeException("No graphic tile set on logic tile!");
        }

        if (!canOpen()) {
            return;
        }

        graphicTile.displayOpenGraphic();
        this.state = TileState.OPENED;
        switch (type) {
            case BOMB:
                if (!userInput) {
                    break;
                }
                this.gameEventListener.gameOver();
                break;
            case EMPTY:
                for (ILogicTile neighbour : neighbours) {
                    if (neighbour == null) {
                        continue;
                    }
                    neighbour.onTileOpenInternal(false);
                }
                break;
            default:
                /**
                 for (ILogicTile neighbour : neighbours) {
                 if (neighbour.getType() == TileType.EMPTY) {
                 neighbour.onTileOpenInternal(false);
                 }
                 }**/
                break;
        }
        graphicTile.displayOpenGraphic();
    }

    public boolean canOpen() {
        return state == TileState.CLOSED && gameState.getState().isCanContinue();
    }

    public boolean canPlaceOrRemoveFlag() {
        return (state == TileState.FLAGGED || state == TileState.CLOSED) && gameState.getState().isCanContinue();
    }


}
