package system.handling.human.user;

public class Staff extends User{

    public Staff(final String userId){
        super(userId);
    }

    public Staff(final String userId, final StaffProperties staffProperties){
        super(userId);
    }

}
