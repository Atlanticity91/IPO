package terrain;

import entities.GameEntityManager;
import entities.GameEntityPlayer;
import graphics.GameRenderManager;
import inputs.GameInputManager;
import utils.GameStateManager;
import utils.Vector2;

import java.awt.*;

public class GameTileOutput extends GameTileInteractable {

    public GameTileOutput( Vector2 location, Vector2 dimensions, Color color ) {
        super( location, dimensions, color, true );
    }

    @Override
    public void tick(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameEntityManager entity_manager,
            GameTilemap tilemap
    ) {
        if ( getEntity( ) instanceof GameEntityPlayer)
            state_manager.win( );
    }

    @Override
    public void display( GameRenderManager render_manager ) {
        super.display( render_manager );

        render_manager.drawSprite( "out", getLocation( ), getDimensions( ), 0, 0 );
    }

}
