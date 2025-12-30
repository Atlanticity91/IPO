package terrain;

import entities.GameEntityManager;
import graphics.GameRenderManager;
import inputs.GameInputManager;
import utils.GameStateManager;
import utils.Vector2;

import java.awt.*;

public class GameTileTeleporter extends GameTileInteractable {

    public GameTileTeleporter( Vector2 location, Vector2 dimensions, Color color ) {
        super( location, dimensions, color );
    }

    @Override
    public void onEnter( Object entity ) {
        super.onEnter( entity );
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
