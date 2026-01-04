package entities;

import graphics.GameRenderManager;
import inputs.GameInputManager;
import terrain.GameTilemap;
import utils.GameStateManager;

import java.util.ArrayList;

public class GameEntityManager {

    private ArrayList<GameEntity> m_entity_list;

    public GameEntityManager( ) {
        m_entity_list = new ArrayList<>( );
    }

    public <T extends GameEntity> void spawn( T entity ) {
        if ( entity == null )
            return;

        entity.onSpawn( );
        m_entity_list.add( entity );
    }

    public <T extends GameEntity> void kill( T entity ) {
        if ( entity == null )
            return;

        entity.onKill( );
        m_entity_list.remove( entity );
    }

    public void tick(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameTilemap tilemap
    ) {
        for ( GameEntity entity : m_entity_list ) {
            if ( entity.getIsAlive( ) )
                entity.tick( state_manager, input_manager, tilemap, this );
        }

        m_entity_list.removeIf( e -> !e.getIsAlive( ) );
    }

    public void display( GameRenderManager render_manager ) {
        for ( GameEntity entity : m_entity_list ) {
            if ( entity.getIsAlive( ) )
                entity.display( render_manager );
        }
    }

    public ArrayList<GameEntity> getAll( ) { return m_entity_list; }

}
