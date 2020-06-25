package application.dao;

import application.model.Credit;

public interface CreditDAO {
    Credit save(Credit credit);
    Credit findById(int id);
}
