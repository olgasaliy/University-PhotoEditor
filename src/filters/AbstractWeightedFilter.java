
package filters;

import Image.Pixel;
import Image.PixelImage;

/**
 * Created by olgasaliy on 01.05.16.
 * Provides common behavior for all weighted filters.
 */
public abstract class AbstractWeightedFilter extends AbstractFilter
{

  /**
   * A 3x3 grid of weights to use for the weighted filter algorithm.
   */
  private final int[][] my_weights;

  /**
   * Constructs a new weighted filter.
   * @param the_description a descriptive name for this filter
   * @param the_weights the 3x3 grid of weights to use
   */
  public AbstractWeightedFilter(final String the_description, final int[][] the_weights)
  {
    super(the_description);
    my_weights = the_weights.clone();
  }

  /**
   * Filters the specified image.
   * @param the_image The image.
   */
  @Override
  public void filter(final PixelImage the_image)
  {
    weight(the_image, my_weights);
  }

  /**
   * Applies a "weighting" to each pixel, where its new value is produced by
   * doing a weighted average of the 3x3 grid of pixels around it. For example,
   * A Gaussian blur/softening effect can be achieved by applying the following
   * weights to each pixel:
   *
   * <pre>
   *    1  2  1
   *    2  4  2
   *    1  2  1
   * </pre>
   *
   * Since the weights increase the pixel's color value, likely beyond the legal
   * maximum color value of 255, a scale-down is applied based on the sum of the
   * weights.
   *
   * @param the_image The image.
   * @param the_weights The weights matrix. This must be a non-null 3x3 matrix
   *          or an IllegalArgumentException is thrown.
   * @exception IllegalArgumentException if the weights are invalid.
   */
  protected void weight(final PixelImage the_image, final int[][] the_weights)
          throws IllegalArgumentException
  {
    checkWeights(the_weights);
    int sum = 0;
    for (final int[] row : the_weights) {
      for (final int col : row) {
        sum += col;
      }
    }

    if (sum == 0) {
      sum = sum + 1;
    }

    weight(the_image, the_weights, sum);
  }

  /**
   * Applies a "weighting" to each pixel, with the given scale-down factor.
   *
   * @param the_image The image.
   * @param the_weights The weights matrix. This must be a non-null 3x3 matrix
   *          or an IllegalArgumentException is thrown.
   * @param the_scale The scale-down factor.
   * @exception IllegalArgumentException if the weights are invalid.
   * @see #weight(PixelImage, int[][])
   */
  protected void weight(final PixelImage the_image, final int[][] the_weights,
                        final int the_scale) throws IllegalArgumentException
  {
    checkWeights(the_weights);

    final int w = the_image.getWidth(null);
    final int h = the_image.getHeight(null);
    final Pixel[][] old_pixels = the_image.getPixelData();
    final Pixel[][] new_pixels = new Pixel[h][w];

    for (int y = 0; y < h; y++)
    {
      for (int x = 0; x < w; x++)
      {
        // add up 9 neighboring pixels
        int red = 0;
        int green = 0;
        int blue = 0;
        //included situation with borders
        for (int j = Math.max(0, y - 1); j <= Math.min(y + 1, h - 1); j++)
        {
          for (int i = Math.max(0, x - 1); i <= Math.min(x + 1, w - 1); i++)
          {
            // Pixel p = oldPixels[i][j];
            final Pixel p = old_pixels[j][i];
            final int weight = the_weights[y - j + 1][x - i + 1];
            red = red + p.getRed() * weight;
            green = green + p.getGreen() * weight;
            blue = blue + p.getBlue() * weight;
          }
        }

        // account for negative / too high color values
        red = normalize(red / the_scale);
        green = normalize(green / the_scale);
        blue = normalize(blue / the_scale);

        new_pixels[y][x] = new Pixel(red, green, blue);
      }
    }

    the_image.setPixelData(new_pixels);
  }

  /**
   * Checks to see if the specified weights matrix is valid (that is, is
   * non-null and a Pixel.NUM_CHANNELS-square grid).
   *
   * @param the_weights The weights matrix.
   * @exception IllegalArgumentException if the weights matrix is invalid.
   */
  private void checkWeights(final int[][] the_weights) throws IllegalArgumentException
  {
    if (the_weights == null || the_weights.length != Pixel.NUM_CHANNELS
            || the_weights[0] == null || the_weights[0].length != Pixel.NUM_CHANNELS
            || the_weights[1] == null || the_weights[1].length != Pixel.NUM_CHANNELS
            || the_weights[2] == null || the_weights[2].length != Pixel.NUM_CHANNELS)
    {
      throw new IllegalArgumentException("must pass correctly-sized grid");
    }
  }

}
