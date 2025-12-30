package utils;

import entities.GameEntityManager;
import terrain.GameTilemap;

public class GameStateManager {

    private GameState m_state = GameState.MenuScreen;
    private GameTilemap m_tilemap;
    private GameEntityManager m_entity_manager;
    private String m_level_name = "Reduxma";

    public GameStateManager( GameTilemap tilemap, GameEntityManager entity_manager ) {
        m_tilemap = tilemap;
        m_entity_manager = entity_manager;
    }

    public void set( GameState state ) { m_state = state; }

    public void loadLevel( String path ) {
    }

    public GameState get( ) { return m_state; }

    public String getLevelName( ) { return m_level_name; }

    public boolean is( GameState state ) { return m_state == state; }

}
