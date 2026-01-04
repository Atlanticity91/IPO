package entities;

import graphics.GameRenderManager;
import inputs.GameInputManager;
import terrain.GameTilemap;
import utils.GameDirection;
import utils.GameStateManager;
import utils.Vector2;

public class GameEntityBullet extends GameEntity {

    private GameDirection m_direction;
    private float m_velocity;

    public GameEntityBullet( Vector2 location, GameDirection direction ) {
        super( location );

        m_direction = direction;
        m_velocity = .5f;
    }

    @Override
    public void onCollide(
            GameStateManager state_manager,
            GameEntity entity,
            Vector2 offset
    ) {
    }

    @Override
    public void tick(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameTilemap tilemap,
            GameEntityManager entity_manager
    ) {
        move( m_velocity, m_velocity );
    }

    @Override
    public void display(GameRenderManager render_manager) {

    }

    public GameDirection getDirection( ) { return m_direction; }

}
