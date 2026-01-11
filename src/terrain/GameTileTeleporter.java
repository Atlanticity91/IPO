package terrain;

import entities.GameEntity;
import entities.GameEntityManager;
import entities.GameEntityPlayer;
import graphics.GameRenderManager;
import inputs.GameInputManager;
import utils.GameStateManager;
import utils.Vector2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameTileTeleporter extends GameTileInteractable {

    private boolean m_can_teleport;
    private Timer m_delay;

    public GameTileTeleporter( Vector2 location, Vector2 dimensions, Color color ) {
        super( location, dimensions, color, true );

        m_can_teleport = true;
        m_delay = new Timer(
                4500,
                e -> { m_can_teleport = true; }
        );
        m_delay.setRepeats( false );
    }

    private GameTile teleport( GameTilemap tilemap, GameTile dst, GameEntity entity ) {
        final Vector2 offset = entity.getDimensions( ).div( 2.f );
        final Vector2 tile_center = dst.getLocation( ).add( tilemap.getTileDimensions( ).div( 2.f ) );

        entity.setLocation( tile_center.sub( offset ) );

        return dst;
    }

    private void lock( ) {
        m_can_teleport = false;
        m_delay.start( );
    }

    @Override
    public void tick(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameEntityManager entity_manager,
            GameTilemap tilemap,
            float delta_time
    ) {
        if ( !m_can_teleport )
            return;

        if ( getEntity( ) instanceof GameEntityPlayer player ) {
            m_can_teleport = false;

            ArrayList<GameTileTeleporter> dest_list = tilemap.getAllTiles( GameTileTeleporter.class, this );
            GameTile dest = null;

            if ( dest_list != null ) {
                final int dest_id = state_manager.randInt( 0, dest_list.size( ) );

                dest = teleport( tilemap, dest_list.get( dest_id ), player );
            } else
                dest = teleport( tilemap, state_manager.randTile( this ), player );

            if ( dest instanceof GameTileInteractable interactable ) {
                if ( interactable instanceof GameTileTeleporter teleporter )
                    teleporter.lock( );

                interactable.onEnter( state_manager, tilemap, this, player, Vector2.Zero );
            } else
                player.setLocation( dest.getLocation( ) );
        }
    }

    @Override
    public void display( GameRenderManager render_manager ){
        super.display( render_manager );

        render_manager.drawSprite( "teleport", getLocation( ), getDimensions( ), 0, 0 );
    }

}
