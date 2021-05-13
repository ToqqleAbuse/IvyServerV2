package system.handling.human.permissions;

import system.database.table.DefaultEntries;
import system.database.table.Tables;

import java.util.*;

public enum Permission implements DefaultEntries{

    IVY_TEAM__IVY_USB_LOGIN("E4166E6EA1686A699058A73EFC3E7C82>>dq6ylt85Dta6W9", null, false, ""),
    IVY_TEAM__IVY_USB_FORCE_LOGIN("8C2E376AFFAD79997D2781621688AF4C>>2KHRU9ZY3OmxrQ", IVY_TEAM__IVY_USB_LOGIN, false, ""),
    IVY_TEAM__IVY_USB_SKIP_LOGIN("225C3DD3D1682D0E4DAE78E2C08D88A6>>7LRQ7nnLujQz32", IVY_TEAM__IVY_USB_LOGIN, false, "");

    final String permissionId;
    final Permission permissionParent;
    final boolean permissionMainValue;
    final String permissionDescription;

    Permission(final String permissionId, final Permission permissionParent, final boolean permissionMainValue, final String permissionDescription){
        this.permissionId = permissionId;
        this.permissionParent = permissionParent;
        this.permissionMainValue = permissionMainValue;
        this.permissionDescription = permissionDescription;
    }

    public static Map<String, Permission> permissions = new HashMap<>();

    static{
        Arrays.stream(Permission.values()).forEach(x -> Permission.permissions.put(x.getPermissionId(), x));
    }

    public static Permission getPermissionsById(final String permissionId){
        return Permission.permissions.get(permissionId);
    }

    public String getPermissionId(){
        return this.permissionId;
    }

    public boolean getPermissionMainValue(){
        return this.permissionMainValue;
    }

    public String getPermissionDescription(){
        return this.permissionDescription;
    }

    public Permission getParent(){
        return this.permissionParent;
    }

    @Override
    public Tables getTable(){
        return Tables.IVY_PERMISSION;
    }

    @Override
    public List<String[]> getValues(){
        List<String[]> forReturn = new ArrayList<>();

        for(Permission permission : Permission.values()){

            String parentPermissionId;
            if(permission.getParent() == null){
                parentPermissionId = "NULL";
            }else{
                parentPermissionId = permission.getParent().getPermissionId();
            }

            String[] values = {
                    permission.getPermissionId(), parentPermissionId, String.valueOf(permission), (permission.getPermissionMainValue() ? "1" : "0"), permission.getPermissionDescription()
            };

            forReturn.add(values);
        }

        return forReturn;
    }
}

