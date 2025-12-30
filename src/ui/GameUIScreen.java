package ui;

import inputs.GameInputManager;
import inputs.GameInputState;
import graphics.GameRenderManager;
import utils.GameStateManager;
import utils.Rectangle;
import utils.Vector2;

import java.awt.event.MouseEvent;

public abstract class GameUIScreen {

    protected boolean button(
            GameInputManager input_manager,
            GameRenderManager render_manager,
            String text,
            Vector2 position,
            Vector2 dimension
    ) {
        final Rectangle hitbox = render_manager.drawButton( text, position, dimension );

        if ( input_manager.isMouseButton( MouseEvent.BUTTON1, GameInputState.Pressed ) )
            return input_manager.getMouseLocation( ).isIn( hitbox );

        return false;
    }

    public abstract void display(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameRenderManager render_manager
    );

}
