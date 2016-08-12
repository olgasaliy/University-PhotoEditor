package Strategy;

import Image.PixelImage;

/**
 * Created by olgasaliy on 01.05.16.
 * The interface for filters that modify images.
 * Also this interface involved in Strategy pattern.
 */
public interface Filter
{
  /**
   * Method to get name of image
   * @return name of file
     */
  String getFileName();
  /**
   * Modifies the image according to this filter's algorithm.
   * Is used by Strategy pattern
   * @param the_image The image.
   */
  void filter(PixelImage the_image);

  /**
   * Returns a text description of this filter.
   * 
   * @return a text description of this filter.
   */
  String getDescription();
}
