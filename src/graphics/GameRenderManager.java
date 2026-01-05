package graphics;

import utils.Hitbox;
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

        ((Graphics2D)m_graphics).setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

        setOrigin( 0.f, 0.f );
    }

    public GameRenderManager setOrigin( Vector2 origin ) {
        m_origin.set( origin );

        return this;
    }

    public GameRenderManager setOrigin( float x, float y ) {
        m_origin.set( x, y );

        return this;
    }

    public GameRenderManager setColor( Color color ) {
        m_graphics.setColor( color );

        return this;
    }

    public void drawRect( Vector2 min, Vector2 max ) {
        final Color color = m_graphics.getColor( );

        drawRect( min, max, color );
    }

    public void drawRect( Vector2 min, Vector2 max, Color color ) {
        final Vector2 position = getPosition( min );
        final Color old_color = m_graphics.getColor( );

        m_graphics.setColor( color );
        m_graphics.fillRect( (int)position.getX( ), (int)position.getY( ), (int)max.getX( ), (int)max.getY( ) );
        m_graphics.setColor( old_color );
    }

    public void drawCircle( Vector2 location, float diameter ) {
        final Color color = m_graphics.getColor( );

        drawCircle( location, diameter, color );
    }

    public void drawCircle( Vector2 location, float diameter, Color color ) {
        final Vector2 position = getPosition( location );
        final Color old_color = m_graphics.getColor( );

        m_graphics.setColor( color );
        m_graphics.fillOval( (int)position.getX( ), (int)position.getY( ), (int)diameter, (int)diameter );
        m_graphics.setColor( old_color );
    }

    public void drawText( String text ) {
        final Vector2 location = new Vector2( );
        final Color color = m_graphics.getColor( );

        drawText( text, location, color );
    }

    public void drawText( String text, Vector2 location ) {
        final Color color = m_graphics.getColor( );

        drawText( text, location, color );
    }

    public void drawText( String text, Vector2 location, Color color ) {
        final Vector2 position = getPosition( location );
        final Color old_color = m_graphics.getColor( );

        m_graphics.setColor( color );
        m_graphics.drawChars( text.toCharArray( ), 0, text.length( ), (int)position.getX( ), (int)position.getY( ) );
        m_graphics.setColor( old_color );
    }

    public void drawTextCentered( String text, Vector2 location, Vector2 dimensions ) {
        final Color color = m_graphics.getColor( );

        drawTextCentered( text, location, dimensions, color );
    }

    public void drawTextCentered( String text, Vector2 location, Vector2 dimensions, Color color ) {
        final Vector2 position = getPosition( location );
        final Color old_color = m_graphics.getColor( );
        final int text_width = m_font_metrics.stringWidth( text );
        final int text_height = m_font_metrics.getHeight( );
        final int text_ascent = m_font_metrics.getAscent( );

        final int x = (int)position.getX( ) + ( ( (int)dimensions.getX( ) - text_width ) / 2 );
        final int y = (int)position.getY( ) + ( ( (int)dimensions.getY( ) - text_height ) / 2 ) + text_ascent;

        m_graphics.setColor( color );
        m_graphics.drawChars( text.toCharArray( ), 0, text.length( ), x, y );
        m_graphics.setColor( old_color );
    }

    private void drawSpriteInternal( Vector2 location, Vector2 dimensions, GameTexture texture, GameSprite sprite ) {
        final Vector2 position = getPosition( location );
        final int x1 = (int)position.getX( );
        final int y1 = (int)position.getY( );
        final int x2 = x1 + (int)dimensions.getX( );
        final int y2 = y1 + (int)dimensions.getY( );

        m_graphics.drawImage( texture.Image, x1, y1, x2, y2, sprite.X1, sprite.Y1, sprite.X2, sprite.Y2, null );
    }

    public void drawSprite( String alias, Vector2 location, Vector2 dimensions, int column, int row ) {
        final GameTexture texture = m_texture_manager.get( alias );

        if ( texture == null ) return;

        final GameSprite sprite = texture.getSprite( column, row );

        if ( sprite == null ) return;

        drawSpriteInternal( location, dimensions, texture, sprite );
    }

    public void drawTexture( String alias, Vector2 location, Vector2 dimensions ) {
        drawSprite( alias, location, dimensions, 0, 0 );
    }

    public void drawBorders( Vector2 location, Vector2 dimensions ) {
        final Color color = m_graphics.getColor( );

        drawBorders( location, dimensions, color, false );
    }

    public void drawBorders( Vector2 location, Vector2 dimensions, Color color, boolean is_over ) {
        final GameTexture texture = m_texture_manager.get(m_button_texture);

        if ( texture == null ) {
            drawRect( location, dimensions, color );

            return;
        }

        final Vector2 pos_stop = location.add(dimensions);
        final float half_height = dimensions.getY( ) / 2;
        final Vector2 center_width = new Vector2(dimensions.getX( ) - 2.f * half_height, half_height);

        // top *-*
        drawSpriteInternal( location, new Vector2(half_height), texture, texture.getSprite( 0, 0 ) );
        drawSpriteInternal( location.add( half_height, 0.f), center_width, texture, texture.getSprite( 1, 0 ) );
        drawSpriteInternal( pos_stop.sub( half_height, dimensions.getY( ) ), new Vector2( half_height ), texture, texture.getSprite( 3, 0 ) );

        // bottom *-*
        drawSpriteInternal( location.add( 0.f, half_height ), new Vector2( half_height ), texture, texture.getSprite( 0, 3 ) );
        drawSpriteInternal( location.add( half_height, half_height ), center_width, texture, texture.getSprite( 1, 3 ) );
        drawSpriteInternal( pos_stop.sub( half_height ), new Vector2( half_height ), texture, texture.getSprite( 3, 3 ) );

        if ( is_over )
            m_graphics.drawRect( (int)location.getX( ), (int)location.getY( ), (int)dimensions.getX( ), (int)dimensions.getY( ) );
    }

    public void drawButton( String text, Vector2 location, Vector2 dimensions, boolean is_over ) {
        final Color color = m_graphics.getColor( );

        drawButton( text, location, dimensions, color, is_over );
    }

    public void drawButton( String text, Vector2 location, Vector2 dimensions, Color color, boolean is_over ) {
        drawBorders( location, dimensions, color, is_over );
        drawTextCentered( text, location, dimensions, color );
    }

    private Vector2 getPosition( Vector2 location ) { return m_origin.add( location ); }

    public Hitbox getHitbox(Vector2 location, Vector2 dimensions ) {
        final Vector2 position = getPosition( location );

        return new Hitbox( position, dimensions );
    }

}
