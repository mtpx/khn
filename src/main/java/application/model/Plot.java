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
@Table(name = "plot")

public class Plot {
//    public static final String GET_USERS = "User.get_flats";
//    public static final String QUERY_GET_USERS = "select f from Flat f";


    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    public int id;

    @Column(name="size",nullable = false)
    private int size;

    @Column(name="price",nullable = false)
    private int price;

    @NotEmpty(message="Provide type")
    @Column(name="type",nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name="userId", nullable=false)
    private User user;

    @OneToOne(cascade={CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "addressId", referencedColumnName = "id")
    private Address address;

    @OneToOne(cascade={CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "houseId", referencedColumnName = "id")
    private House house;
}
