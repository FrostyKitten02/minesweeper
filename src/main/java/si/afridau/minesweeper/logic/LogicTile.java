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
    @Getter
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
        if (this.state == TileState.OPENED) {
            pressDownNeighbours();
        }
        onTileOpenInternal();
        gameEventListener.checkGameWin();
    }

    @Override
    public void onTileRelease() {
        if (this.state != TileState.OPENED) {
            return;
        }
        unHoverNeighbours();
        checkNeighboursForBombs();
    }

    //this is used when user actually clicks to open the tile
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

    @Override
    public void hover() {
        if (graphicTile == null) {
            throw new RuntimeException("No graphic tile set on logic tile!");
        }

        displayHover();
    }

    @Override
    public void displayHover() {
        if (state == TileState.CLOSED || state == TileState.FLAGGED) {
            graphicTile.displayHoverClosed();
            return;
        }

        graphicTile.displayHoverOpened();
    }


    @Override
    public void hoverExit() {
        if (graphicTile == null) {
            throw new RuntimeException("No graphic tile set on logic tile!");
        }

        displayNormal();
    }

    @Override
    public void displayNormal() {
        if (state == TileState.CLOSED || state == TileState.FLAGGED) {
            graphicTile.displayNormalClosed();
            return;
        }

        graphicTile.displayNormalOpened();
    }


    //this is then used for recursive calls so we can differentiate between user click and internal call
    @Override
    public void onTileOpenInternal() {
        if (graphicTile == null) {
            throw new RuntimeException("No graphic tile set on logic tile!");
        }

        if (!canOpen()) {
            return;
        }

        this.state = TileState.OPENED;
        graphicTile.displayOpenGraphic();
        switch (type) {
            case BOMB:
                this.gameEventListener.gameOver();
                break;
            case EMPTY:
                for (ILogicTile neighbour : neighbours) {
                    if (neighbour == null || neighbour.getType() == TileType.BOMB) {
                        continue;
                    }

                    neighbour.onTileOpenInternal();
                }
                break;
            default:
                for (ILogicTile neighbour : neighbours) {
                    if (neighbour == null || neighbour.getType() != TileType.EMPTY) {
                        continue;
                    }

                    neighbour.onTileOpenInternal();
                }
                break;
        }
        graphicTile.displayOpenGraphic();
    }

    private void pressDownNeighbours() {
        for (ILogicTile neighbour : neighbours) {
            if (neighbour == null) {
                continue;
            }

            if (neighbour.getState() == TileState.OPENED || neighbour.getState() == TileState.FLAGGED) {
                continue;
            }

            neighbour.displayHover();
        }
    }

    private void unHoverNeighbours() {
        for (ILogicTile neighbour : neighbours) {
            if (neighbour == null) {
                continue;
            }

            if (neighbour.getState() == TileState.OPENED || neighbour.getState() == TileState.FLAGGED) {
                continue;
            }

            neighbour.displayNormal();
        }
    }

    private void checkNeighboursForBombs() {
        int flags = 0;
        final int numberOfBombsAround = this.getType().getNumberOfBombsAround();
        for (ILogicTile neighbour : neighbours) {
            if (neighbour == null) {
                continue;
            }

            if (neighbour.getState() == TileState.OPENED) {
                continue;
            }

            if (neighbour.getState() == TileState.FLAGGED) {
                flags++;
            }
        }

        if (flags != numberOfBombsAround) {
            return;
        }

        for (ILogicTile neighbour : neighbours) {
            if (neighbour == null) {
                continue;
            }

            if (neighbour.getState() == TileState.OPENED) {
                continue;
            }

            neighbour.onTileOpenInternal();
        }
    }

    //opens all neighbours that regardless if they are flagged or not
    //it will un-flag all miss flagged tiles and then open all of un-flagged tiles
    private void openNeighbours() {
        for (ILogicTile neighbour : neighbours) {
            if (neighbour == null) {
                continue;
            }

            if (neighbour.getState() == TileState.OPENED) {
                continue;
            }

            if (neighbour.getState() == TileState.FLAGGED && neighbour.getType() == TileType.BOMB) {
                neighbour.placeFlag();
            }

            neighbour.onTileOpenInternal();
        }
    }

    public boolean canOpen() {
        return state == TileState.CLOSED && gameState.getState().isCanContinue();
    }

    public boolean canPlaceOrRemoveFlag() {
        return state != TileState.OPENED && gameState.getState().isCanContinue();
    }


}
