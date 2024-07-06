package si.afridau.minesweeper.swingimpl;

import si.afridau.minesweeper.logic.TileType;

import javax.swing.ImageIcon;
import java.net.URL;

public class TextureFactory {
    private static final ImageIcon flag = loadImage("swingimpl/textures/tiles/flag.png");
    private static final ImageIcon bomb = loadImage("swingimpl/textures/tiles/bomb.png");

    private static final ImageIcon number1 = loadImage("swingimpl/textures/tiles/number1.png");
    private static final ImageIcon number2 = loadImage("swingimpl/textures/tiles/number2.png");
    private static final ImageIcon number3 = loadImage("swingimpl/textures/tiles/number3.png");
    private static final ImageIcon number4 = loadImage("swingimpl/textures/tiles/number4.png");
    private static final ImageIcon number5 = loadImage("swingimpl/textures/tiles/number5.png");
    private static final ImageIcon number6 = loadImage("swingimpl/textures/tiles/number6.png");
    private static final ImageIcon number7 = loadImage("swingimpl/textures/tiles/number7.png");
    private static final ImageIcon number8 = loadImage("swingimpl/textures/tiles/number8.png");


    private static final TextureSet emptySet = new TextureSet(null, flag, false);
    private static final TextureSet bombset = new TextureSet(bomb, flag, false);
    private static final TextureSet number1set = new TextureSet(number1, flag, true);
    private static final TextureSet number2set = new TextureSet(number2, flag, true);
    private static final TextureSet number3set = new TextureSet(number3, flag, true);
    private static final TextureSet number4set = new TextureSet(number4, flag, true);
    private static final TextureSet number5set = new TextureSet(number5, flag, true);
    private static final TextureSet number6set = new TextureSet(number6, flag, true);
    private static final TextureSet number7set = new TextureSet(number7, flag, true);
    private static final TextureSet number8set = new TextureSet(number8, flag, true);


    private static ImageIcon loadImage(String path) {
        URL url = TextureFactory.class.getClassLoader().getResource(path);
        if (url == null) {
            throw new IllegalArgumentException("Resource not found: " + path);
        }
        return new ImageIcon(url);
    }


    public TextureSet create(TileType type) {
        return switch (type) {
            case BOMB -> bombset;
            case ONE -> number1set;
            case TWO -> number2set;
            case THREE -> number3set;
            case FOUR -> number4set;
            case FIVE -> number5set;
            case SIX -> number6set;
            case SEVEN -> number7set;
            case EIGHT -> number8set;
            case EMPTY -> emptySet;
            default -> throw new IllegalArgumentException("Unknown tile type: " + type);
        };
    }

}
