package entities;

import inputs.GameInputManager;
import terrain.GameTile;
import terrain.GameTilemap;
import utils.GameDirection;
import utils.GameStateManager;
import utils.Vector2;

public class GameEntityBullet extends GameEntity {

    private GameDirection m_direction;
    private Vector2 m_acceleration;
    private float m_max_acceleration = 10.f;
    private float m_acceleration_factor = 50.f;

    public GameEntityBullet( Vector2 location, float tile_dimension, GameDirection direction ) {
        super( location, new Vector2( tile_dimension * .2f ) );

        m_direction = direction;
        m_acceleration = Vector2.Zero;
    }

    public void setMaxAcceleration( float value ) { m_max_acceleration = value; }

    public void setAccelerationFactor( float value ) { m_acceleration_factor = value; }

    @Override
    public void onCollide(
            GameStateManager state_manager,
            GameEntityManager entity_manager,
            GameEntity entity,
            Vector2 offset
    ) {
        if ( entity instanceof GameEntityPlayer p ) {
            state_manager.subLive( );

            p.setLocation( state_manager.getSpawnPoint( ) );
        }

        entity_manager.kill( this );
    }

    private Vector2 acquireAcceleration( float delta_time ) {
        Vector2 acceleration = new Vector2( m_acceleration );

        switch ( m_direction ) {
            case North     : acceleration = acceleration.add( 0.f, m_acceleration_factor ); break;
            case East      : acceleration = acceleration.add( m_acceleration_factor, 0.f ); break;
            case South     : acceleration = acceleration.add( 0.f, -m_acceleration_factor ); break;
            case West      : acceleration = acceleration.add( -m_acceleration_factor, 0.f ); break;
            case Northeast : acceleration = acceleration.add(  m_acceleration_factor,  m_acceleration_factor ); break;
            case Southeast : acceleration = acceleration.add(  m_acceleration_factor, -m_acceleration_factor ); break;
            case Southwest : acceleration = acceleration.add( -m_acceleration_factor, -m_acceleration_factor ); break;
            case Northwest : acceleration = acceleration.add( -m_acceleration_factor,  m_acceleration_factor ); break;
            default : break;
        }

        acceleration = acceleration.scale( delta_time );

        if ( acceleration.getX( ) > m_max_acceleration )
            acceleration.setX( m_max_acceleration );

        if ( acceleration.getY( ) > m_max_acceleration )
            acceleration.setY( m_max_acceleration );

        return acceleration;
    }

    private void tickMovement( float delta_time ) {
        final Vector2 acceleration = acquireAcceleration( delta_time );

        move( acceleration );
    }

    private void tickCollision( GameTilemap tilemap, GameEntityManager entity_manager ) {
        final Vector2 location = getLocation( );
        final GameTile tile = tilemap.getTile( location );

        if ( tile != null && !tile.isTraversable( ) )
            entity_manager.kill( this );
    }

    @Override
    public void tick(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameTilemap tilemap,
            GameEntityManager entity_manager,
            float delta_time
    ) {
        tickMovement( delta_time );
        tickCollision( tilemap, entity_manager );
    }

    public GameDirection getDirection( ) { return m_direction; }

}
