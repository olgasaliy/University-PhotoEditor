package Strategy;

import Image.PixelImage;

/**
 * Created by olgasaliy on 05.05.16.
 * The class that lets strategy accessing its data.
 * Context uses Filter to call the algorithm defined by a ConcreteStrategy.
 */
public class Context {
    private Filter strategy;

    public Filter getStrategy() {
        return strategy;
    }

    public void setStrategy(Filter str) {
        this.strategy = str;
    }

    /**
     * Method to execute concrete algorithm
     * @param image - the source object
     */
    public void execute (PixelImage image) {
        this.strategy.filter(image);
    }
}
