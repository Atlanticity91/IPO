package terrain;

import entities.GameEntity;
import entities.GameEntityManager;
import inputs.GameInputManager;
import utils.GameStateManager;
import utils.Vector2;

import java.awt.*;

public abstract class GameTileInteractable extends GameTile implements GameTileTickable {

    private GameEntity m_entity;

    public GameTileInteractable( Vector2 location, Vector2 dimensions, Color color, boolean is_traversable ) {
        super( is_traversable, location, dimensions, color );

        m_entity = null;
    }

    protected void setEntity( GameEntity entity ) { m_entity = entity; }

    public void onEnter(
            GameStateManager state_manager,
            GameTilemap tilemap,
            GameTileInteractable previous,
            GameEntity entity,
            Vector2 offset
    ) {
        if ( previous != null )
            previous.onLeave( );

        m_entity = entity;
    }

    public void onLeave( ) { m_entity = null; }

    public abstract void tick(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameEntityManager entity_manager,
            GameTilemap tilemap,
            float delta_time
    );

    public GameEntity getEntity( ) { return m_entity; }

}
