package Image;

/**
 * Created by olgasaliy on 21.04.16.
 * Represents a pixel (an RGB value).
 */
public class Pixel implements Prototype
{
    // Static Fields

    /**
     * The number of color channels.
     */
    public static final int NUM_CHANNELS = 3;

    /**
     * The minimum value for a color.
     */
    public static final int MIN_COLOR_VALUE = 0;

    /**
     * The maximum value for a color.
     */
    public static final int MAX_COLOR_VALUE = 255;

    // Instance Fields

    /**
     * The red value for this pixel.
     */
    private int my_red;

    /**
     * The green value for this pixel.
     */
    private int my_green;

    /**
     * The blue value for this pixel.
     */
    private int my_blue;


    // Constructors


    /**
     * Constructs a black pixel (all values at minimum).
     */
    public Pixel()
    {
        this(MIN_COLOR_VALUE, MIN_COLOR_VALUE, MIN_COLOR_VALUE);
    }

    /**
     * Constructs a pixel with the specified color values.
     *
     * @param the_red The red value.
     * @param the_green The green value.
     * @param the_blue The blue value.
     */
    public Pixel(final int the_red, final int the_green, final int the_blue)
    {
        my_red = the_red;
        my_green = the_green;
        my_blue = the_blue;
    }

    // Instance Methods

    /**
     * Returns the red value for this pixel.
     *
     * @return the red value for this pixel.
     */
    public int getRed()
    {
        return my_red;
    }

    /**
     * Returns the green value for this pixel.
     *
     * @return the green value for this pixel.
     */
    public int getGreen()
    {
        return my_green;
    }

    /**
     * Returns the blue value for this pixel.
     *
     * @return the blue value for this pixel.
     */
    public int getBlue()
    {
        return my_blue;
    }

    /**
     * Sets the red value for this pixel to the specified value.
     *
     * @param the_red The new value.
     * @exception IllegalArgumentException if the specified value is less than
     *              MIN_COLOR_VALUE or greater than MAX_COLOR_VALUE.
     */
    public void setRed(final int the_red) throws IllegalArgumentException
    {
        checkColorValue(the_red);
        my_red = the_red;
    }

    /**
     * Sets the green value for this pixel to the specified value.
     *
     * @param the_green The new value.
     * @exception IllegalArgumentException if the specified value is less than
     *              MIN_COLOR_VALUE or greater than MAX_COLOR_VALUE.
     */
    public void setGreen(final int the_green) throws IllegalArgumentException
    {
        checkColorValue(the_green);
        my_green = the_green;
    }

    /**
     * Sets the blue value for this pixel to the specified value.
     *
     * @param the_blue The new value.
     * @exception IllegalArgumentException if the specified value is less than
     *              MIN_COLOR_VALUE or greater than MAX_COLOR_VALUE.
     */
    public void setBlue(final int the_blue) throws IllegalArgumentException
    {
        checkColorValue(the_blue);
        my_blue = the_blue;
    }

    /**
     * Checks to see if the specified color value is valid.
     *
     * @param the_value The value.
     * @exception IllegalArgumentException if the specified value is less than
     *              MIN_COLOR_VALUE or greater than MAX_COLOR_VALUE.
     */
    private void checkColorValue(final int the_value) throws IllegalArgumentException
    {
        if (the_value < MIN_COLOR_VALUE || the_value > MAX_COLOR_VALUE)
        {
            throw new IllegalArgumentException("Invalid color value: " + the_value);
        }
    }

    @Override
    public Prototype Clone() throws CloneNotSupportedException {
        return new Pixel(my_red, my_green, my_blue);
    }


}
