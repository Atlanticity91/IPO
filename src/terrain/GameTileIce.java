package terrain;

import entities.GameEntityManager;
import inputs.GameInputManager;
import utils.GameStateManager;
import utils.Vector2;

import java.awt.*;

public class GameTileIce extends GameTileInteractable {

    public GameTileIce( Vector2 location, Vector2 dimensions, Color color ) {
        super( location, dimensions, color );
    }

    @Override
    public void tick(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameEntityManager entity_manager,
            GameTilemap tilemap
    ) {
    }

}
