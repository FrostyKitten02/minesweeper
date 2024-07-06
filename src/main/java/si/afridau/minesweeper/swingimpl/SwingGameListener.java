package si.afridau.minesweeper.swingimpl;

import lombok.RequiredArgsConstructor;
import si.afridau.minesweeper.logic.IGameListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

@RequiredArgsConstructor
public class SwingGameListener implements IGameListener {
    private final JFrame frame;
    @Override
    public void gameWon() {
        JOptionPane.showMessageDialog(frame, "GAME WON!");
    }

    @Override
    public void gameLost() {
        JOptionPane.showMessageDialog(frame, "GAME LOST!");
    }
}
