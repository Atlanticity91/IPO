package terrain;

import entities.GameEntityManager;
import inputs.GameInputManager;
import utils.GameDirection;
import utils.GameStateManager;
import utils.Vector2;

import java.awt.*;

public class GameTileTunnel extends GameTileInteractable {

    private final GameDirection m_direction;

    public GameTileTunnel(Vector2 location, Vector2 dimensions, GameDirection direction ) {
        super( location, dimensions, Color.yellow, true );

        m_direction = direction;
    }

    @Override
    public void onEnter(
            GameStateManager state_manager,
            GameTilemap tilemap,
            GameTileInteractable previous,
            Object entity,
            Vector2 offset
    ) {
        super.onEnter( state_manager, tilemap, previous, entity, offset );

        //if ( entity instanceof GameEntityPlayer player )
        //player.setAvailableDirection( m_direction );
    }

    @Override
    public void onLeave( ) {
        //GameEntity entity = getEntity( );
        //if ( entity instanceof GameEntityPlayer player )
        //player.setAvailableDirection( GameDirection.None );

        super.onLeave( );
    }

    @Override
    public void tick(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameEntityManager entity_manager,
            GameTilemap tilemap
    ) {
    }

    public GameDirection getDirection( ) { return m_direction; }

}
