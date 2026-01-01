package terrain;

import entities.GameEntityManager;
import graphics.GameRenderManager;
import inputs.GameInputManager;
import utils.GameDirection;
import utils.GameStateManager;
import utils.Globals;
import utils.Vector2;

import java.awt.*;
import java.util.ArrayList;

public class GameTilemap {

    private Vector2 m_origin;
    private GameTile[] m_tiles;
    private int m_columns = 0;
    private int m_rows = 0;
    private float m_tile_dimensions = 0.f;

    public GameTilemap( ) {
        m_origin = new Vector2( );
        m_tiles = null;
    }

    public void create( int columns, int rows, float tile_dimensions ) {
        m_origin = new Vector2(
                ( Globals.WIN_WIDTH - columns * tile_dimensions ) / 2,
                ( Globals.WIN_HEIGHT - Globals.PLAY_UI_HEIGHT - rows * tile_dimensions ) / 2
        );
        m_tiles = new GameTile[ columns * rows ];
        m_columns = columns;
        m_rows = rows;
        m_tile_dimensions = tile_dimensions;
    }

    public GameTile spawnTile( char tile_type, int column, int row ) {
        final Vector2 location = new Vector2( column * m_tile_dimensions, row * m_tile_dimensions );
        final Vector2 dimensions = new Vector2( m_tile_dimensions );
        GameTile tile = null;

        switch ( tile_type ) {
            case '#' : tile = new GameTileDecoration( location, dimensions, false ); break;
            case ' ' : tile = new GameTileDecoration( location, dimensions, true ); break;
            case 'O' : tile = new GameTileOutput( location, dimensions, Color.cyan ); break;

            default : break;
        }

        if ( tile != null )
            m_tiles[ row * m_columns + column ] = tile;

        return tile;
    }

    public void clear( ) { m_tiles = null; }

    public void tick(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameEntityManager entity_manager
    ) {
        if ( m_tiles == null )
            return;

        for ( GameTile tile : m_tiles ) {
            if ( tile instanceof GameTileTickable tickable )
                tickable.tick( state_manager, input_manager, entity_manager, this );
        }
    }

    public void display( GameRenderManager render_manager ) {
        if ( m_tiles == null )
            return;

        render_manager.setOrigin( m_origin );

        for ( GameTile tile : m_tiles ) {
            if ( tile != null )
                tile.display( render_manager );
        }

        render_manager.setOrigin( 0.f, 0.f );
    }

    public Vector2 getOrigin( ) { return m_origin; }

    public int getColumns( ) { return m_columns; }

    public int getRows( ) { return m_rows; }

    public Vector2 getTileDimensions( ) { return new Vector2( m_tile_dimensions ); }

    private Vector2 getLocationAt( Vector2 location, GameDirection direction ) {
        return switch ( direction ) {
            case None -> location;
            case North -> location.add( 0.f, 1.f );
            case East -> location.add( 1.f, 0.f );
            case South -> location.add( 0.f, -1.f );
            case West -> location.add( -1.f, 0.f );
            case Northeast -> location.add( 1.f );
            case Southeast -> location.add( 1.f, -1.f );
            case Southwest -> location.add( -1.f, -1.f );
            case Northwest -> location.add( -1.f, 1.f );
        };
    }

    public GameTile getTile( Vector2 location ) {
        return getTileAt( location, GameDirection.None );
    }

    public GameTile getTile( int x, int y ) { return m_tiles[ y * m_columns + x ]; }

    public GameTile getTileAt( Vector2 location, GameDirection direction ) {
        final Vector2 adjust_pos = location.sub( m_origin ).div( m_tile_dimensions );
        final Vector2 tile_pos = getLocationAt( adjust_pos, direction );
        final int x = (int)tile_pos.getX( );
        final int y = (int)tile_pos.getY( );

        if ( x < 0 || x >= m_columns || y < 0 || y > m_rows)
            return null;

        return getTile( x, y );
    }

    public <T extends GameTile> ArrayList<T> getAllTiles( Class<T> type, GameTile current_tile ) {
        if ( m_tiles == null )
            return null;

        ArrayList<T> tile_list = new ArrayList<>( );

        for ( GameTile tile : m_tiles ) {
            if ( type.isInstance( tile ) && tile != current_tile )
                tile_list.add( type.cast( tile ) );
        }

        return tile_list;
    }

}
