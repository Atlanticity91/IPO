package entities;

import graphics.GameRenderManager;
import inputs.GameInputManager;
import terrain.GameTilemap;
import utils.GameDirection;
import utils.GameStateManager;
import utils.Vector2;

import java.awt.*;

public class GameEntityPlayer extends GameEntity {

    private float m_diameter;
    private GameDirection m_direction = GameDirection.None;

    public GameEntityPlayer( Vector2 location, float tile_dimensions ) {
        super( location.sub( tile_dimensions * .5f ));

        m_diameter = tile_dimensions * .5f;
    }

    public void setLockDirection( GameDirection direction ) { m_direction = direction; }

    @Override
    public void onCollide(
            GameStateManager state_manager,
            GameEntity entity,
            Vector2 offset
    ) {
        if ( entity instanceof GameEntityPlayer player )
            state_manager.subLive( );
    }

    @Override
    public void tick(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameTilemap tilemap,
            GameEntityManager entity_manager
    ) {
    }

    @Override
    public void display( GameRenderManager render_manager ) {
        render_manager.drawCircle( getLocation( ), m_diameter, Color.RED );
    }

    public float getDiameter( ) { return m_diameter; }

}
