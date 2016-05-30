package Bot;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerContextProxy implements InvocationHandler {
    private Object target;

    public static Logger logger = Logger.getLogger(ControllerContextProxy.class.getName());


    public ControllerContextProxy (Object target){
        this.target = target;
        logger.info("ControllerContextProxy init");
    }
    
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;
        long t1 = System.nanoTime();
        result = method.invoke(target, args);
        long t2 = System.nanoTime();

        if((t2-t1 >= 0))
        {
            logger.log(Level.INFO, "It takes " + (t2 - t1) + " ns " + method.getName());
        }
        return result;
    }

    public static Object newInstance (Object target){
        ClassLoader loader = target.getClass().getClassLoader();
        Class[] classes = target.getClass().getInterfaces();
        return Proxy.newProxyInstance(loader, classes, new ControllerContextProxy(target));
    }
}
