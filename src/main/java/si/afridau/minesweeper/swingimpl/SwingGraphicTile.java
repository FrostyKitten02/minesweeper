package si.afridau.minesweeper.swingimpl;

import lombok.Getter;
import si.afridau.minesweeper.logic.IGraphicTile;
import si.afridau.minesweeper.logic.ITileActionsHandler;
import si.afridau.minesweeper.logic.TileType;

import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SwingGraphicTile implements IGraphicTile<JButton> {
    @Getter
    private final JButton tile;
    private final String tempOpenText;
    public SwingGraphicTile(ITileActionsHandler handler, TileType type) {
        tile = new JButton();
        tempOpenText = type.name();
        tile.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    handler.placeFlag();
                    return;
                }

                if (SwingUtilities.isLeftMouseButton(e)) {
                    handler.onTileOpen();
                    return;
                }
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

    @Override
    public void displayOpenGraphic() {
        tile.setText(tempOpenText);
        tile.setEnabled(false);
        tile.setBorder(new BasicBorders.ButtonBorder(Color.RED, Color.RED, Color.RED, Color.RED));
    }

    @Override
    public void displayFlagGraphic() {
        tile.setText("F");
    }

    @Override
    public void removeGraphic() {
        tile.setText("");
    }
}
