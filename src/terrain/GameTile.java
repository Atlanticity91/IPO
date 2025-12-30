package terrain;

import graphics.GameRenderManager;
import utils.Vector2;

import java.awt.*;

public abstract class GameTile {

    private final Vector2 m_location;
    private final Vector2 m_dimensions;
    private final Color m_color;

    public GameTile( Vector2 location, Vector2 dimensions, Color color ) {
        m_location = location;
        m_dimensions = dimensions;
        m_color = color;
    }

    public void display( GameRenderManager render_manager ) {
        render_manager.drawRect( m_location, m_dimensions, m_color );
    }

    public abstract boolean isTraversable( );

}
