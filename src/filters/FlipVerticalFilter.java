package filters;

import Image.Pixel;
import Image.PixelImage;

/**
 * Created by olgasaliy on 01.05.16.
 * A filter that flips the image vertically.
 */
public class FlipVerticalFilter extends AbstractFilter
{
  public String nameFile = "flipV.png";

  public String getFileName() {
    return  nameFile;
  }
  /**
   * Constructs a new flip vertical filter.
   */
  public FlipVerticalFilter()
  {
    super("Flip Vertical");
  }

  /**
   * Method to modify the image by concrete filter
   * @param the_image The image.
   */
  @Override
  public void filter(final PixelImage the_image)
  {
    final Pixel[][] data = the_image.getPixelData();
    for (int row = 0; row < the_image.getHeight() / 2; row++)
    {
      for (int col = 0; col < the_image.getWidth(); col++)
      {
        swap(data, row, col, the_image.getHeight() - row - 1, col);
      }
    }
    the_image.setPixelData(data);
  }
}
