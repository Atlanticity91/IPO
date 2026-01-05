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

    @Override
    public void onEnter(
            GameStateManager state_manager,
            GameTilemap tilemap,
            GameTileInteractable previous,
            GameEntity entity,
            Vector2 offset
    ) {
        super.onEnter( state_manager, tilemap, previous, entity, offset );

        if ( entity instanceof GameEntityLaser laser )
            laser.trace( state_manager, tilemap, m_direction );
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
    public void display(GameRenderManager render_manager) {
        super.display( render_manager );

        if ( m_direction == GameDirection.West )
            render_manager.drawSprite( "laser_droite", getLocation(), getDimensions( ), 0, 0 );
        else if ( m_direction == GameDirection.East )
            render_manager.drawSprite( "laser_gauche", getLocation(), getDimensions( ), 0, 0 );
    }

    public GameDirection getDirection( ) { return m_direction; }

}
