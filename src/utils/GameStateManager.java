package utils;

import entities.GameEntityManager;
import terrain.GameTile;
import terrain.GameTileInteractable;
import terrain.GameTilemap;

import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;

public class GameStateManager {

    private GameState m_state = GameState.MenuScreen;
    private Random m_random;
    private GameTilemap m_tilemap;
    private GameEntityManager m_entity_manager;
    private String m_level_name = "Reduxma";
    private String m_level_time = "";
    private int m_live_count = Globals.MAX_LIVE_COUNT;
    private boolean m_has_win = false;

    public GameStateManager( GameTilemap tilemap, GameEntityManager entity_manager ) {
        m_random = new Random( );
        m_tilemap = tilemap;
        m_entity_manager = entity_manager;
    }

    public int randInt( int min, int max ) { return m_random.nextInt( min, max ); }

    public GameTile randTile( GameTile current_tile ) {
        final int columns = m_tilemap.getColumns( ) - 2;
        final int rows = m_tilemap.getRows( ) - 2;

        int x = randInt( 1, columns );
        int y = randInt( 1, rows );
        GameTile tile = m_tilemap.getTile( x, y );

        while ( !tile.isTraversable( ) || tile == current_tile ) {
            x = randInt( 1, columns );
            y = randInt( 1, rows );
            tile = m_tilemap.getTile( x, y );
        }

        return tile;
    }

    public void set( GameState state ) { m_state = state; }

    public void setLevelTime( String time ) { m_level_time = time; }

    public void win( ) { m_has_win = true; }

    public void addLive( ) { m_live_count += 1; }

    public void subLive( ) { m_live_count -= 1; }

    private String getLevelName( String path ) {
        final int separator_1 = path.lastIndexOf( '/' );
        final String name = path.substring( separator_1 + 1 );
        final int separator_2 = name.lastIndexOf( '.' );

        return name.substring( 0, separator_2 );
    }

    private float getTileDimensions( int columns, int rows ) {
        final float width = Globals.WIN_WIDTH;
        final float height = Globals.WIN_HEIGHT - Globals.PLAY_UI_HEIGHT;

        return Math.min( width / columns, height / rows );
    }

    public boolean loadLevel( String path ) {
        m_level_name = getLevelName( path );
        m_level_time = "00:00";
        m_live_count = Globals.MAX_LIVE_COUNT;
        m_has_win = false;

        InputStream is = getClass( ).getResourceAsStream( path );

        if ( is == null )  return false;

        Scanner sc = new Scanner( is );

        if ( !sc.hasNextInt( ) || !sc.hasNextInt( ) )  return false;

        final int rows = sc.nextInt( );
        final int columns = sc.nextInt( );

        sc.nextLine( );

        if ( columns < 1 || rows < 1 )  return false;

        final float dimensions = getTileDimensions( columns, rows );

        m_tilemap.create( columns, rows, dimensions );

        int y = 0;

        while ( sc.hasNextLine( ) && y < rows ) {
            final String line = sc.nextLine( );

            for ( int x = 0; x < columns; x++ ) {
                final char tile_type = line.charAt( x );
                final Object entity = null;//m_entity_manager.spawnEntity( tile_type, x * dimensions, y * dimensions, dimensions );

                GameTile tile = m_tilemap.spawnTile( tile_type, x, y );

                if ( tile instanceof GameTileInteractable interactable )
                    interactable.onEnter( this, m_tilemap, null, entity, Vector2.Zero );
            }

            y += 1;
        }

        sc.close( );

        return y == rows;
    }

    public GameState get( ) { return m_state; }

    public String getLevelName( ) { return m_level_name; }

    public String getLevelTime( ) { return m_level_time; }

    public int getLiveCount( ) { return m_live_count; }

    public boolean getHasWin( ) { return m_has_win; }

    public boolean is( GameState state ) { return m_state == state; }

}
