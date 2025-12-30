package graphics;

import utils.Rectangle;
import utils.Vector2;

import java.awt.*;

public class GameRenderManager {

    private Graphics m_graphics;
    private FontMetrics m_font_metrics;
    private Vector2 m_origin;
    private GameTextureManager m_texture_manager;
    private String m_button_texture = "";

    public GameRenderManager( ) {
        m_origin = new Vector2( );
        m_texture_manager = new GameTextureManager( );
    }

    public boolean load( String alias, String path, int columns, int rows ) {
        return m_texture_manager.load( alias, path, columns, rows );
    }

    public void setButtonTexture( String alias ) { m_button_texture = alias; }

    public void prepare( Graphics graphics ) {
        m_graphics = graphics;
        m_font_metrics = graphics.getFontMetrics( );

        m_origin.set( 0.f, 0.f );
    }

    public GameRenderManager setOrigin( Vector2 origin ) {
        return setOrigin( (int)origin.getX( ), (int)origin.getY( ) );
    }

    public GameRenderManager setOrigin( float x, float y ) {
        m_origin.set( x, y );

        return this;
    }
    public GameRenderManager setColor( Color color ) {
        m_graphics.setColor( color );

        return this;
    }

    public Rectangle drawRect(Vector2 min, Vector2 max ) {
        final Color color = m_graphics.getColor( );

        return drawRect( min, max, color );
    }

    private void drawRectInternal( Vector2 min, Vector2 max ) {
        final Vector2 position = getPosition( min );

        m_graphics.drawRect( (int)position.getX( ), (int)position.getY( ), (int)max.getX( ), (int)max.getY( ) );
    }

    public Rectangle drawRect(Vector2 min, Vector2 max, Color color ) {
        final Color old_color = m_graphics.getColor( );

        m_graphics.setColor( color );
        drawRectInternal( min, max );
        m_graphics.setColor( old_color );

        return new utils.Rectangle( getPosition( min ), max );
    }

    public GameRenderManager drawText( String text ) {
        final Vector2 location = new Vector2( );
        final Color color = m_graphics.getColor( );

        return drawText( text, location, color );
    }

    public GameRenderManager drawText( String text, Vector2 location ) {
        final Color color = m_graphics.getColor( );

        return drawText( text, location, color );
    }

    public GameRenderManager drawText( String text, Vector2 location, Color color ) {
        final Vector2 position = getPosition( location );
        final Color old_color = m_graphics.getColor( );

        m_graphics.setColor( color );
        m_graphics.drawChars( text.toCharArray( ), 0, text.length( ), (int)position.getX( ), (int)position.getY( ) );
        m_graphics.setColor( old_color );

        return this;
    }

    public GameRenderManager drawTextCentered( String text, Vector2 min, Vector2 max ) {
        final Color color = m_graphics.getColor( );

        return drawTextCentered( text, min, max, color );
    }

    public GameRenderManager drawTextCentered( String text, Vector2 min, Vector2 max, Color color ) {
        final Vector2 position = getPosition( min.add( max ) );
        final Color old_color = m_graphics.getColor( );
        final int text_width = m_font_metrics.stringWidth( text );
        final int text_height = m_font_metrics.getHeight( );
        final int text_ascent = m_font_metrics.getAscent( );
        final int x = ( (int)position.getX( ) - text_width ) / 2;
        final int y = (int)( m_origin.getY( ) + min.getY( ) ) + ( ( (int)max.getY( ) - text_height ) / 2 ) + text_ascent;

        m_graphics.setColor( color );
        m_graphics.drawChars( text.toCharArray( ), 0, text.length( ), x, y );
        m_graphics.setColor( old_color );

        return this;
    }

    private void drawSpriteInternal( Vector2 location, Vector2 dimensions, GameTexture texture, GameSprite sprite ) {
        final Vector2 position = getPosition( location );
        final int x1 = (int)position.getX( );
        final int y1 = (int)position.getY( );
        final int x2 = x1 + (int)dimensions.getX( );
        final int y2 = y1 + (int)dimensions.getY( );

        m_graphics.drawImage( texture.Image, x1, y1, x2, y2, sprite.X1, sprite.Y1, sprite.X2, sprite.Y2, null );
    }

    public Rectangle drawSprite( String alias, Vector2 location, Vector2 dimensions, int column, int row ) {
        final GameTexture texture = m_texture_manager.get( alias );

        if ( texture == null )
            return null;

        final GameSprite sprite = texture.getSprite( column, row );

        if ( sprite == null )
            return null;

        drawSpriteInternal( location, dimensions, texture, sprite );

        return new Rectangle( getPosition( location ), dimensions );
    }

    public Rectangle drawTexture( String alias, Vector2 location, Vector2 dimensions ) {
        return drawSprite( alias, location, dimensions, 0, 0 );
    }

    public utils.Rectangle drawButton( String text, Vector2 min, Vector2 max ) {
        final Color color = m_graphics.getColor( );

        return drawButton( text, min, max, color );
    }

    private void drawButtonBorders( Vector2 min, Vector2 max ) {
        final GameTexture texture = m_texture_manager.get( m_button_texture );

        if ( texture != null ) {
            // TODO
            // top *-*
            drawSpriteInternal( min, max, texture, texture.getSprite( 0, 0 ) );
            drawSpriteInternal( min, max, texture, texture.getSprite( 1, 0 ) );
            drawSpriteInternal( min, max, texture, texture.getSprite( 3, 0 ) );

            // bottom *-*
            drawSpriteInternal( min, max, texture, texture.getSprite( 0, 3 ) );
            drawSpriteInternal( min, max, texture, texture.getSprite( 1, 3 ) );
            drawSpriteInternal( min, max, texture, texture.getSprite( 3, 3 ) );

            // left, right | |
            drawSpriteInternal( min, max, texture, texture.getSprite( 0, 1 ) );
            drawSpriteInternal( min, max, texture, texture.getSprite( 3, 1 ) );
        } else
            drawRectInternal( min, max );
    }

    public Rectangle drawButton( String text, Vector2 min, Vector2 max, Color color ) {
        drawButtonBorders( min, max );
        drawTextCentered( text, min, max, color );

        return new Rectangle( getPosition( min ), max );
    }

    private Vector2 getPosition( Vector2 location ) { return m_origin.add( location ); }

}
