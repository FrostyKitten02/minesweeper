package si.afridau.minesweeper.swingimpl;

import si.afridau.minesweeper.logic.IGraphicTile;
import si.afridau.minesweeper.logic.IGraphicTileFactory;
import si.afridau.minesweeper.logic.ITileActionsHandler;
import si.afridau.minesweeper.logic.TileType;

import javax.swing.JButton;
import java.awt.Color;

public class SwingGraphicTileFactory implements IGraphicTileFactory<JButton> {
    private static final TileColorSet oddSet = new TileColorSet(
            new Color( 0xe89eff ),
            new Color( 0xb8e5ff ),
            new Color( 0x6e0090 ),
            new Color( 0x006199 )
    );

    private static final TileColorSet evenSet = new TileColorSet(
            new Color(0xdb69ff),
            new Color(0x80d0ff),
            new Color(0x5f007d),
            new Color(0x005486)
    );

    private static final TileColorSet oddSetBomb = new TileColorSet(
            new Color(0xFF0000),
            new Color( 0xb8e5ff ),
            new Color(0x7D0000),
            new Color( 0x006199 )
    );

    private static final TileColorSet evenSetBomb = new TileColorSet(
            new Color(0xFF0000),
            new Color(0x80d0ff),
            new Color(0x7D0000),
            new Color(0x005486)
    );

    private final TextureFactory textureFactory;
    public SwingGraphicTileFactory(TextureFactory textureFactory) {
        this.textureFactory = textureFactory;
    }

    @Override
    public IGraphicTile<JButton> createTile(ITileActionsHandler handler, TileType type, int row, int col) {
        return new SwingGraphicTile(handler, getColorSet(row + col, type), textureFactory.create(type));
    }

    private TileColorSet getColorSet(int number, TileType type) {
        boolean isEven = number % 2 == 0;
        boolean isBomb = type == TileType.BOMB;

        if (isEven && isBomb) {
            return evenSetBomb;
        }

        if (!isEven && isBomb) {
            return oddSetBomb;
        }

        if (isEven) {
            return evenSet;
        }

        return oddSet;
    }
}
