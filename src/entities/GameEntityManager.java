package entities;

import graphics.GameRenderManager;
import inputs.GameInputManager;
import terrain.GameTileEmitter;
import terrain.GameTilemap;
import utils.GameDirection;
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
            case 'l' :
            case 'L' :
            case 'j' :
            case 'J' : entity = new GameEntityLaser( location ); break;

            default : break;
        }

        return addEntity( entity );
    }

    public GameEntity addEntity( GameEntity entity ) {
        if ( entity == null )
            return null;

        entity.onSpawn( );
        m_entity_list.add( entity );

        return entity;
    }

    public void kill( GameEntity entity ) {
        if ( entity == null )
            return;

        entity.onKill( );
        m_entity_list.remove( entity );
    }

    private void tickEntities(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameTilemap tilemap
    ) {
        for ( GameEntity entity : m_entity_list ) {
            if ( entity.getIsAlive( ) )
                entity.tick( state_manager, input_manager, tilemap, this );
        }
    }

    private void tickCollisions( GameStateManager state_manager ) {
        GameEntityPlayer player = getFirst( GameEntityPlayer.class );

        if ( player == null )
            return;

        for ( GameEntity entity : m_entity_list ) {
            if ( entity != player ) {
                final Vector2 offset = player.doCollide( entity );

                if ( offset != null )
                    entity.onCollide( state_manager, this, player, offset );
            }
        }
    }

    public void tick(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameTilemap tilemap
    ) {
        tickEntities( state_manager, input_manager, tilemap );
        tickCollisions( state_manager );

        m_entity_list.removeIf( e -> !e.getIsAlive( ) );
    }

    public void display( GameRenderManager render_manager ) {
        for ( GameEntity entity : m_entity_list )
            entity.display( render_manager );
    }

    public ArrayList<GameEntity> getAll( ) { return m_entity_list; }

    public <T extends GameEntity> T getFirst( Class<T> entity_class ) {
        for ( GameEntity entity : m_entity_list ) {
            if ( entity_class.isInstance( entity ) )
                return entity_class.cast( entity );
        }

        return null;
    }

}
