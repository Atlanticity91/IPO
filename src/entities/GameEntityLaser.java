package entities;

import graphics.GameRenderManager;
import inputs.GameInputManager;
import terrain.GameTilemap;
import utils.GameDirection;
import utils.GameStateManager;
import utils.Vector2;

public class GameEntityLaser extends GameEntity {

    public GameEntityLaser( Vector2 location ) {
        super( location );
    }

    public void trace(GameTilemap tilemap, GameDirection direction ) {
    }

    @Override
    public void onCollide(GameStateManager state_manager, GameEntity entity, Vector2 offset) {
    }

    @Override
    public void tick(GameStateManager state_manager, GameInputManager input_manager, GameTilemap tilemap, GameEntityManager entity_manager) {
    }

    @Override
    public void display(GameRenderManager render_manager) {
    }

}
