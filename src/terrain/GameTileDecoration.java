package terrain;

import utils.Vector2;

import java.awt.*;

public class GameTileDecoration extends GameTile {

    private boolean m_is_traversable;

    public GameTileDecoration( Vector2 location, Vector2 dimensions, boolean is_traversable ) {
        super( location, dimensions, is_traversable ? Color.white : Color.black );

        m_is_traversable = is_traversable;
    }

    @Override
    public boolean isTraversable( ) { return m_is_traversable; }

}
