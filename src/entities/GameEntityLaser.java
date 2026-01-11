package entities;

import graphics.GameRenderManager;
import inputs.GameInputManager;
import jdk.management.jfr.SettingDescriptorInfo;
import terrain.GameTile;
import terrain.GameTileMirror;
import terrain.GameTilemap;
import utils.GameDirection;
import utils.GameStateManager;
import utils.Vector2;

import java.awt.*;

public class GameEntityLaser extends GameEntity {

    private Color m_color;
    private GameDirection m_direction = GameDirection.None;

    public GameEntityLaser( Vector2 location ) {
        super( location, Vector2.Zero );

        m_color = new Color( 9, 219, 249 );
    }

    private Vector2 acquireRayHit( GameTilemap tilemap, GameDirection direction ) {
        final float tile_dimension = tilemap.getTileDimensions( ).getX( );
        Vector2 tile_position = getLocation( );
        GameTile tile = null;

        do {
            switch ( direction ) {
                case GameDirection.North : tile_position = tile_position.add(  0.f, -tile_dimension ); break;
                case GameDirection.South : tile_position = tile_position.add(  0.f,  tile_dimension ); break;
                case GameDirection.East  : tile_position = tile_position.add(  tile_dimension,  0.f ); break;
                case GameDirection.West  : tile_position = tile_position.add( -tile_dimension,  0.f ); break;
                default : return null;
            }

            tile = tilemap.getTile( tile_position );
        } while ( tile != null && tile.isTraversable( ) && !( tile instanceof GameTileMirror ) );

        return tile_position;
    }

    private void traceBeam(
            GameTilemap tilemap,
            GameDirection direction,
            Vector2 hit_location
    ) {
        final float half_dimensions = tilemap.getTileDimensions( ).getX( ) *.5f;
        final Vector2 old_location = getLocation( );
        final Vector2 beam = hit_location.sub( old_location ).sub( half_dimensions );
        final float beam_thickness = tilemap.getTileDimensions( ).getX( ) * .2f;

        Vector2 dimensions;
        if ( direction == GameDirection.North || direction == GameDirection.South )
            dimensions = new Vector2( beam_thickness, Math.abs( beam.getY( ) ) );
        else
            dimensions = new Vector2( Math.abs( beam.getX( ) ), beam_thickness );
        setDimensions( dimensions );

        Vector2 location;
        switch ( direction ) {
            case GameDirection.North : location = old_location.add( -dimensions.getX( ), -dimensions.getY( ) ).add( dimensions.getX( ) * .5f, .0f ); break;
            case GameDirection.South : location = old_location.add( -dimensions.getX( ), .0f ).add( dimensions.getX( ) * .5f, .0f ); break;
            case GameDirection.East  : location = old_location.add( .0f, -dimensions.getY( ) ).add( .0f, dimensions.getY( ) * .5f ); break;
            case GameDirection.West  : location = old_location.sub( dimensions ).add( .0f, dimensions.getY( ) * .5f ); break;
            default : return;
        }
        setLocation( location.add( half_dimensions ) );
    }

    private void doHitMirror(
            GameStateManager state_manager,
            GameTilemap tilemap,
            Vector2 hit_location
    ) {
        if ( tilemap.getTile( hit_location ) instanceof GameTileMirror mirror )
            mirror.onEnter( state_manager, tilemap, null, this, Vector2.Zero );
    }

    public void trace(
            GameStateManager state_manager,
            GameTilemap tilemap,
            GameDirection direction
    ) {
        m_direction = direction;

        final Vector2 hit_location = acquireRayHit( tilemap, direction );

        traceBeam( tilemap, direction, hit_location );
        doHitMirror( state_manager, tilemap, hit_location );
    }

    @Override
    public void onCollide(
            GameStateManager state_manager,
            GameEntityManager entity_manager,
            GameEntity entity,
            Vector2 offset
    ) {
        if ( entity instanceof GameEntityPlayer p ){
            state_manager.subLive( );

            p.setLocation( state_manager.getSpawnPoint( ) );
        }
    }

    @Override
    public void tick(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameTilemap tilemap,
            GameEntityManager entity_manager,
            float delta_time
    ) {
    }

    public GameDirection getDirection( ) { return m_direction; }

    @Override
    public void display( GameRenderManager render_manager ) {
        render_manager.drawRect( getLocation( ), getDimensions( ), m_color );
    }

}
