package graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Hashtable;

public class GameTextureManager {

    private Hashtable<String, GameTexture> m_textures;

    public GameTextureManager( ) {
        m_textures = new Hashtable<>( );
    }

    public boolean load( String alias, String path, int columns, int rows ) {
        if ( m_textures.containsKey( alias ) )
            return false;

        try {
            //BufferedImage texture = ImageIO.read( new File( path ) );
            BufferedImage texture = ImageIO.read( getClass().getResource( path ) );

            if ( texture != null )
                m_textures.put( alias, new GameTexture( texture, columns, rows ) );
        } catch ( IOException e ) {
            e.printStackTrace( );
        }

        return false;
    }

    public GameTexture get( String alias ) { return m_textures.get( alias ); }

}
