package terrain;

import entities.GameEntity;
import entities.GameEntityManager;
import entities.GameEntityPlayer;
import inputs.GameInputManager;
import utils.GameStateManager;
import utils.Vector2;

import java.awt.*;

public class GameTileHole extends GameTileInteractable {

    public GameTileHole(Vector2 location, Vector2 dimensions ) {
        super( location, dimensions, Color.GREEN, true );
    }

    @Override
    public void onEnter(
            GameStateManager state_manager,
            GameTilemap tilemap,
            GameTileInteractable previous,
            GameEntity entity,
            Vector2 offset
    ) {
        if ( entity == null || previous == null )
            return;

        if ( !( entity instanceof GameEntityPlayer ) )
            return;

        final Vector2 location = state_manager.getSpawnPoint( );

        state_manager.subLive( );
        entity.setLocation( location );
    }

    @Override
    public void tick(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameEntityManager entity_manager,
            GameTilemap tilemap
    ) {
    }

}
