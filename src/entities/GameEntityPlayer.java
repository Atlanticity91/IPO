package entities;

import inputs.GameInputManager;
import terrain.GameTile;
import terrain.GameTileInteractable;
import terrain.GameTilemap;
import utils.GameDirection;
import utils.GameStateManager;
import utils.Globals;
import utils.Vector2;

import java.awt.*;

public class GameEntityPlayer extends GameEntity {

    private GameDirection m_direction = GameDirection.None;

    private Vector2 m_velocity;
    private float m_slippery_factor = 0.f;

    public GameEntityPlayer( Vector2 location, float tile_dimensions ) {
        super( location, new Vector2( tile_dimensions * .5f ) );

        m_velocity = Vector2.Zero;
    }

    public void setLockDirection( GameDirection direction ) { m_direction = direction; }

    public void setSlipperyFactor( float scalar ) { m_slippery_factor = scalar; }

    @Override
    public void onCollide(
            GameStateManager state_manager,
            GameEntityManager entity_manager,
            GameEntity entity,
            Vector2 offset
    ) {
        if ( entity instanceof GameEntityPlayer player )
            state_manager.subLive( );
    }

    private boolean isMouseIn( Vector2 mouse ) {
        final float dist_x = mouse.getX( ) - getLocation( ).getX( );
        final float dist_y = mouse.getY( ) - getLocation( ).getY( );
        final float dist = dist_x * dist_x + dist_y * dist_y;
        final float radius = getDimensions( ).getX( ) / 2.f;

        return dist <= ( radius * radius );
    }

    private void checkTileCollision( GameTilemap tilemap ) {
    }

    @Override
    public void tick(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameTilemap tilemap,
            GameEntityManager entity_manager
    ) {
        final Vector2 old_location = getLocation( );
        final GameTile current_tile = tilemap.getTile( old_location );

        checkTileCollision( tilemap );

        Vector2 dir = m_velocity.normalize( );

        if (m_velocity.magnitude() <= Globals.BRAKE_FACTOR)
            m_velocity = new Vector2();
        else
            m_velocity = m_velocity.sub(dir.scale(Globals.BRAKE_FACTOR));

        if ( isMouseIn( input_manager.getMouseLocation( ).sub( tilemap.getOrigin( ) ) ) )
            m_velocity = m_velocity.add( input_manager.getMouseDirection( ).scale( Globals.ACCELERATION_FACTOR ) );

        if (m_velocity.magnitude() >= Globals.MAX_SPEED)
            m_velocity = m_velocity.normalize().scale(Globals.MAX_SPEED);

        move( m_velocity );

        final GameTile new_tile = tilemap.getTile( getLocation( ) );

        if (new_tile instanceof GameTileInteractable interactable ) {
            final Vector2 offset = getDimensions( ).sub( old_location );

            interactable.onEnter(state_manager, tilemap, (GameTileInteractable) current_tile, this, offset );
        }
    }

    @Override
    public Vector2 doCollide( GameEntity entity ) {
        return null;
    }

    public float getDiameter( ) { return getDimensions( ).getX( ); }

}
