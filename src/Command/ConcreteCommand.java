package Command;

import Image.PixelImage;
import Strategy.Filter;

/**
 * Created by olgasaliy on 30.04.16.
 */
public class ConcreteCommand extends Command {

    public ConcreteCommand (Filter f){
        super(f);
    }

    @Override
    public void execute(PixelImage img) {
        filt.filter(img);
    }
}
