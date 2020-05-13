package application.service;

import application.dto.FlatDTO;
import application.dto.HouseDTO;
import application.dto.PlotDTO;
import application.model.Address;
import java.util.List;

public interface AddressService {

    //Chyba bym wywaliła stąd te 3 tworzenia obiektów i wrzuciła je do poszczególnych serwisów
    // addHouseService
    //addFlatService
    //addPlotService


    // Może żeby nie było duplikatów metod to trzeba zrobić AddressObject i wtędy będzie jedna metoda - trzeba tutaj się zastanowić, która opcja lepsza

    Address createAddressObject(FlatDTO flatDTO);
    Address createAddressObject(HouseDTO houseDTO);
    Address createAddressObject(PlotDTO plotDTO);
    List<Address> getAddress(Address address);
}
