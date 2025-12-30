package ui;

import inputs.GameInputManager;
import graphics.GameRenderManager;
import utils.GameState;
import utils.GameStateManager;
import utils.Globals;
import utils.Vector2;

public class GameUIScreenMenu extends GameUIScreen {

    private Vector2 m_button_dimensions;

    public GameUIScreenMenu( ) {
        m_button_dimensions = new Vector2( 256, 64 );
    }

    @Override
    public void display(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameRenderManager render_manager
    ) {
        render_manager.drawText( "Menu !", new Vector2( 10, 10 ) );

        final Vector2 origin = new Vector2(
                ( Globals.WIN_WIDTH - m_button_dimensions.getX( ) ) / 2,
                ( Globals.WIN_HEIGHT - m_button_dimensions.getY( ) ) / 2
        );

        if ( button( input_manager, render_manager, "Jouer", origin, m_button_dimensions ) )
            state_manager.set( GameState.SelectionScreen );
    }

}
