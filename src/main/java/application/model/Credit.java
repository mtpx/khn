package application.model;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@NamedQueries({
//        @NamedQuery(name = Credit.GET_USERS, query = Credit.QUERY_GET_USERS)
})

@Getter
@Setter
@Entity
@Table(name = "credit")

public class Credit {
//    public static final String GET_USERS = "User.get_users";
//    public static final String QUERY_GET_USERS = "select u from User u";


    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    public int id;

    @NotEmpty(message="Provide name")
    @Column(name="name",nullable = false)
    private String name;

    @NotEmpty(message="Provide intallment")
    @Column(name="installment",nullable = false)
    private Integer installment;



}

