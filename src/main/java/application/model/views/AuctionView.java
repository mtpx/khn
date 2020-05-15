package application.model.views;
import lombok.Data;
import org.hibernate.annotations.Synchronize;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = AuctionView.GET_PROPERTIES, query = AuctionView.QUERY_GET_PROPERTIES),
        @NamedQuery(name = AuctionView.GET_PROPERTIES_BY_TYPE, query = AuctionView.QUERY_GET_PROPERTIES_BY_TYPE),
})

@Data
@Entity
@Synchronize("Address")
@Table(name = "auction_view")

public class AuctionView {
    public static final String GET_PROPERTIES = "AuctionView.get_properties";
    public static final String QUERY_GET_PROPERTIES = "select a from AuctionView a";
    public static final String GET_PROPERTIES_BY_TYPE = "AuctionView.get_flats";
    public static final String QUERY_GET_PROPERTIES_BY_TYPE = "select a from AuctionView a where a.type= :type";

    @Id
    @Column(name = "propertyId")
    public Integer propertyId;

    @Column(name = "userId")
    public Integer userId;

    @Column(name = "roles_id")
    public Integer rolesId;

    @Column(name="type")
    private String type;

    @Column(name="street")
    private String street;

    @Column(name = "homeNumber")
    public Integer homeNumber;

    @Column(name = "localNumber")
    public Integer localNumber;

    @Column(name="postCode")
    private String postCode;

    @Column(name="city")
    private String city;

    @Column(name = "price")
    public Integer price;

    @Column(name = "size")
    public Integer size;

    @Column(name = "floor")
    public Integer floor;

    @Column(name = "rooms")
    public Integer rooms;

}
