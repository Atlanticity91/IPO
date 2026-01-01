package terrain;

import entities.GameEntityManager;
import graphics.GameRenderManager;
import inputs.GameInputManager;
import utils.GameStateManager;
import utils.Vector2;

import java.awt.*;
import java.util.ArrayList;

public class GameTileTeleporter extends GameTileInteractable {

    public GameTileTeleporter( Vector2 location, Vector2 dimensions, Color color ) {
        super( location, dimensions, color, true );
    }

    private GameTile teleport( GameTilemap tilemap, GameTile dst, Object entity, Vector2 offset ) {
        final Vector2 location = /*entity.getDirection( )*/ new Vector2( )
                .scale( tilemap.getTileDimensions( ) )
                .add( 0.f/*entity.getLocation( )*/ );

        //TODO Add calculation for the position on the new tile with deplacement of 'offset'
        //entity.setLocation( location.add( tilemap.getTileDimensions( ).scale( .5f ) ) );

        return tilemap.getTile( location );
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

        ArrayList<GameTileTeleporter> dest_list = tilemap.getAllTiles( GameTileTeleporter.class, this );
        GameTile dest = null;

        if ( dest_list != null ) {
            final int dest_id = state_manager.randInt( 0, dest_list.size( ) );

            dest = teleport( tilemap, dest_list.get( dest_id ), entity, offset );
        } else
            dest = teleport( tilemap, state_manager.randTile( this ), entity, offset );

        if ( dest instanceof GameTileInteractable interactable )
            interactable.onEnter( state_manager, tilemap, this, entity, Vector2.Zero );
        else
            onLeave( );
    }

    @Override
    public void tick(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameEntityManager entity_manager,
            GameTilemap tilemap
    ) {
    }

    @Override
    public void display( GameRenderManager render_manager ) {
    }

}
