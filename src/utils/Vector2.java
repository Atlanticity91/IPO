package utils;

public class Vector2 {

    public static final Vector2 Zero = new Vector2( );
    public static final Vector2 One = new Vector2( 1.f );

    private float m_x;
    private float m_y;

    public Vector2( ) {
        this( 0.f, 0.f );
    }

    public Vector2( float scalar ) {
        this( scalar, scalar );
    }

    public Vector2( Vector2 other ) {
        this( other.m_x, other.m_y );
    }

    public Vector2( float x, float y ) {
        set( x, y );
    }

    public void set( Vector2 other ) {
        set( other.m_x, other.m_y );
    }

    public void set( float x, float y ) {
        m_x = x;
        m_y = y;
    }

    public void setX( float scalar ) { m_x = scalar; }

    public void setY( float scalar ) { m_y = scalar; }

    public Vector2 add( float scalar ) {
        return add( scalar, scalar );
    }

    public Vector2 add( float x, float y ) {
        return new Vector2( m_x + x, m_y + y );
    }

    public Vector2 add( Vector2 other ) {
        if ( other == null )
            return new Vector2( this );

        return new Vector2( m_x + other.m_x, m_y + other.m_y );
    }

    public Vector2 sub( float scalar ) {
        return sub( scalar, scalar );
    }

    public Vector2 sub( float x, float y ) {
        return new Vector2( m_x - x, m_y - y );
    }

    public Vector2 sub( Vector2 other ) {
        if ( other == null )
            return new Vector2( this );

        return new Vector2( m_x - other.m_x, m_y - other.m_y );
    }

    public Vector2 div( float scalar ) {
        if ( scalar == 0.f )
            throw new IllegalArgumentException( "Division par 0" );

        return new Vector2( m_x / scalar, m_y / scalar );
    }

    public Vector2 scale( float scalar ) {
        return scale( scalar, scalar );
    }

    public Vector2 scale( Vector2 other ) {
        return scale( other.m_x, other.m_y );
    }

    public Vector2 scale( float x, float y ) {
        return new Vector2( m_x * x, m_y * y );
    }

    public Vector2 negate( ) {
        return new Vector2( -m_x, -m_y );
    }

    public Vector2 rotate( float degrees ) {
        final float radians = degrees * Globals.TO_RADS;
        final float cos = (float)Math.cos( radians );
        final float sin = (float)Math.sin( radians );

        return new Vector2(
                m_x * cos - m_y * sin,
                m_x * sin + m_y * cos
        );
    }

    public static Vector2 direction( Vector2 src, Vector2 dst ) {
        return src.sub( dst ).normalize( );
    }

    @Override
    public String toString( ) { return "{ x : " + m_x + ", y : " + m_y + " }"; }

    public float getX( ) { return m_x; }

    public float getY( ) { return m_y; }

    public float magnitudeSq( ) {
        return m_x * m_x + m_y * m_y;
    }

    public float magnitude( ) {
        final float mag_sq = magnitudeSq( );

        return (float)Math.sqrt( mag_sq );
    }

    public Vector2 normalize( ) {
        final float mag = magnitude( );

        return div( mag );
    }

    public boolean isIn( Hitbox rectangle ) {
        if ( rectangle == null )
            return false;

        final Vector2 min = rectangle.getMin( );
        final Vector2 max = rectangle.getMax( );

        return m_x >= min.m_x && m_x <= max.m_x &&
                m_y >= min.m_y && m_y <= max.m_y;
    }

}
