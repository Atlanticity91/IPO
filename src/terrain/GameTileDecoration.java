package terrain;

import graphics.GameRenderManager;
import utils.Hitbox;
import utils.Vector2;

import java.awt.*;

public class GameTileDecoration extends GameTile {

    public GameTileDecoration( Vector2 location, Vector2 dimensions, boolean is_traversable ) {
        super( is_traversable, location, dimensions, is_traversable ? Color.white : Color.black );
    }

    @Override
    public void display( GameRenderManager render_manager ){
        super.display( render_manager );

        if ( isTraversable( ) )
            render_manager.drawSprite( "deco", getLocation( ), getDimensions( ), 0, 0 );
        else {
            render_manager.drawSprite("wall", getLocation(), getDimensions(), 0, 0 );
            final Hitbox h = getLocalHitbox( );
            render_manager.drawRect( h.getMin(), h.getMax(), Color.red );
        }
    }

}
