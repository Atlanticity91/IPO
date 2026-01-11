package terrain;

import entities.GameEntityBullet;
import entities.GameEntityManager;
import graphics.GameRenderManager;
import inputs.GameInputManager;
import utils.GameDirection;
import utils.GameStateManager;
import utils.Vector2;

import javax.swing.*;
import java.awt.*;

public class GameTileLauncher extends GameTile implements GameTileTickable {

    private GameDirection m_direction;
    private boolean m_spawn_bullet = false;

    public GameTileLauncher( Vector2 location, Vector2 dimensions, GameDirection direction ) {
        super( false, location, dimensions, Color.red );

        m_direction = direction;

        Timer timer = new Timer(
                1500,
                e -> { m_spawn_bullet = true; }
        );

        timer.start( );
    }

    public void setDirection( GameDirection direction ) { m_direction = direction; }

    private Vector2 acquireSpawnLocation( float tile_dimension ) {
        final float half_tile = tile_dimension * .5f;
        final Vector2 center = getLocation( ).add( half_tile );

        return center.add( Vector2.FromDirection( m_direction, half_tile ) );
    }

    @Override
    public void tick(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameEntityManager entity_manager,
            GameTilemap tilemap,
            float delta_time
    ) {
        if ( !m_spawn_bullet || m_direction == GameDirection.None )
            return;

        final float tile_dimension = tilemap.getTileDimensions( ).getX( );
        final Vector2 location = acquireSpawnLocation( tile_dimension );

        GameEntityBullet bullet = new GameEntityBullet( location, tile_dimension, m_direction );

        entity_manager.addEntity( bullet );

        m_spawn_bullet = false;
    }

    @Override
    public void display( GameRenderManager render_manager ){
        super.display( render_manager );

        render_manager.drawSprite( "launcher", getLocation( ), getDimensions( ), 0, 0 );
    }

    @Override
    public boolean isTraversable( ) { return false; }

    public GameDirection getDirection( ) { return m_direction; }

}
