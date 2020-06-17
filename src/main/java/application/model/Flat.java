package application.model;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.*;

@NamedQueries({
        //@NamedQuery(name = Flat.GET_FLAT, query = Flat.QUERY_GET_USERS),
})

@Data
@Entity
@Table(name = "flat")

public class Flat {
   // public static final String GET_USERS = "User.get_flats";
   // public static final String QUERY_GET_USERS = "select f from Flat f";


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

    @Column(name="floor",nullable = false)
    private int floor;

    @Column(name="sold",nullable = false)
    private boolean sold;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="userId")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId", referencedColumnName = "id")
    private Address address;
}
