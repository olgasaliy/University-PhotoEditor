package filters;

import Image.PixelImage;

/**
 * Created by olgasaliy on 01.05.16.
 * The filter that detects edges in the image.
 */
public class EdgeDetectFilter extends AbstractWeightedFilter
{

  public String nameFile = "detect.png";

  public String getFileName() {
    return  nameFile;
  }
  /**
   * A 3x3 matrix of weights to use in the filtering algorithm.
   */
  private static final int[][] WEIGHTS = {{-1, -1, -1}, {-1, 8, -1}, {-1, -1, -1}};

  /**
   * Constructs a new edge detection filter.
   */
  public EdgeDetectFilter()
  {
    super("Edge Detect",WEIGHTS);
  }

}
