package application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NamedQueries({
        @NamedQuery(name = Finance.GET_BY_USER_ID, query = Finance.QUERY_GET_BY_USER_ID),
})

@Data
@Entity
@Table(name = "finance")

public class Finance {
    public static final String GET_BY_USER_ID = "Finance.get_by_UserId";
    public static final String QUERY_GET_BY_USER_ID = "select f from Finance f where f.user.id= :userId";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer id;

    @NotNull(message="Provide amount")
    @Column(name="amount",nullable = false)
    private Integer amount;

    @NotEmpty(message="Provide currency")
    @Column(name="currency",nullable = false)
    private String currency;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name="userId", nullable=false)
    private User user;
}
