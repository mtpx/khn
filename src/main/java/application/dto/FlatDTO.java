package application.dto;

import lombok.Data;

@Data
public class FlatDTO extends AddressDTO {
    private int userId;

    private int size;
    private int price;

    private int floor;
    private int rooms;
}

