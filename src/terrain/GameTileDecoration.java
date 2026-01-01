package terrain;

import utils.Vector2;

import java.awt.*;

public class GameTileDecoration extends GameTile {

    public GameTileDecoration( Vector2 location, Vector2 dimensions, boolean is_traversable ) {
        super( is_traversable, location, dimensions, is_traversable ? Color.white : Color.black );
    }

}
