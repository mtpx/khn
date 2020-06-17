package application.model.views;
import lombok.Data;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = PropertyView.GET_PROPERTIES_FOR_SALE, query = PropertyView.QUERY_GET_PROPERTIES_FOR_SALE),
        @NamedQuery(name = PropertyView.GET_PROPERTIES_FOR_SALE_BY_TYPE, query = PropertyView.QUERY_GET_PROPERTIES_FOR_SALE_BY_TYPE),
        @NamedQuery(name = PropertyView.GET_PROPERTIES_FOR_SALE_BY_USERID, query = PropertyView.QUERY_GET_PROPERTIES_FOR_SALE_BY_USERID),
        @NamedQuery(name = PropertyView.GET_PROPERTIES_FOR_SALE_BY_EMAIL, query = PropertyView.QUERY_GET_PROPERTIES_FOR_SALE_BY_EMAIL),
        @NamedQuery(name = PropertyView.GET_SOLD_PROPERTIES_BY_EMAIL, query = PropertyView.QUERY_GET_SOLD_PROPERTIES_BY_EMAIL)

})

@Data
@Entity
@Table(name = "property_view")

public class PropertyView {
    public static final String GET_PROPERTIES_FOR_SALE = "PropertyView.get_properties_for_sale";
    public static final String QUERY_GET_PROPERTIES_FOR_SALE = "select p from PropertyView p where p.sold=false";
    public static final String GET_PROPERTIES_FOR_SALE_BY_TYPE = "PropertyView.get_properties_for_sale_by_type";
    public static final String QUERY_GET_PROPERTIES_FOR_SALE_BY_TYPE = "select p from PropertyView p where p.type= :type and p.sold=false";
    public static final String GET_PROPERTIES_FOR_SALE_BY_USERID = "PropertyView.get_properties_for_sale_by_userId";
    public static final String QUERY_GET_PROPERTIES_FOR_SALE_BY_USERID = "select p from PropertyView p where p.userId= :userId and p.sold=false";
    public static final String GET_PROPERTIES_FOR_SALE_BY_EMAIL = "PropertyView.get_properties_for_sale_by_email";
    public static final String QUERY_GET_PROPERTIES_FOR_SALE_BY_EMAIL = "select p from PropertyView p where p.email= :email and p.sold=false ";

    public static final String GET_SOLD_PROPERTIES_BY_EMAIL = "PropertyView.get_sold_properties_by_email";
    public static final String QUERY_GET_SOLD_PROPERTIES_BY_EMAIL = "select p from PropertyView p where p.email= :email and p.sold=true ";


    @Id
    @Column(name = "propertyId")
    public Integer propertyId;

    @Column(name = "userId")
    public Integer userId;

    @Column(name = "email")
    public String email;

    @Column(name="type")
    private String type;

    @Column(name="street")
    private String street;

    @Column(name = "homeNumber")
    public String homeNumber;

    @Column(name = "localNumber")
    public String localNumber;

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

    @Column(name = "sold")
    public Boolean sold;

}
