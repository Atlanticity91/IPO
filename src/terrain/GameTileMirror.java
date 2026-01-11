package terrain;

import entities.GameEntity;
import entities.GameEntityLaser;
import entities.GameEntityManager;
import graphics.GameRenderManager;
import inputs.GameInputManager;
import inputs.GameInputState;
import utils.GameDirection;
import utils.GameStateManager;
import utils.Hitbox;
import utils.Vector2;

import java.awt.event.KeyEvent;

public class GameTileMirror extends GameTileEmitter {

    private boolean m_has_source = false;

    public GameTileMirror(
            Vector2 location,
            Vector2 dimensions,
            GameDirection direction
    ) {
        super( location, dimensions, direction );
    }

    @Override
    public void onEnter(
            GameStateManager state_manager,
            GameTilemap tilemap,
            GameTileInteractable previous,
            GameEntity entity,
            Vector2 offset
    ) {
        if ( entity instanceof GameEntityLaser laser ) {
            setDirection( laser.getDirection( ) );
            m_has_source = true;
        }
    }

    @Override
    public void tick(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameEntityManager entity_manager,
            GameTilemap tilemap,
            float delta_time
    ) {
        if ( !m_has_source )
            return;

        final GameDirection old_direction = getDirection( );
        final Hitbox hitbox = getLocalHitbox( ).move( tilemap.getOrigin( ) );

        if ( old_direction != GameDirection.None && getEntity( ) == null ) {
            spawnLaser( state_manager, entity_manager, tilemap );

            return;
        }

        if ( !input_manager.getMouseLocation( ).isIn( hitbox ) )
            return;

        if ( input_manager.isKey( KeyEvent.VK_UP, GameInputState.Pressed ) )
            setDirection( GameDirection.North );
        else if ( input_manager.isKey( KeyEvent.VK_DOWN, GameInputState.Pressed ) )
            setDirection( GameDirection.South );
        else if ( input_manager.isKey( KeyEvent.VK_LEFT, GameInputState.Pressed ) )
            setDirection( GameDirection.West );
        else if ( input_manager.isKey( KeyEvent.VK_RIGHT, GameInputState.Pressed ) )
            setDirection( GameDirection.East );
        else if ( input_manager.isKey( KeyEvent.VK_SPACE, GameInputState.Pressed ) ) {
            switch ( getDirection( ) ) {
                case GameDirection.North : setDirection( GameDirection.East ); break;
                case GameDirection.South : setDirection( GameDirection.West ); break;
                case GameDirection.West  : setDirection( GameDirection.North ); break;
                case GameDirection.East  : setDirection( GameDirection.South ); break;
            }
        }

        if ( old_direction != getDirection()  )
            spawnLaser( state_manager, entity_manager, tilemap );
    }

    @Override
    public void display( GameRenderManager render_manager ) {
        String sprite = "";

        switch ( getDirection( ) ){
            case GameDirection.North : sprite = "laser_north"; break;
            case GameDirection.South : sprite = "laser_south"; break;
            case GameDirection.West  : sprite = "laser_west"; break;
            case GameDirection.East  : sprite = "laser_east"; break;
        }

        if ( !sprite.isEmpty( ) )
            render_manager.drawSprite( sprite, getLocation( ), getDimensions( ), 0, 0 );
    }

}
