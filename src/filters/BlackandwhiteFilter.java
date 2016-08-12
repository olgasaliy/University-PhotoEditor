package filters;

import Image.PixelImage;

/**
 * Created by olgasaliy on 01.05.16.
 * Filter that converts the image to black and white colors (grayscale).
 */
public class BlackandwhiteFilter extends AbstractFilter
{
  public String nameFile = "bw.png";

  public String getFileName() {
    return  nameFile;
  }
  /**
   * The mask for transforming the colors to grayscale.
   */
  private static final int MASK = 0xff;

  /**
   * The number of bits offset for the alpha channel.
   */
  private static final int ALPHA_OFFSET = 24;

  /**
   * The number of bits offset for the red channel.
   */
  private static final int RED_OFFSET = 16;

  /**
   * The number of bits offset for the green channel.
   */
  private static final int GREEN_OFFSET = 8;

  /**
   * A constant used to average the color values.
   */
  private static final int NUM_COLORS = 3;

  /**
   * Constructs a new grayscale filter.
   */
  public BlackandwhiteFilter()
  {
    super("Black and white");
  }

  /**
   * Method to modify the image by concrete filter
   * @param the_image The image.
   */
  @Override
  public void filter(final PixelImage the_image)
  {
    final int w = the_image.getWidth(null);
    final int h = the_image.getHeight(null);
    for (int i = 0; i < w; i++)
    {
      for (int j = 0; j < h; j++)
      {
        final int p = the_image.getRGB(i, j);
        final int a = (((p >> RED_OFFSET) & MASK) + ((p >> GREEN_OFFSET) & MASK)
                      + (p & MASK)) / NUM_COLORS;
        the_image.setRGB(i, j, (MASK << ALPHA_OFFSET) | (a << RED_OFFSET)
                         | (a << GREEN_OFFSET) | a);
      }
    }
  }
}
