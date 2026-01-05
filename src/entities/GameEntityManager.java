package entities;

import graphics.GameRenderManager;
import inputs.GameInputManager;
import terrain.GameTilemap;
import utils.GameStateManager;
import utils.Vector2;

import java.util.ArrayList;

public class GameEntityManager {

    private ArrayList<GameEntity> m_entity_list;

    public GameEntityManager( ) {
        m_entity_list = new ArrayList<>( );
    }

    public void clear( ) { m_entity_list.clear( ); }

    public GameEntity spawnEntity(
            char tile_type,
            float x,
            float y,
            float dimensions
    ) {
        final Vector2 location = new Vector2( x + dimensions * .5f, y + dimensions * .5f );
        GameEntity entity = null;

        switch ( tile_type ) {
            case 'v' : entity = new GameEntityPlayer( location, dimensions ); break;
            default : break;
        }

        if ( entity != null ) {
            entity.onSpawn( );
            m_entity_list.add( entity );
        }

        return entity;
    }

    public void kill( GameEntity entity ) {
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
