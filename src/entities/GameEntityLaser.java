package entities;

import graphics.GameRenderManager;
import inputs.GameInputManager;
import terrain.GameTile;
import terrain.GameTileMirror;
import terrain.GameTilemap;
import utils.GameDirection;
import utils.GameStateManager;
import utils.Vector2;

import java.awt.*;
import java.io.ObjectInputStream;

public class GameEntityLaser extends GameEntity {

    public GameEntityLaser( Vector2 location ) {
        super( location, Vector2.Zero );
    }

    public void trace(
            GameStateManager state_manager,
            GameTilemap tilemap,
            GameDirection direction
    ) {
        int x = 0;
        int y = 0;

        switch ( direction ) {
            case GameDirection.North : y = -1; break;
            case GameDirection.South : y =  1; break;
            case GameDirection.East  : x = -1; break;
            case GameDirection.West  : x =  1; break;
            default : return;
        }

        GameTile tile = null;

        do {
            tile = tilemap.getTile( x, y );

            if ( x > 0 ) x += 1;
            if ( x < 0 ) x -= 1;
            if ( y > 0 ) y += 1;
            if ( y < 0 ) y -= 1;
        } while ( tile != null && tile.isTraversable( ) && !( tile instanceof GameTileMirror ) );

        if ( tile instanceof GameTileMirror mirror )
            mirror.onEnter( state_manager, tilemap, null, this, Vector2.Zero );

        final Vector2 tile_dimensions = tilemap.getTileDimensions( );
        final Vector2 half_tile = tile_dimensions.div( 2.f );
        final float width = .2f;

        if ( x != 0 ) {
            setDimensions(new Vector2(x * tile_dimensions.getX(), tile_dimensions.getX() * width));
            setLocation( getLocation().add( 0.f, getDimensions().getY() / 2.f ) );
        }else if ( y != 0 ) {
            setDimensions(new Vector2(tile_dimensions.getX() * width, y * tile_dimensions.getY()));
            setLocation( getLocation().add( getDimensions().getX() / 2.f , 0.f));
        }
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

    @Override
    public void display( GameRenderManager render_manager ) {
        render_manager.drawRect( getLocation( ), getDimensions( ), Color.orange );
    }

}
