package system.handling.products;

import system.database.table.DefaultEntries;
import system.database.table.Tables;
import system.io.filesystem.SystemPaths;
import system.startup.StartupHandler;
import system.tools.skills.TimeManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProductsSystem implements StartupHandler, DefaultEntries{

    @Override
    public void __init__() throws Exception{
        createProductsFiles();
    }

    @Override
    public boolean async(){
        return true;
    }

    @Override
    public boolean systemRequirement(){
        return true;
    }

    @Override
    public String systemName(){
        return "Products-System";
    }

    public void createProductsFiles(){

        Products.productsList.forEach(x -> {
            File productsFolder = SystemPaths.PRODUCTS.getFileName();
            File productFolder = new File(productsFolder.getPath() + "/" + x.getName());

            if(productFolder.mkdir()){

            }

        });

    }

    @Override
    public Tables getTable(){
        return Tables.IVY_PRODUCT;
    }

    @Override
    public List<String[]> getValues(){
        List<String[]> forReturn = new ArrayList<>();

        Products.productsList.forEach(x -> {
            String[] values = {
                    x.getProductId(), x.getName(), "1", String.valueOf(x.getPort()), new TimeManager(System.currentTimeMillis()).getDateFormat("YYYY-MM-dd HH:mm:ss"),
            };
            forReturn.add(values);
        });

        return forReturn;
    }
}
