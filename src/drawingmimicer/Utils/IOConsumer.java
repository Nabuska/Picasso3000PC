package drawingmimicer.Utils;

import java.io.IOException;

/**
 * Created by WinNabuska on 1.11.2015.
 */
@FunctionalInterface
public interface IOConsumer<T>{
    void accept(T elem) throws IOException;
}
