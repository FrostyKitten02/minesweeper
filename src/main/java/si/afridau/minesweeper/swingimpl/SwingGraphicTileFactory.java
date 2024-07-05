package si.afridau.minesweeper.swingimpl;

import si.afridau.minesweeper.logic.IGraphicTile;
import si.afridau.minesweeper.logic.IGraphicTileFactory;
import si.afridau.minesweeper.logic.ITileActionsHandler;
import si.afridau.minesweeper.logic.TileType;

import javax.swing.JButton;

public class SwingGraphicTileFactory implements IGraphicTileFactory<JButton> {

    @Override
    public IGraphicTile<JButton> createTile(ITileActionsHandler handler, TileType type) {
        return new SwingGraphicTile(handler, type);
    }
}
