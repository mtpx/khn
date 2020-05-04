package application.controller.api;

import application.dto.TransactionDTO;
import application.model.Flat;
import application.model.House;
import application.model.Plot;
import application.service.facades.TransactionFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@Api(value = "KHN property API controller")
@RestController
@RequestMapping("/property/transaction")
public class APITransactionController {

    private TransactionFacade transactionFacade;

    public APITransactionController(TransactionFacade transactionFacade) {
        this.transactionFacade = transactionFacade;
    }

    @ApiOperation(value = "Plot transaction", response = Plot.class)
    @PostMapping(value = "/plot")
    public ResponseEntity<Object> plotTransaction(@RequestBody @Valid TransactionDTO transactionDTO){
        return transactionFacade.plotTransaction(transactionDTO);
    }

    @ApiOperation(value = "Flat transaction", response = Flat.class)
    @PostMapping(value = "/flat")
    public ResponseEntity<Object> flatTransaction(@RequestBody @Valid TransactionDTO transactionDTO){
        return transactionFacade.flatTransaction(transactionDTO);
    }

    @ApiOperation(value = "House transaction", response = House.class)
    @PostMapping(value = "/house")
    public ResponseEntity<Object> houseTransaction(@RequestBody @Valid TransactionDTO transactionDTO){
        return transactionFacade.houseTransaction(transactionDTO);
    }


}
