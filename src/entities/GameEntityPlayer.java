package entities;

import inputs.GameInputManager;
import terrain.GameTile;
import terrain.GameTileInteractable;
import terrain.GameTilemap;
import utils.*;

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
        if ( north != null && !north.isTraversable()) {
            float top = north.getLocalHitbox().getMax().getY() + getRadius();
            if (top >= location.getY()) {
                setLocation( new Vector2(location.getX(), top) );
                m_velocity = new Vector2( m_velocity.getX(), -Math.abs(m_velocity.getY()) );
            }
        }

        GameTile east = tilemap.getTileAt( location, GameDirection.East);
        if ( east != null && !east.isTraversable()) {
            float right = east.getLocalHitbox().getMin().getX() - getRadius();
            if (right <= location.getX()) {
                setLocation( new Vector2(right, location.getY()) );
                m_velocity = new Vector2( -Math.abs(m_velocity.getX()), m_velocity.getY() );
            }
        }

        GameTile south = tilemap.getTileAt( location, GameDirection.South);
        if ( south != null && !south.isTraversable()) {
            float bottom = south.getLocalHitbox().getMin().getY() - getRadius();
            if (bottom <= location.getY()) {
                setLocation( new Vector2( location.getX(), bottom) );
                m_velocity = new Vector2( m_velocity.getX(), Math.abs(m_velocity.getY()) );
            }
        }

        GameTile west = tilemap.getTileAt( location, GameDirection.West);
        if ( west != null && !west.isTraversable()) {
            float left = west.getLocalHitbox().getMax().getX() + getRadius();
            if (left <= location.getX()) {
                setLocation( new Vector2(left, location.getY()) );
                m_velocity = new Vector2( Math.abs(m_velocity.getX()), m_velocity.getY() );
            }
        }

//        GameTile ne = tilemap.getTileAt( location, GameDirection.Northeast);
//        if ( ne != null ) {
//            checkCorner(ne,
//                    ne.getLocalHitbox().getMin().getX(),
//                    ne.getLocalHitbox().getMax().getY());
//        }
//
//        GameTile se = tilemap.getTileAt( location, GameDirection.Southeast);
//        if ( se != null ) {
//            checkCorner(se,
//                    se.getLocalHitbox().getMin().getX(),
//                    se.getLocalHitbox().getMin().getY());
//        }
//
//        GameTile sw = tilemap.getTileAt( location, GameDirection.Southwest);
//        if ( sw != null ) {
//            checkCorner(sw,
//                    sw.getLocalHitbox().getMax().getX(),
//                    sw.getLocalHitbox().getMin().getY());
//        }
//
//        GameTile nw = tilemap.getTileAt( location, GameDirection.Northwest);
//        if ( nw != null ) {
//            checkCorner(nw,
//                    nw.getLocalHitbox().getMax().getX(),
//                    nw.getLocalHitbox().getMax().getY());
//        }
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

        if ( m_velocity.magnitude( ) >= Globals.MAX_SPEED )
            m_velocity = m_velocity.normalize( ).scale( Globals.MAX_SPEED );

        move( m_velocity.add( m_velocity.scale( m_slippery_factor ) ) );

        final GameTile new_tile = tilemap.getTile( getLocation( ) );

        if (new_tile instanceof GameTileInteractable interactable ) {
            final Vector2 offset = getDimensions( ).sub( old_location );

            interactable.onEnter(state_manager, tilemap, (GameTileInteractable) current_tile, this, offset );
        }
    }

    private Vector2 getCircleCenter( Vector2 location, Vector2 dimensions ) {
        final float half_diameter = dimensions.getX( ) * .5f;

        return new Vector2(
                location.getX( ) + half_diameter,
                location.getY( ) + half_diameter
        );
    }

    @Override
    public Vector2 doCollide( GameEntity entity ) {
        final Vector2 player_center = getCircleCenter( getLocation(), getDimensions());
        final float radius = getRadius( );

        if ( entity instanceof GameEntityBullet bullet ) {
            final Vector2 bullet_center = getCircleCenter( bullet.getLocation(), bullet.getDimensions() );
            final float dist = bullet_center.sub( player_center ).magnitudeSq( );
            final float penetration = ( radius * radius ) - dist;

            if ( penetration > 0.f ) {
                if ( dist > .0001f )
                    return bullet_center.sub( player_center ).div( dist ).scale( penetration );
                else
                    return new Vector2( 0.f, penetration );
            }
        } else if ( entity instanceof GameEntityLaser laser ) {
            final Vector2 min = laser.getLocation();
            final Vector2 max = min.add( laser.getDimensions( ) );
            final Vector2 closet = new Vector2(
                    Math.max( min.getX( ), Math.min( player_center.getX( ), max.getX( ) ) ),
                    Math.max( min.getY( ), Math.min( player_center.getY( ), max.getY( ) ) )
            );
            final Vector2 dist_vector = player_center.sub( closet );
            final float dist = dist_vector.magnitudeSq( );
            final float penetration = radius - dist;

            if ( penetration > 0.f ) {
                if ( dist > .0001f )
                    return dist_vector.div( dist ).scale( penetration );
                else {
                    final float dist_left   = player_center.getX() - min.getX( );
                    final float dist_right  = max.getX( ) - player_center.getX( );
                    final float dist_bottom = player_center.getY( ) - min.getY( );
                    final float dist_top    = max.getY( ) - player_center.getY( );
                    final float push_x = (dist_left < dist_right) ? dist_left : -dist_right;
                    final float push_y = (dist_bottom < dist_top) ? dist_bottom : -dist_top;

                    if ( Math.abs( push_x ) < Math.abs( push_y ) )
                        return new Vector2( push_x, 0.f);
                    else
                        return new Vector2( 0.f, push_y );
                }
            }
        }

        return null;
    }

    public float getDiameter( ) { return getDimensions( ).getX( ); }

    public float getRadius( ) { return getDiameter( ) / 2.f; }

}
