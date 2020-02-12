package application.model;

import lombok.*;
import javax.validation.constraints.*;
import javax.persistence.*;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = User.GET_USERS, query = User.QUERY_GET_USERS),
})
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    public static final String GET_USERS = "User.get_users";
    public static final String QUERY_GET_USERS = "select u from User u";
//    public static final String ADD_ROLE = "User.add_role";
//    public static final String QUERY_ADD_ROLE = "insert into users_role (user_id,role_id)";


    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    public int id;

    @NotNull
    @NotEmpty
    @Column(name="firstname")
    private String firstname;

    @NotNull
    @Column(name="lastname")
    private String lastname;

    @NotNull
    @Column(name="email")
    private String email;

    @NotNull
    @Column(name="password")
    private String password;

    @JoinTable
    @ManyToMany
    private List<Role> roles;
}

// dodawanie ról i admina
//    INSERT INTO role (name) VALUES ('admin'), ('seller'), ('customer')
//        INSERT INTO users (id,firstname, lastname, email, password) VALUES (1,'imie', 'nazwisko', 'admin@admin.com', 'password')
//        insert into users_role (user_id, roles_id) values (1,1)
