package terrain;

import graphics.GameRenderManager;
import utils.Vector2;

import java.awt.*;

public class GameTile {

    private final Vector2 m_location;
    private final Vector2 m_dimensions;

    public GameTile( Vector2 location, Vector2 dimensions ) {
        m_location = location;
        m_dimensions = dimensions;
    }

    public void display( GameRenderManager render_manager ) {
        render_manager.drawRect( m_location, m_dimensions );
    }

}
