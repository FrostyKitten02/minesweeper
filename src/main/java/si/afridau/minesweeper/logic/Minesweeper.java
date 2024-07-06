package si.afridau.minesweeper.logic;


import lombok.Getter;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class Minesweeper<T> implements GameEventListener, IGameState {
    private final LogicTileFactory logicTileFactory;
    private final IGraphicTileFactory<T> graphicTileFactory;
    private final int maxMines;
    @Getter
    private final List<List<T>> boardTiles;
    private final ILogicTile[][] logicTiles;

    @Getter
    private GameState state = GameState.IN_PROGRESS;
    public Minesweeper(IGraphicTileFactory<T> graphicTileFactory, int height, int width, int mines) {
        if (height*width*0.3 < mines) {
            //can't have more than 30% of the board as mines
            throw new IllegalArgumentException("Too many mines for this board size");
        }
        maxMines = mines;
        this.logicTileFactory = new LogicTileFactory();
        this.graphicTileFactory = graphicTileFactory;
        Pair<ILogicTile[][], List<List<T>>> pair = createBoard(height, width);
        this.logicTiles = pair.getValue0();
        this.boardTiles = pair.getValue1();
        this.setLogicTileNeighbours(height, width);
    }

    private Pair<ILogicTile[][], List<List<T>>> createBoard(int height, int width) {
        boolean[][] mines = createMineBitMap(height, width, maxMines);
        ILogicTile[][] logicTiles = new ILogicTile[height][width];
        List<List<T>> board = new ArrayList<>(height);

        for (int i = 0; i < height; i++) {
            List<T> row = new ArrayList<>(width);
            board.add(row);
            for (int j = 0; j < width; j++) {
                TileType tileType = getTileType(mines, i, j, height, width);
                ILogicTile lTile = logicTileFactory.create(tileType, this);
                logicTiles[i][j] = lTile;
                IGraphicTile<T> gTile = graphicTileFactory.createTile(lTile, tileType);
                lTile.setGraphicTile(gTile);
                row.add(gTile.getTile());
            }
        }

        return new Pair<>(logicTiles, board);
    }

    private void setLogicTileNeighbours(int height, int width) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.logicTiles[i][j].setNeighbours(getNeighborTiles(this.logicTiles, i, j, height, width));
            }
        }
    }


    private boolean[][] createMineBitMap(int height, int width, int maxMines) {
        boolean[][] mines = new boolean[height][width];

        int minesPlaced = 0;
        do {
            long randomY = Math.round(Math.random() * (height - 1));
            long randomX = Math.round(Math.random() * (width - 1));
            if (!mines[(int) randomY][(int) randomX]) {
                mines[(int) randomY][(int) randomX] = true;
                minesPlaced++;
            }
        } while (minesPlaced < maxMines);

        return mines;
    }

    private ILogicTile[] getNeighborTiles(ILogicTile[][] tiles, int y, int x, int height, int width) {
        int neighborLeft = x - 1;
        int neighborRight = x + 1;

        int neighborTop = y - 1;
        int neighborBottom = y + 1;

        boolean leftOffBounds = neighborLeft < 0 || neighborLeft >= width;
        boolean rightOffBounds = neighborRight < 0 || neighborRight >= width;

        boolean topOffBounds = neighborTop < 0 || neighborTop >= height;
        boolean bottomOffBounds = neighborBottom < 0 || neighborBottom >= height;

        //left, right, up, down, up-left, up-right, down-left, down-right
        ILogicTile[] neighbours = new ILogicTile[8];
        if (!leftOffBounds) {
            ILogicTile leftTile = tiles[y][neighborLeft];
            neighbours[0] = leftTile;
        }

        if (!rightOffBounds) {
            ILogicTile rightTile = tiles[y][neighborRight];
            neighbours[1] = rightTile;
        }

        //now we check top neighbors
        if (!topOffBounds) {
            if (!leftOffBounds) {
                ILogicTile topLeftTile = tiles[neighborTop][neighborLeft];
                neighbours[4] = topLeftTile;
            }

            ILogicTile topTile = tiles[neighborTop][x];
            neighbours[2] = topTile;

            if (!rightOffBounds) {
                ILogicTile topRightTile = tiles[neighborTop][neighborRight];
                neighbours[5] = topRightTile;
            }
        }

        //and now bottom neighbors
        if (!bottomOffBounds) {
            if (!leftOffBounds) {
                ILogicTile bottomLeftTile = tiles[neighborBottom][neighborLeft];
                neighbours[6] = bottomLeftTile;
            }

            ILogicTile bottomTile = tiles[neighborBottom][x];
            neighbours[3] = bottomTile;

            if (!rightOffBounds) {
                ILogicTile bottomRightTile = tiles[neighborBottom][neighborRight];
                neighbours[7] = bottomRightTile;
            }
        }

        return neighbours;
    }


    //TODO probably faster if we go over mines array and then increment neighbors number, which will give us the number that is supposed to be on the tile
    private TileType getTileType(boolean[][] mines, int y, int x, int height, int width) {
        if (mines[y][x]) {
            return TileType.BOMB;
        }

        int neighborLeft = x - 1;
        int neighborRight = x + 1;

        int neighborTop = y - 1;
        int neighborBottom = y + 1;

        boolean leftOffBounds = neighborLeft < 0 || neighborLeft >= width;
        boolean rightOffBounds = neighborRight < 0 || neighborRight >= width;

        boolean topOffBounds = neighborTop < 0 || neighborTop >= height;
        boolean bottomOffBounds = neighborBottom < 0 || neighborBottom >= height;

        int mineCount = 0;
        //first we check left and right if possible
        if (!leftOffBounds && mines[y][neighborLeft]) {
            mineCount++;
        }

        if (!rightOffBounds && mines[y][neighborRight]) {
            mineCount++;
        }

        //now we check top neighbors
        if (!topOffBounds) {
            if (!leftOffBounds && mines[neighborTop][neighborLeft]) {
                mineCount++;
            }

            if (mines[neighborTop][x]) {
                mineCount++;
            }

            if (!rightOffBounds && mines[neighborTop][neighborRight]) {
                mineCount++;
            }
        }

        //and now bottom neighbors
        if (!bottomOffBounds) {
            if (!leftOffBounds && mines[neighborBottom][neighborLeft]) {
                mineCount++;
            }

            if (mines[neighborBottom][x]) {
                mineCount++;
            }

            if (!rightOffBounds && mines[neighborBottom][neighborRight]) {
                mineCount++;
            }
        }

        switch (mineCount) {
            case 0:
                return TileType.EMPTY;
            case 1:
                return TileType.ONE;
            case 2:
                return TileType.TWO;
            case 3:
                return TileType.THREE;
            case 4:
                return TileType.FOUR;
            case 5:
                return TileType.FIVE;
            case 6:
                return TileType.SIX;
            case 7:
                return TileType.SEVEN;
            case 8:
                return TileType.EIGHT;
            default:
                throw new IllegalStateException("Unexpected value: " + mineCount);
        }
    }


    @Override
    public void gameOver() {
        this.state = GameState.LOST;
    }

    @Override
    public void checkGameWin() {
        //TODO implement
    }
}
