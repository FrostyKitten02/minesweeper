package si.afridau.minesweeper.swingimpl;

import lombok.RequiredArgsConstructor;
import si.afridau.minesweeper.logic.IGameListener;
import si.afridau.minesweeper.logic.Minesweeper;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

@RequiredArgsConstructor
public class SwingGame implements IGameListener {
    private final JFrame frame;
    private int width;
    private int height;
    private int mines;

    public SwingGame() {
        this.frame = new JFrame("Minesweeper");
        this.frame.setSize(800, 600);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        JButton restartBtn = new JButton("Restart");
        menuBar.add(restartBtn);
        restartBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                SwingGame.this.restartGame();
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
        this.frame.setJMenuBar(menuBar);
    }

    public void startGame(int width, int height, int mines) {
        this.width = width;
        this.height = height;
        this.mines = mines;

        setBoard(width, height, mines);
        this.frame.setVisible(true);
    }

    private void restartGame() {
        Container container = frame.getContentPane();
        setBoard(width, height, mines);
        container.revalidate();
        container.repaint();
    }

    private void setBoard(int width, int height, int mines) {
        Container container = frame.getContentPane();
        container.removeAll();
        container.setLayout(new GridLayout(height, width));

        TextureFactory textureFactory = new TextureFactory();
        SwingGraphicTileFactory factory = new SwingGraphicTileFactory(textureFactory);
        Minesweeper<JButton> minesweeper = new Minesweeper<>(factory, height, width, mines);

        minesweeper.addGameListener(this);
        for (List<JButton> row : minesweeper.getBoardTiles()) {
            for (JButton btn : row) {
                container.add(btn);
            }
        }
    }

    @Override
    public void gameWon() {
        JOptionPane.showMessageDialog(frame, "GAME WON!");
    }

    @Override
    public void gameLost() {
        JOptionPane.showMessageDialog(frame, "GAME LOST!");
    }
}
