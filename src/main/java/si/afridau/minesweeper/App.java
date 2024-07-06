package si.afridau.minesweeper;


import si.afridau.minesweeper.logic.Minesweeper;
import si.afridau.minesweeper.swingimpl.SwingGameListener;
import si.afridau.minesweeper.swingimpl.SwingGraphicTileFactory;
import si.afridau.minesweeper.swingimpl.TextureFactory;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import java.util.List;


public class App {
    public static void main( String[] args ) {
        JFrame frame = new JFrame("Minesweeper");
        frame.setLayout(new GridLayout(10, 10));

        TextureFactory textureFactory = new TextureFactory();
        SwingGraphicTileFactory factory = new SwingGraphicTileFactory(textureFactory);
        Minesweeper<JButton> minesweeper = new Minesweeper<>(factory, 10, 10, 10);

        SwingGameListener listener = new SwingGameListener(frame);
        minesweeper.addGameListener(listener);
        for (List<JButton> row : minesweeper.getBoardTiles()) {
            for (JButton btn : row) {
                frame.add(btn);
            }
        }

        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
