package filters;

import Image.Pixel;
import Image.PixelImage;

/**
 * Created by olgasaliy on 01.05.16.
 * The filter that flips the image horizontally.
 */
public class FlipHorizontalFilter extends AbstractFilter
{
  public String nameFile = "flipH.png";

  public String getFileName() {
    return  nameFile;
  }
  /**
   * Constructs a new flip horizontal filter.
   */
  public FlipHorizontalFilter()
  {
    super("Flip Horizontal");
  }

  /**
   * Method to modify the image by concrete filter
   * @param the_image The image.
   */
  @Override
  public void filter(final PixelImage the_image)
  {
    final Pixel[][] data = the_image.getPixelData();
    for (int row = 0; row < the_image.getHeight(); row++)
    {
      for (int col = 0; col < the_image.getWidth() / 2; col++)
      {
        swap(data, row, col, row, the_image.getWidth() - col - 1);
      }
    }
    the_image.setPixelData(data);
  }
}
