package entities;

import graphics.GameRenderManager;
import inputs.GameInputManager;
import terrain.GameTilemap;
import utils.GameStateManager;
import utils.Vector2;

public class GameEntityPlayer extends GameEntity {

    public GameEntityPlayer( Vector2 location ) {
        super( location );
    }

    @Override
    public void onCollide(
            GameStateManager state_manager,
            GameEntity entity,
            Vector2 offset
    ) {
        if ( entity instanceof GameEntityPlayer player )
            state_manager.subLive( );
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
    }

}
