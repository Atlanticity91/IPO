package terrain;

import entities.GameEntity;
import entities.GameEntityManager;
import entities.GameEntityPlayer;
import graphics.GameRenderManager;
import inputs.GameInputManager;
import utils.GameStateManager;
import utils.Vector2;

import java.awt.*;

public class GameTileIce extends GameTileInteractable {

    private float m_slippery_factor = 0.025f;

    public GameTileIce( Vector2 location, Vector2 dimensions ) {
        super( location, dimensions, Color.blue, true );
    }

    @Override
    public void onEnter(
            GameStateManager state_manager,
            GameTilemap tilemap,
            GameTileInteractable previous,
            GameEntity entity,
            Vector2 offset
    ) {
        super.onEnter( state_manager, tilemap, previous, entity, offset );

        if ( entity instanceof GameEntityPlayer p )
            p.setSlipperyFactor( m_slippery_factor );
    }

    @Override
    public void onLeave( ) {
        GameEntity entity = getEntity( );

        if ( entity instanceof GameEntityPlayer p )
            p.setSlipperyFactor( 0.f );

        super.onLeave( );
    }

    @Override
    public void tick(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameEntityManager entity_manager,
            GameTilemap tilemap,
            float delta_time
    ) {
    }

    @Override
    public void display( GameRenderManager render_manager ){
        super.display( render_manager );

        render_manager.drawSprite( "ice", getLocation( ), getDimensions( ), 0, 0 );
    }

}
