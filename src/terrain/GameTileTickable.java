package terrain;

import entities.GameEntityManager;
import inputs.GameInputManager;
import utils.GameStateManager;

public interface GameTileTickable {

    void tick(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameEntityManager entity_manager,
            GameTilemap tilemap,
            float delta_time
    );

}
