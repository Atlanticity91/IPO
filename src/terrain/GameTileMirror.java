package terrain;

import entities.GameEntityLaser;
import entities.GameEntityManager;
import inputs.GameInputManager;
import inputs.GameInputState;
import utils.GameDirection;
import utils.GameStateManager;
import utils.Hitbox;
import utils.Vector2;

import java.awt.event.KeyEvent;

public class GameTileMirror extends GameTileEmitter {

    public GameTileMirror(
            Vector2 location,
            Vector2 dimensions,
            GameDirection direction
    ) {
        super( location, dimensions, direction );
    }

    private void spawnLaser(
            GameStateManager state_manager,
            GameEntityManager entity_manager,
            GameTilemap tilemap
    ) {
        entity_manager.kill( getEntity( ) );

        GameEntityLaser laser = new GameEntityLaser( getLocation( ) );

        entity_manager.addEntity( laser );
        laser.trace( state_manager, tilemap, getDirection( ) );

        entity_manager.addEntity( laser );
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

        final Hitbox hitbox = getLocalHitbox( ).move( tilemap.getOrigin( ) );

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

        spawnLaser( state_manager, entity_manager, tilemap );
    }

}
