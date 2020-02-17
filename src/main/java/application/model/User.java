package application.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NamedQueries({
        @NamedQuery(name = User.GET_USERS, query = User.QUERY_GET_USERS),
        @NamedQuery(name = User.VERIFY_SELLER_CREDENTIALS, query = User.QUERY_VERIFY_SELLER_CREDENTIALS),
        @NamedQuery(name = User.GET_USER_ROLE, query = User.QUERY_GET_USER_ROLE),
        @NamedQuery(name = User.VERIFY_CUSTOMER_CREDENTIALS, query = User.QUERY_VERIFY_CUSTOMER_CREDENTIALS),
        @NamedQuery(name = User.GET_USER_ID_BY_EMAIL, query = User.QUERY_GET_USER_ID_BY_EMAIL)
})

//@NamedNativeQueries({
//        @NamedNativeQuery(name = User.ADD_CUSTOMER_ROLE, query = User.QUERY_ADD_CUSTOMER_ROLE),
//
//})
@Data
@Entity
@Table(name = "users")
public class User {
    public static final String GET_USERS = "User.get_users";
    public static final String QUERY_GET_USERS = "select u from User u";

    public static final String VERIFY_SELLER_CREDENTIALS = "User.verify_seller_credentials";
    public static final String QUERY_VERIFY_SELLER_CREDENTIALS = "select u from User u where u.email = :email and u.password = :password and u.roles.id in (1,2)";

    public static final String VERIFY_CUSTOMER_CREDENTIALS = "User.verify_customer_credentials";
    public static final String QUERY_VERIFY_CUSTOMER_CREDENTIALS = "select u from User u where u.email = :email and u.password = :password and u.roles.id in (1,3)";

    public static final String GET_USER_ROLE = "User.get_role";
    public static final String QUERY_GET_USER_ROLE = "select u from User u where u.id= :id";

    public static final String GET_USER_ID_BY_EMAIL = "User.get_userId_by_mail";
    public static final String QUERY_GET_USER_ID_BY_EMAIL = "select u from User u where u.email= :email";

//    public static final String ADD_CUSTOMER_ROLE = "User.get_userId_by_mail";
//    public static final String QUERY_ADD_CUSTOMER_ROLE = "insert into ";

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    public int id;

    //K: Ogólnie nazwy piszemy camelCase czyli powinno być: firstName

    @NotNull
    @Column(name="firstname",nullable = false)
    private String firstname;

    @NotNull
    @Column(name="lastname",nullable = false)
    private String lastname;

    @NotNull
    @Column(name="email",unique = true,nullable = false)
    private String email;

    @NotNull
    @Column(name="password",nullable = false)
    private String password;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name="user_roles")
    private Role roles;
}

// dodawanie ról i admina
//    INSERT INTO role (name) VALUES ('admin'), ('seller'), ('customer')
//        INSERT INTO users (id,firstname, lastname, email, password) VALUES (1,'imie', 'nazwisko', 'admin@admin.com', 'password')
//        insert into users_role (user_id, roles_id) values (1,1)
