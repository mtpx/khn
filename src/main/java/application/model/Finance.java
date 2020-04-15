package application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NamedQueries({
//        @NamedQuery(name = Flat.GET_USERS, query = Flat.QUERY_GET_USERS),
})

@Data
@Entity
@Table(name = "finance")

public class Finance {
//    public static final String GET_USERS = "User.get_flats";
//    public static final String QUERY_GET_USERS = "select f from Flat f";


    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    public int id;

    @NotEmpty(message="Provide amount")
    @Column(name="amount",nullable = false)
    private int amount;

    @NotEmpty(message="Provide currency")
    @Column(name="currency",nullable = false)
    private String currency;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name="userId", nullable=false)
    private User user;
}
