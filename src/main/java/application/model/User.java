package application.model;


import lombok.*;
import javax.validation.constraints.NotNull;
import javax.persistence.*;


//@NamedQueries({
//        @NamedQuery(name = User.GET_USER_BY_ID, query = User.QUERY_GET_USER_BY_ID),
//        @NamedQuery(name = User.GET_USERS, query = User.QUERY_GET_USERS),
//        @NamedQuery(name = User.CHECK_USER, query = User.QUERY_CHECK_USER),
//})
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    public int id;

    @NotNull
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
}
