package si.afridau.minesweeper.swingimpl;

import lombok.Getter;
import si.afridau.minesweeper.logic.IGraphicTile;
import si.afridau.minesweeper.logic.ITileActionsHandler;
import si.afridau.minesweeper.logic.TileType;

import javax.swing.JButton;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SwingGraphicTile implements IGraphicTile<JButton> {
    @Getter
    private final JButton tile;
    public SwingGraphicTile(ITileActionsHandler handler, TileType type) {
        tile = new JButton(type.name());
        if (type == TileType.EMPTY) {
            tile.setText("");
        }
        tile.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handler.onTileOpen();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }
}
