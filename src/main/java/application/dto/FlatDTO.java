package application.dto;

import lombok.Data;

@Data
public class FlatDTO {
    private String city;
    private String street;
    private String houseNumber;
    private String localNumber;
    private String postCode;

    private int userId;

    private int size;
    private int price;

    private int floor;
    private int rooms;
}

