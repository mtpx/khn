package application.dto;

import lombok.Data;

@Data
public class HouseDTO extends AddressDTO {
    private int userId;

    private int size;
    private int price;

    private int rooms;
}

