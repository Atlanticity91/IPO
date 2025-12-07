package utils;

import java.awt.*;

public class GameRenderManager {

    private Graphics m_graphics;
    private float m_origin_x;
    private float m_origin_y;

    public GameRenderManager( ) {
    }

    public void prepare( Graphics graphics ) {
        m_graphics = graphics;
    }

    public GameRenderManager setOrigin( float x, float y ) {
        m_origin_x = x;
        m_origin_y = y;

        return this;
    }

    public GameRenderManager drawText( String text ) {
        return drawText( text, 0.0f, 0.0f );
    }

    public GameRenderManager drawText( String text, float x, float y ) {
        int pos_x = (int)( m_origin_x + x );
        int pos_y = (int)( m_origin_y + y );

        m_graphics.drawChars( text.toCharArray( ), 0, text.length( ), pos_x, pos_y );

        return this;
    }

}
