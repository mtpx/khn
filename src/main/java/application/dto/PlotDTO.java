package application.dto;

import lombok.Data;

@Data
public class PlotDTO extends AddressDTO {
    private int userId;

    private int size;
    private int price;

    private String type;
}

