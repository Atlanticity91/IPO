package terrain;

import graphics.GameRenderManager;
import utils.Hitbox;
import utils.Vector2;

import java.awt.*;

public abstract class GameTile {

    private final boolean m_is_traversable;
    private final Vector2 m_location;
    private final Vector2 m_dimensions;
    protected final Color m_color;

    public GameTile( boolean is_traversable, Vector2 location, Vector2 dimensions, Color color ) {
        m_is_traversable = is_traversable;
        m_location = location;
        m_dimensions = dimensions;
        m_color = color;
    }

    public void display( GameRenderManager render_manager ) {
        render_manager.drawRect( m_location, m_dimensions, m_color );
    }

    public boolean isTraversable( ) { return m_is_traversable; }

    public Vector2 getLocation( ) { return m_location; }

    public Hitbox getLocalHitbox( ) {
        return new Hitbox( m_location, m_dimensions );
    }

}
