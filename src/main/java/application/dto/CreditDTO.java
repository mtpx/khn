package application.dto;

import lombok.Data;

@Data
public class CreditDTO {
    private String firstName;
    private String lastName;
    private Integer installment;
    private String houseNumber;
    private Integer quantity;
    private String localNumber;
    private String postCode;
    private Integer amountOfInstallments;
}
