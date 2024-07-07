package si.afridau.minesweeper.swingimpl;

import lombok.Getter;
import lombok.Setter;
import si.afridau.minesweeper.logic.IGraphicTile;
import si.afridau.minesweeper.logic.ITileActionsHandler;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SwingGraphicTile implements IGraphicTile<JButton> {
    @Getter
    private final JButton tile;
    @Setter
    private boolean userPressed = false;
    private final TileColorSet colorSet;
    private final TextureSet textureSet;

    public SwingGraphicTile(ITileActionsHandler handler, TileColorSet colorSet, TextureSet textureSet) {
        this.tile = createButton();
        this.colorSet = colorSet;
        this.textureSet = textureSet;
        displayNormalClosed();
        this.tile.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
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
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    handler.onTileRelease();
                    return;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                handler.hover();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                handler.hoverExit();
            }
        });
    }

    private JButton createButton() {
        JButton button = new JButton();
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        return button;
    }

    @Override
    public void displayOpenGraphic() {
        tile.setBackground(colorSet.getOpened());
        tile.setIcon(textureSet.getGraphic());
        tile.setBorder(BorderFactory.createLoweredBevelBorder());
    }

    @Override
    public void displayFlagGraphic() {
        tile.setIcon(textureSet.getFlag());
    }

    @Override
    public void removeGraphic() {
        tile.setIcon(null);
    }

    @Override
    public void displayHoverOpened() {
        if (userPressed) {
            tile.setBackground(colorSet.getHoverOpenedUserPressed());
            return;
        }
        tile.setBackground(colorSet.getHoverOpened());
    }

    @Override
    public void displayHoverClosed() {
        tile.setBackground(colorSet.getHoverClosed());
    }

    @Override
    public void displayNormalClosed() {
        tile.setBorder(BorderFactory.createRaisedBevelBorder());
        tile.setBackground(colorSet.getClosed());
    }

    @Override
    public void displayNormalOpened() {
        tile.setBorder(BorderFactory.createLoweredBevelBorder());
        if (userPressed) {
            tile.setBackground(colorSet.getOpenedUserPressed());
            return;
        }
        tile.setBackground(colorSet.getOpened());
    }
}
