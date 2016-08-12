package Command;

import Image.PixelImage;
import Strategy.Filter;

/**
 * Created by olgasaliy on 30.04.16.
 * Declares the interface for doing operations
 */
public abstract class Command {


    protected Filter filt;


    public Filter getFilt() {
        return filt;
    }

    public Command (Filter f) {
        this.filt = f;
    }



    public abstract void execute(PixelImage img);
}
