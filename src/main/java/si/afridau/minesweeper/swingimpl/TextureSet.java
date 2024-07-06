package si.afridau.minesweeper.swingimpl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.swing.ImageIcon;

@Getter
@RequiredArgsConstructor
public class TextureSet {
    private final ImageIcon graphic;
    private final ImageIcon flag;
    private final boolean enabledOnTexture;
}
