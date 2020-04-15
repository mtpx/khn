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
@Table(name = "house")

public class House {
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

    @Column(name="rooms",nullable = false)
    private int rooms;

    @ManyToOne
    @JoinColumn(name="userId", nullable=false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId", referencedColumnName = "id")
    private Address address;

    @JsonIgnore
    @OneToOne(mappedBy = "address")
    private Plot plot;

}
