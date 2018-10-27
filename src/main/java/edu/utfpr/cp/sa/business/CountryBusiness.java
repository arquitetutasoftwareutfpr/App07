package edu.utfpr.cp.sa.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.utfpr.cp.sa.dao.CountryDAO;
import edu.utfpr.cp.sa.entity.Country;
import edu.utfpr.cp.sa.entity.Customer;

@Component
public class CountryBusiness {

    private CountryDAO countryDAO;

    @Autowired
    public CountryBusiness (CountryDAO countryDAO) {
        this.countryDAO = countryDAO;
    }
    
    public boolean create(Country country) throws Exception {

        if (this.read().stream().map(Country::getName).anyMatch(e -> e.equals(country.getName()))) 
            throw new IllegalArgumentException("There already is a country with this name!");
        
        else {
            this.countryDAO.save(country);
            return true;
        }
            
    }

    public List<Country> read() {
        return this.countryDAO.findAll();
    }

    public boolean update(Country country) throws Exception {
        if (this.read().stream().map(Country::getName).anyMatch(e -> e.equals(country.getName()))) 
            throw new IllegalArgumentException("There already is a country with this name!");

        else {
            this.countryDAO.saveAndFlush(country);
            return true;
        }
    }

    public boolean delete(Long id , List<Customer> listCustomer) throws Exception {
        
        Country c = this.read().stream().filter(e -> e.getId() == id).findAny().get();
        
        if (listCustomer.stream().filter(e -> e.getCountry().getId() == c.getId()).findAny().get() != null) {
            
            throw new IllegalArgumentException("Error! Country is being used.");
            
        }else{
            
            this.countryDAO.delete(c);    
        
        }
        
        return true;
    }

}