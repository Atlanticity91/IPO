package entities;

import graphics.GameRenderManager;
import inputs.GameInputManager;
import terrain.GameTilemap;
import utils.GameDirection;
import utils.GameStateManager;
import utils.Vector2;

public class GameEntityLaser extends GameEntity {

    public GameEntityLaser( Vector2 location ) {
        super( location, Vector2.Zero );
    }

    public void trace( GameTilemap tilemap, GameDirection direction ) {
    }

    @Override
    public void onCollide(
            GameStateManager state_manager,
            GameEntityManager entity_manager,
            GameEntity entity,
            Vector2 offset
    ) {
        if ( entity instanceof GameEntityPlayer p ){
            state_manager.subLive( );

            p.setLocation( state_manager.getSpawnPoint( ) );
        }
    }

    @Override
    public void tick(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameTilemap tilemap,
            GameEntityManager entity_manager
    ) {
    }

    @Override
    public void display( GameRenderManager render_manager ) {
        //render_manager.drawRect( );
    }

}
