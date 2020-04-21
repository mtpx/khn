package application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NamedQueries({
        @NamedQuery(name = House.GET_BY_ADDRESSID, query = House.QUERY_GET_BY_ADDRESSID),
})

@Data
@Entity
@Table(name = "house")

public class House {
    public static final String GET_BY_ADDRESSID = "House.get_by_addressId";
    public static final String QUERY_GET_BY_ADDRESSID = "select h from House h where h.address.id= :addressId";


    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
