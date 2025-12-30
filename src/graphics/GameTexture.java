package graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameTexture {

    public final BufferedImage Image;
    private final int m_columns;
    private final int m_rows;
    private final int m_sprite_width;
    private final int m_sprite_height;

    public GameTexture( BufferedImage image, int columns, int rows ) {
        Image = image;
        m_columns = columns;
        m_rows = rows;
        m_sprite_width = image.getWidth( ) / columns;
        m_sprite_height = image.getHeight( ) / rows;
    }

    public GameSprite getSprite( int column, int row ) {
        if ( column < 0 || column >= m_columns || row < 0 || row >= m_rows )
            return null;

        final int sprite_x = column * m_sprite_width;
        final int sprite_y = row * m_sprite_height;

        return new GameSprite( sprite_x, sprite_y, sprite_x + m_sprite_width, sprite_y + m_sprite_height );
    }

}
