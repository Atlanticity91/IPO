package terrain;

import entities.GameEntityManager;
import graphics.GameRenderManager;
import inputs.GameInputManager;
import utils.GameStateManager;
import utils.Vector2;

public class GameTilemap {

    private Vector2 m_origin;
    private GameTile[] m_tiles;
    private int m_columns = 0;
    private int m_rows = 0;

    public GameTilemap( ) {
        m_origin = new Vector2( );
        m_tiles = null;
    }

    public void create( int columns, int rows, Vector2 dimensions ) {
        m_tiles = new GameTile[ columns * rows ];
        m_columns = columns;
        m_rows = rows;

        for ( int y = 0; y < rows; y++ ) {
            for ( int x = 0; x < columns; x++ ) {
                Vector2 location = new Vector2(
                        (float)x * dimensions.getX( ),
                        (float)y * dimensions.getY( )
                );

                m_tiles[ y * columns + x ] = new GameTile( location, dimensions );
            }
        }
    }

    public void tick(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameEntityManager entity_manager
    ) {
        if ( m_tiles == null )
            return;

        for ( GameTile tile : m_tiles ) {
        }
    }

    public void display( GameRenderManager render_manager ) {
        if ( m_tiles == null )
            return;

        render_manager.setOrigin( m_origin );

        for ( GameTile tile : m_tiles )
            tile.display( render_manager );
    }

}
