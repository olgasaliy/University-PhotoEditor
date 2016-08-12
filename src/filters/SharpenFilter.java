
package filters;

/**
 * Created by olgasaliy on 01.05.16.
 * The filter that makes the image sharper.
 */
public class SharpenFilter extends AbstractWeightedFilter
{
  public String nameFile = "sharp.png";

  public String getFileName() {
    return  nameFile;
  }
  /**
   * A 3x3 grid of weights to use for the weighted filter algorithm.
   */
  private static final int[][] WEIGHTS = {{-1, -2, -1}, {-2, 28, -2}, {-1, -2, -1}};

  /**
   * Constructs a new sharpening filter.
   */
  public SharpenFilter()
  {
    super("Sharpen", WEIGHTS);
  }

}
