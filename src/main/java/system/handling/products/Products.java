package system.handling.products;

import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class Products{

    public static List<Products> productsList = new ArrayList<>();

    static{
        Reflections reflections = new Reflections("system");
        Set<Class<? extends Products>> classes = reflections.getSubTypesOf(Products.class);

        classes.forEach(singleClass -> {
            try{
                Products product = singleClass.newInstance();
                productsList.add(product);
            }catch(Exception exception){
                exception.printStackTrace();
            }
        });
    }

    public abstract int getPort();

    public abstract String getName();

    public abstract String getProductId();

}
