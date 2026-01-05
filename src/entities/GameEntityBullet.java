package entities;

import inputs.GameInputManager;
import terrain.GameTilemap;
import utils.GameDirection;
import utils.GameStateManager;
import utils.Vector2;

public class GameEntityBullet extends GameEntity {

    private GameDirection m_direction;
    private Vector2 m_acceleration;
    private float m_max_acceleration = 10.f;
    private float m_acceleration_factor = 0.5f;

    public GameEntityBullet( Vector2 location, float tile_dimension, GameDirection direction ) {
        super( location, new Vector2( tile_dimension * .2f ) );

        m_direction = direction;
        m_acceleration = Vector2.Zero;
        System.out.println( "new GameEntityBullet" );
    }

    @Override
    public void onCollide(
            GameStateManager state_manager,
            GameEntityManager entity_manager,
            GameEntity entity,
            Vector2 offset
    ) {
        if ( entity instanceof GameEntityPlayer p ) {
            state_manager.subLive( );

            p.setLocation( offset.negate( ) );
        }

        entity_manager.kill( this );
    }

    @Override
    public void tick(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameTilemap tilemap,
            GameEntityManager entity_manager
    ) {
        Vector2 acceleration = new Vector2( m_acceleration );

        switch ( m_direction ) {
            case North     : acceleration.add( 0.f, m_acceleration_factor ); break;
            case East      : acceleration.add( m_acceleration_factor, 0.f ); break;
            case South     : acceleration.add( 0.f, -m_acceleration_factor ); break;
            case West      : acceleration.add( -m_acceleration_factor, 0.f ); break;
            case Northeast : acceleration.add( m_acceleration_factor, m_acceleration_factor ); break;
            case Southeast : acceleration.add( m_acceleration_factor, -m_acceleration_factor ); break;
            case Southwest : acceleration.add( -m_acceleration_factor, -m_acceleration_factor ); break;
            case Northwest : acceleration.add( -m_acceleration_factor, m_acceleration_factor ); break;
            default : break;
        }

        m_acceleration = acceleration.clamp( 0.f, m_max_acceleration );

        move( m_acceleration );
    }

    public GameDirection getDirection( ) { return m_direction; }

}
