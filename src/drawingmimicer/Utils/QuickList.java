package drawingmimicer.Utils;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by WinNabuska on 27.10.2015.
 */
public class QuickList<E> extends ArrayList<E>{

    public Optional<E> last(){
        if(this.isEmpty())
           return Optional.empty();
        else
            return Optional.of(this.get(this.size()-1));
    }

    public Optional<E> first(){
        if(this.isEmpty())
            return Optional.empty();
        else
            return Optional.of(this.get(0));
    }

    public QuickList<E> dropFirst(){
        if(!this.isEmpty())
            remove(0);
        return this;
    }

    public QuickList<E> dropLast(){
        if(size()>0)
            remove(size()-1);
        return this;
    }

    public boolean notEmpty(){
        return size()>0;
    }

}
