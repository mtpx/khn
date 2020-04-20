package application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@NamedQueries({
        @NamedQuery(name = Address.VERIFY_ADDRESS, query = Address.QUERY_VERIFY_ADDRESS),
})

@Data
@Entity
@Table(name = "address")

public class Address {
    public static final String VERIFY_ADDRESS = "Address.verify_address";
    public static final String QUERY_VERIFY_ADDRESS = "select a from Address a where a.city = :city and a.homeNumber = :homeNumber and a.localNumber = :localNumber and a.postCode = :postCode and a.street= :street";


    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    public int id;

    //@NotEmpty(message="Provide city")
    @Column(name="city",nullable = false)
    private String city;

    //@NotEmpty(message="Provide street")
    @Column(name="street",nullable = false)
    private String street;

    @Column(name="homeNumber",nullable = false)
    private int homeNumber;

    @Column(name="localNumber",nullable = false)
    private int localNumber;

    @Column(name="postCode",nullable = false)
    private String postCode;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "realAssetId", referencedColumnName = "id")
    private RealAssets realAssets;
}

