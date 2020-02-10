package application.model;

import lombok.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    @OneToMany
    private List<Role> roles;
}

//    INSERT INTO role (name) VALUES ('admin'), ('seller'), ('customer')
//    INSERT INTO users (firstname, lastname, email, password) VALUES ('imie', 'nazwisko', 'admin@admin.com', 'password')
