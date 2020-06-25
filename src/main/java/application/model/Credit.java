package application.model;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = Credit.GET_USERS, query = Credit.QUERY_GET_USERS),
        @NamedQuery(name = Credit.VERIFY_CREDENTIALS, query = Credit.QUERY_VERIFY_CREDENTIALS),
        @NamedQuery(name = Credit.GET_USER_BY_EMAIL, query = Credit.QUERY_GET_USER_BY_EMAIL),
})

@Getter
@Setter
@Entity
@Table(name = "credit")

public class Credit {
    public static final String GET_USERS = "User.get_users";
    public static final String QUERY_GET_USERS = "select u from User u";

    public static final String VERIFY_CREDENTIALS = "User.verify_credentials";
    public static final String QUERY_VERIFY_CREDENTIALS = "select u from User u where u.email = :email and u.password = :password";

    public static final String GET_USER_BY_EMAIL = "User.get_user_by_mail";
    public static final String QUERY_GET_USER_BY_EMAIL = "select u from User u where u.email= :email";


    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    public int id;

    @NotEmpty(message="Provide name")
    @Column(name="name",nullable = false)
    private String name;

    @NotEmpty(message="Provide intallment")
    @Column(name="name",nullable = false)
    private Integer installment;



}

