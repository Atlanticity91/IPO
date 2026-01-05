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
        final Vector2 center = getCircleCenter( getLocation(), getDimensions( ) );
        final float dist_x = mouse.getX( ) - center.getX( );
        final float dist_y = mouse.getY( ) - center.getY( );
        final float dist = dist_x * dist_x + dist_y * dist_y;
        final float radius = getDimensions( ).getX( ) / 2.f;

        return dist <= ( radius * radius );
    }

    private void checkCorner(GameTile tile, float cornerX, float cornerY) {
        if (tile.isTraversable()) return;

        float dx = getLocation().getX() - cornerX;
        float dy = getLocation().getY() - cornerY;

        float dist = (float)Math.sqrt(dx * dx + dy * dy);

        if (dist < getRadius( ) && dist > 0.0001f) {

            float nx = dx / dist;
            float ny = dy / dist;

            float vCoin = m_velocity.getX() * nx + m_velocity.getY() * ny;

            if (vCoin < 0) {
                m_velocity = new Vector2(
                        m_velocity.getX() - 2 * vCoin * nx,
                        m_velocity.getY() - 2 * vCoin * ny
                );
            }
        }
    }

    private void checkTileCollision( GameTilemap tilemap ) {
        final Vector2 location = getLocation( );

        GameTile north = tilemap.getTileAt( location, GameDirection.North);
        if (!north.isTraversable()) {
            float top = north.getLocalHitbox().getMax().getY() + getRadius();
            if (top >= location.getY()) {
                setLocation( new Vector2(location.getX(), top) );
                m_velocity = m_velocity.negateY();
            }
        }

        GameTile east = tilemap.getTileAt( location, GameDirection.East);
        if (!east.isTraversable()) {
            float right = east.getLocalHitbox().getMin().getX() - getRadius();
            if (right <= location.getX()) {
                setLocation( new Vector2(right, location.getY()) );
                m_velocity = m_velocity.negateX();
            }
        }

        GameTile south = tilemap.getTileAt( location, GameDirection.South);
        if (!south.isTraversable()) {
            float bottom = south.getLocalHitbox().getMin().getY() - getRadius();
            if (bottom <= location.getY()) {
                setLocation( new Vector2( location.getX(), bottom) );
                m_velocity = m_velocity.negateY();
            }
        }

        GameTile west = tilemap.getTileAt( location, GameDirection.West);
        if (!west.isTraversable()) {
            float left = west.getLocalHitbox().getMax().getX() + getRadius();
            if (left <= location.getX()) {
                setLocation( new Vector2(left, location.getY()) );
                m_velocity = m_velocity.negateX();
            }
        }

        GameTile ne = tilemap.getTileAt( location, GameDirection.Northeast);
        checkCorner(ne,
                ne.getLocalHitbox().getMin().getX(),
                ne.getLocalHitbox().getMax().getY());

        GameTile se = tilemap.getTileAt( location, GameDirection.Southeast);
        checkCorner(se,
                se.getLocalHitbox().getMin().getX(),
                se.getLocalHitbox().getMin().getY());

        GameTile sw = tilemap.getTileAt( location, GameDirection.Southwest);
        checkCorner(sw,
                sw.getLocalHitbox().getMax().getX(),
                sw.getLocalHitbox().getMin().getY());

        GameTile nw = tilemap.getTileAt( location, GameDirection.Northwest);
        checkCorner(nw,
                nw.getLocalHitbox().getMax().getX(),
                nw.getLocalHitbox().getMax().getY());
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

        //checkTileCollision( tilemap );

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

    private Vector2 getCircleCenter( Vector2 location, Vector2 dimenions ) {
        final float half_diameter = dimenions.getX( ) * .5f;

        return new Vector2(
                location.getX( ) + half_diameter,
                location.getY( ) + half_diameter
        );
    }

    @Override
    public Vector2 doCollide( GameEntity entity ) {
        final Vector2 player_center = getCircleCenter( getLocation(), getDimensions());

        if ( entity instanceof GameEntityBullet bullet ) {
            final Vector2 bullet_center = getCircleCenter( bullet.getLocation(), bullet.getDimensions() );
            final float dist = bullet_center.sub( player_center ).magnitudeSq( );
            final float radius = getDimensions().getX() + bullet.getDimensions().getX();
            final float penetration = ( radius * radius ) - dist;

            if ( penetration > 0.f ) {
                if ( dist > 0.0001f )
                    return bullet_center.sub( player_center ).div( dist ).scale( penetration );
                else
                    return new Vector2( 0.f, penetration );
            }
        } else if ( entity instanceof GameEntityLaser laser ) {
        }

        return null;
    }

    public float getDiameter( ) { return getDimensions( ).getX( ); }

    public float getRadius( ) { return getDiameter( ) / 2.f; }

}
