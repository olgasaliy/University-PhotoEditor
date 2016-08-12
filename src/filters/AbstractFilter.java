package filters;

import Image.Pixel;
import Strategy.Filter;

/**
 * Created by olgasaliy on 01.05.16.
 * Abstract superclass for all image filters.
 * Also it is the superclass for all algorithms in Strategy pattern with execute method "filter".
 */
public abstract class AbstractFilter implements Filter
{

  /**
   * The "Filter" suffix.
   */
  private static final String FILTER_SUFFIX = "Filter";

  /**
   * The description of this filter (will be used on buttons).
   */
  private String my_description;

  /**
   * Constructs a filter with the specified description.
   * @param the_description The description.
   */

  public AbstractFilter(final String the_description)
  {
    my_description = the_description;
  }


  /**
   * Returns the text description of this filter.
   * @return the text description of this filter
   */
  @Override
  public String getDescription()
  {
    return my_description;
  }



  /**
   * Normalizes the specified color value to the range 0-255.
   * @param the_color The color value.
   * @return the normalized color value.
   */
  protected int normalize(final int the_color)
  {
    return Math.max(Pixel.MIN_COLOR_VALUE, Math.min(Pixel.MAX_COLOR_VALUE, the_color));
  }

  /**
   * Swaps the specified pixels in the image.
   * @param the_data The image data.
   * @param row_1 The row of the first pixel to swap.
   * @param col_1 The column of the first pixel to swap.
   * @param row_2 The row of the second pixel to swap.
   * @param col_2 The column of the second pixel to swap.
   */
  protected void swap(final Pixel[][] the_data, final int row_1, final int col_1,
                      final int row_2, final int col_2)
  {
    final Pixel temp = the_data[row_1][col_1];
    the_data[row_1][col_1] = the_data[row_2][col_2];
    the_data[row_2][col_2] = temp;
  }
}
