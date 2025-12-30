package utils;

import entities.GameEntityManager;
import terrain.GameTilemap;

public class GameStateManager {

    private GameState m_state = GameState.MenuScreen;
    private GameTilemap m_tilemap;
    private GameEntityManager m_entity_manager;
    private String m_level_name = "Reduxma";
    private int m_live_count = Globals.MAX_LIVE_COUNT;

    public GameStateManager( GameTilemap tilemap, GameEntityManager entity_manager ) {
        m_tilemap = tilemap;
        m_entity_manager = entity_manager;
    }

    public void set( GameState state ) { m_state = state; }

    public void addLive( ) { m_live_count += 1; }

    public void subLive( ) { m_live_count -= 1; }

    public void loadLevel( String path ) {
        m_live_count = Globals.MAX_LIVE_COUNT;
    }

    public GameState get( ) { return m_state; }

    public String getLevelName( ) { return m_level_name; }

    public int getLiveCount( ) { return m_live_count; }

    public boolean is( GameState state ) { return m_state == state; }

}
