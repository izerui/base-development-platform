package xxx.yyy.sys.security.model.listener;

import xxx.yyy.sys.security.model.User;

import javax.persistence.*;

/**
 * Created by serv on 14-6-3.
 */
public class UserListener {

    @PostLoad
    public void postLoad(User user) {
        System.out.println("In post load");
    }
    @PrePersist
    public void prePersist(User user) {
        System.out.println("In pre persist"+user.getId());
    }
    @PostPersist
    public void postPersist(User user) {
        System.out.println("In post persist"+user.getId());
    }
    @PreUpdate
    public void preUpdate(User user) {
        System.out.println("In pre update");

    }
    @PostUpdate
    public void postUpdate(User user) {
        System.out.println("In post update");
    }
    @PreRemove
    public void preRemove(User user) {
        System.out.println("In pre remove");
    }
    @PostRemove
    public void postRemove(User user) {
        System.out.println("In post remove");
    }

}
