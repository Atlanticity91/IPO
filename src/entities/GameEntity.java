package entities;

import graphics.GameRenderManager;
import inputs.GameInputManager;
import terrain.GameTilemap;
import utils.GameStateManager;
import utils.Vector2;

public abstract class GameEntity {

    private Vector2 m_location;
    private Vector2 m_dimenions;
    private boolean m_is_alive;

    public GameEntity( Vector2 location, Vector2 dimenions ) {
        m_location  = location.sub( dimenions.div( 2.f ) );
        m_dimenions = dimenions;
        m_is_alive  = true;
    }

    public void onSpawn( ) { }

    public void onKill( ) { m_is_alive = false; }

    public void setLocation( Vector2 location ) { m_location = location; }

    public void move( float offset ) { m_location = m_location.add( offset ); }

    public void move( float x, float y ) { m_location = m_location.add( x, y ); }

    public void move( Vector2 offset ) { m_location = m_location.add( offset ); }

    protected void setDimensions( Vector2 dimensions ) { m_dimenions = dimensions; }

    public abstract void onCollide(
            GameStateManager state_manager,
            GameEntityManager entity_manager,
            GameEntity entity,
            Vector2 offset
    );

    public abstract void tick(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameTilemap tilemap,
            GameEntityManager entity_manager
    );

    public void display( GameRenderManager render_manager ) {
        //render_manager.drawRect( getLocation(), getDimensions() );
        render_manager.drawCircle( getLocation( ), getDimensions( ).getX( ) );
    }

    public Vector2 getLocation( ) { return m_location; }

    public Vector2 getDimensions( ) { return m_dimenions; }

    public boolean getIsAlive( ) { return m_is_alive; }

    public Vector2 doCollide( GameEntity entity ) { return null; }

}
