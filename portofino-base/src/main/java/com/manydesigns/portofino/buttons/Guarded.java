package com.manydesigns.portofino.buttons;

import javax.ws.rs.core.Response;
import java.lang.reflect.Method;

/**
 * Created by alessio on 22/12/16.
 */
public interface Guarded {
    
    Response guardsFailed(Method handler);
    
}
