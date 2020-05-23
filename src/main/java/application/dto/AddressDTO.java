package application.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private String city;
    private String street;
    private String houseNumber;
    private String localNumber;
    private String postCode;
}

