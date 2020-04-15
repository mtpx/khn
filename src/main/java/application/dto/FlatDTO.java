package application.dto;

import lombok.Data;

@Data
public class FlatDTO {
    private String city;
    private String street;
    private int houseNumber;
    private int localNumber;
    private String postCode;

    private int realAssetId;
    private int userId;

    private int size;
    private int price;

    private int floor;
    private int rooms;
}

