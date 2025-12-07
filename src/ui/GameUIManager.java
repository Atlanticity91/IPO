package ui;

import inputs.GameInputManager;
import utils.GameRenderManager;
import utils.Golbals;

import java.awt.*;

public class GameUIManager {

    public GameUIManager( ) {
    }

    public void display( GameInputManager input_manager, GameRenderManager render_manager ) {
        render_manager.setOrigin( Golbals.WIN_WIDTH / 2, Golbals.WIN_HEIGHT / 2 );
        render_manager.drawText( "Hello World !" );
    }

}
