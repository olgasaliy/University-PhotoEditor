package filters;

/**
 * Created by olgasaliy on 01.05.16.
 * The filter that makes the image less sharp.
 */
public class SoftenFilter extends AbstractWeightedFilter
{
  public String nameFile = "soft.png";

  public String getFileName() {
    return  nameFile;
  }
  /**
   * A 3x3 grid of weights to use for the weighted filter algorithm.
   */
  private static final int[][] WEIGHTS = {{1, 2, 1}, {2, 4, 2}, {1, 2, 1}};

  /**
   * Constructs a new softening filter.
   */
  public SoftenFilter()
  {
    super("Soften", WEIGHTS);
  }


}
