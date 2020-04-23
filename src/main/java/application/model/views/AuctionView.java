package application.model.views;
import lombok.Data;
import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = AuctionView.GET_PROPERTIES, query = AuctionView.QUERY_GET_PROPERTIES),
        @NamedQuery(name = AuctionView.GET_PROPERTIES_BY_TYPE, query = AuctionView.QUERY_GET_PROPERTIES_BY_TYPE),
})

@Data
@Entity
@Table(name = "auction_view")

public class AuctionView {
    public static final String GET_PROPERTIES = "AuctionView.get_properties";
    public static final String QUERY_GET_PROPERTIES = "select a from AuctionView a";
    public static final String GET_PROPERTIES_BY_TYPE = "AuctionView.get_flats";
    public static final String QUERY_GET_PROPERTIES_BY_TYPE = "select a from AuctionView a where a.type= :type";

    @Id
    @Column(name = "row_number")
    public Integer id;

    @Column(name = "propertyId")
    public Integer propertyId;

    @Column(name = "userId")
    public Integer userId;

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
/*
create or replace view auction_view as
    with data as (
    select f.id as propertyId,f.userid, r."type", a.street, a.homenumber, a.localnumber, a.city, a.postcode, f.price, f."size", f.rooms, f.floor
    from address a, flat f, realassets r
    where f.addressid = a.id and a.realassetid =r.id
    union all
    select p.id as propertyId, p.userid, r."type", a.street, a.homenumber, a.localnumber, a.city, a.postcode, p.price, p."size", null as rooms, null as floor
    from address a, plot p, realassets r
    where p.addressid = a.id and a.realassetid =r.id
    union all
    select  h.id as propertyId, h.userid, r."type", a.street, a.homenumber, a.localnumber, a.city, a.postcode, h.price, h."size", h.rooms, null as floor
    from address a, house h, realassets r
    where h.addressid = a.id and a.realassetid =r.id )
select row_number() over(order by userid), * from data

*/
