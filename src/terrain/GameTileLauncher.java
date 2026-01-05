package terrain;

import entities.GameEntityBullet;
import entities.GameEntityManager;
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

    @Override
    public void tick(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameEntityManager entity_manager,
            GameTilemap tilemap
    ) {
        if ( !m_spawn_bullet || m_direction == GameDirection.None )
            return;

        GameEntityBullet bullet = new GameEntityBullet( getLocation( ), tilemap.getTileDimensions( ).getX( ), m_direction );

        entity_manager.addEntity( bullet );

        m_spawn_bullet = false;
    }

    @Override
    public boolean isTraversable( ) { return false; }

    public GameDirection getDirection( ) { return m_direction; }

}
