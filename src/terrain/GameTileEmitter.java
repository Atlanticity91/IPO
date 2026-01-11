package terrain;

import entities.GameEntity;
import entities.GameEntityLaser;
import entities.GameEntityManager;
import graphics.GameRenderManager;
import inputs.GameInputManager;
import utils.GameDirection;
import utils.GameStateManager;
import utils.Vector2;

import java.awt.*;

public class GameTileEmitter extends GameTileInteractable {

    private GameDirection m_direction;

    public GameTileEmitter( Vector2 location, Vector2 dimensions, GameDirection direction ) {
        super(  location, dimensions, Color.orange, false );

        m_direction = direction;
    }

    protected void setDirection( GameDirection direction ) { m_direction = direction; }

    protected void spawnLaser(
            GameStateManager state_manager,
            GameEntityManager entity_manager,
            GameTilemap tilemap
    ) {
        entity_manager.kill( getEntity( ) );

        GameEntityLaser laser = new GameEntityLaser( getLocation( ) );

        entity_manager.addEntity( laser );
        laser.trace( state_manager, tilemap, getDirection( ) );

        setEntity( laser );
    }

    @Override
    public void tick(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameEntityManager entity_manager,
            GameTilemap tilemap,
            float delta_time
    ) {
        if ( getEntity( ) == null )
            spawnLaser( state_manager, entity_manager, tilemap );
    }

    @Override
    public void display( GameRenderManager render_manager ) {
        render_manager.drawSprite( "laser",getLocation( ), getDimensions( ), 0, 0 );
    }

    public GameDirection getDirection( ) { return m_direction; }

}
