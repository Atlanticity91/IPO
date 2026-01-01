package ui;

import inputs.GameInputManager;
import inputs.GameInputState;
import graphics.GameRenderManager;
import utils.GameStateManager;
import utils.Hitbox;
import utils.Vector2;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class GameUIScreen {

    protected boolean button(
            GameInputManager input_manager,
            GameRenderManager render_manager,
            String text,
            Vector2 position,
            Vector2 dimension
    ) {
        final Hitbox hitbox = render_manager.getHitbox( position, dimension );
        final boolean is_over = input_manager.getMouseLocation( ).isIn( hitbox );
        final Color color = is_over ? Color.black : Color.gray;

        render_manager.drawButton( text, position, dimension, color, is_over );

        if ( input_manager.isMouseButton( MouseEvent.BUTTON1, GameInputState.Pressed ) )
            return is_over;

        return false;
    }

    public abstract void display(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameRenderManager render_manager
    );

}
