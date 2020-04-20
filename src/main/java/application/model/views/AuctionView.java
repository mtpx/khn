package application.model.views;
import lombok.Data;
import org.hibernate.annotations.Immutable;
import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = AuctionView.GET_PROPERTIES, query = AuctionView.QUERY_GET_PROPERTIES),
})

@Data
@Entity
@Table(name = "auction_view")
@Immutable

public class AuctionView {
    public static final String GET_PROPERTIES = "AuctionView.get_properties";
    public static final String QUERY_GET_PROPERTIES = "select a from AuctionView a";

    @Id
    @Column(name = "id")
    public int id;

    @Column(name = "userId")
    public int userId;

    @Column(name="type")
    private String type;

    @Column(name="street")
    private String street;

    @Column(name = "homeNumber")
    public int homeNumber;

    @Column(name = "localNumber")
    public int localNumber;

    @Column(name="postCode")
    private String postCode;

    @Column(name="city")
    private String city;

    @Column(name = "price")
    public int price;

    @Column(name = "size")
    public int size;

    @Column(name = "floor")
    public int floor;

    @Column(name = "rooms")
    public int rooms;

}
/*
    create or replace VIEW auction_view AS
    SELECT ROW_NUMBER() OVER(ORDER BY f.userid) AS id, f.userid, r."type", a.street, a.homenumber, a.localnumber, a.city, a.postcode, f.price, f."size", f.rooms, f.floor
        FROM address a, flat f, plot p, house h, realassets r
        WHERE f.addressid = a.id AND a.realassetid =r.id

*/
