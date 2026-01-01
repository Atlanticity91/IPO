package terrain;

import entities.GameEntityManager;
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
            Object entity,
            Vector2 offset
    ) {
        if ( entity == null || previous == null )
            return;

        if ( entity instanceof Object )
            state_manager.subLive( );

        final Vector2 location_offset = tilemap.getTileDimensions( ).sub( 0.f /*entity.getDimensions( )*/ ).div( .5f );
        //entity.setLocation( previous.getLocation( ).add( location_offset ) );
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
