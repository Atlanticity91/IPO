package terrain;

import entities.GameEntityManager;
import inputs.GameInputManager;
import utils.GameStateManager;
import utils.Vector2;

import java.awt.*;

public abstract class GameTileInteractable extends GameTile {

    private Object m_entity;

    public GameTileInteractable( Vector2 location, Vector2 dimensions, Color color ) {
        super( location, dimensions, color );

        m_entity = null;
    }

    public void onEnter( Object entity ) { m_entity = entity; }

    public void onLeave( ) { m_entity = null; }

    public abstract void tick(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameEntityManager entity_manager,
            GameTilemap tilemap
    );

    @Override
    public boolean isTraversable( ) { return true; }

    public Object getEntity( ) { return m_entity; }

}
