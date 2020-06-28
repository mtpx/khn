package application.dto;

import lombok.Data;

@Data
public class CreditDTO {
    private String name;
    private Long installment;
    private Integer numberOfInstallments;
    private Integer creditAmount;
}
