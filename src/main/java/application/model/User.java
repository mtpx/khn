package application.model;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = User.GET_USERS, query = User.QUERY_GET_USERS),
        @NamedQuery(name = User.VERIFY_CREDENTIALS, query = User.QUERY_VERIFY_CREDENTIALS),
        @NamedQuery(name = User.GET_USER_ROLES, query = User.QUERY_GET_USER_ROLES),
        @NamedQuery(name = User.GET_USER_ID_BY_EMAIL, query = User.QUERY_GET_USER_ID_BY_EMAIL),
        @NamedQuery(name = User.GET_USER_ROLES_BY_EMAIL, query = User.QUERY_GET_USER_ROLES_BY_EMAIL)
})

@Data
@Entity
@Table(name = "users")

public class User {
    public static final String GET_USERS = "User.get_users";
    public static final String QUERY_GET_USERS = "select u from User u";

    public static final String VERIFY_CREDENTIALS = "User.verify_credentials";
    public static final String QUERY_VERIFY_CREDENTIALS = "select u from User u where u.email = :email and u.password = :password";

    public static final String GET_USER_ROLES = "User.get_roles";
    public static final String QUERY_GET_USER_ROLES = "select u.roles from User u where u.id= :id";

    public static final String GET_USER_ID_BY_EMAIL = "User.get_userId_by_mail";
    public static final String QUERY_GET_USER_ID_BY_EMAIL = "select u from User u where u.email= :email";

    public static final String GET_USER_ROLES_BY_EMAIL = "User.get_roles_by_email" ;
    public static final String QUERY_GET_USER_ROLES_BY_EMAIL = "select u.roles from User u where u.email= :email" ;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    public int id;

    @NotEmpty(message="Provide firstname")
    @Column(name="firstname",nullable = false)
    private String firstname;

    @NotEmpty(message="Provide lastname")
    @Column(name="lastname",nullable = false)
    private String lastname;

    //@Email(message = "Provide valid email")
    @NotEmpty(message="Provide email")
    @Column(name="email",unique = true,nullable = false)
    private String email;

    @NotEmpty(message="Provide password")
    @Size(min=4,message = "Password: minimum 4 chars")
    @Column(name="password",nullable = false)
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="user_roles")
    private List<Role> roles;
}

// dodawanie ról i admina
//    INSERT INTO role (name) VALUES ('admin'), ('seller'), ('customer')
//        INSERT INTO users (id,firstname, lastname, email, password) VALUES (1,'imie', 'nazwisko', 'admin@admin.com', 'password')
//        insert into users_role (user_id, roles_id) values (1,1)
