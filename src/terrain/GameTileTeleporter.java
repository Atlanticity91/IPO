package terrain;

import entities.GameEntity;
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

    private GameTile teleport( GameTilemap tilemap, GameTile dst, GameEntity entity ) {
        final Vector2 offset = entity.getDimensions( ).div( 2.f );
        final Vector2 tile_center = dst.getLocation( ).add( tilemap.getTileDimensions( ).div( 2.f ) );

        entity.setLocation( tile_center.sub( offset ) );

        return dst;
    }

    @Override
    public void onEnter(
            GameStateManager state_manager,
            GameTilemap tilemap,
            GameTileInteractable previous,
            GameEntity entity,
            Vector2 offset
    ) {
        if ( entity == null )
            return;

        super.onEnter( state_manager, tilemap, previous, entity, offset );

        ArrayList<GameTileTeleporter> dest_list = tilemap.getAllTiles( GameTileTeleporter.class, this );
        GameTile dest = null;

        if ( dest_list != null ) {
            final int dest_id = state_manager.randInt( 0, dest_list.size( ) );

            dest = teleport( tilemap, dest_list.get( dest_id ), entity );
        } else
            dest = teleport( tilemap, state_manager.randTile( this ), entity );

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
            GameTilemap tilemap,
            float delta_time
    ) {
    }

    @Override
    public void display( GameRenderManager render_manager ){
        super.display( render_manager );

        render_manager.drawSprite( "teleport", getLocation( ), getDimensions( ), 0, 0 );
    }

}
