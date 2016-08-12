package filters;


/**
 * Created by olgasaliy on 01.05.16.
 * The filter that highlights edges in the image.
 */
public class EdgeHighlightFilter extends AbstractWeightedFilter
{
  public String nameFile = "highlight.png";

  public String getFileName() {
    return  nameFile;
  }
  /**
   * A 3x3 matrix of weights to use in the filtering algorithm.
   */
  private static final int[][] WEIGHTS = {{-1, -1, -1}, {-1, 9, -1}, {-1, -1, -1}};

  /**
   * Constructs a new edge highlighting filter.
   */
  public EdgeHighlightFilter()
  {
    super("Edge Highlight", WEIGHTS);
  }


}
