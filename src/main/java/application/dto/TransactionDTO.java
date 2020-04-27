package application.dto;

import lombok.Data;

@Data
public class TransactionDTO {
    private String propertyType;
    private int propertyId;
    private int customerId;
}

