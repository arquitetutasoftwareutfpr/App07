package edu.utfpr.cp.sa.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import edu.utfpr.cp.sa.business.CountryBusiness;
import edu.utfpr.cp.sa.business.CustomerBusiness;
import edu.utfpr.cp.sa.entity.Country;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CountryController {

    private CountryBusiness countryBusiness;
    private CustomerBusiness customerBusiness;

    @Autowired
    public CountryController(CountryBusiness countryBusiness, CustomerBusiness customerBusiness) {
        this.countryBusiness = countryBusiness;
        this.customerBusiness = customerBusiness;

    }

    @GetMapping("/country")
    public String view(Model model) {
        model.addAttribute("countryList", countryBusiness.read());

        return "country";
    }

    @PostMapping("/country/new")
    public String create(CountryDTO country) {

        try {
            Country c = new Country();
            c.setName(country.getName());
            c.setAcronym(country.getAcronym());
            c.setPhoneDigits(country.getPhoneDigits());

            countryBusiness.create(c);

        } catch (Exception e) {
            //TODO: handle exception
        }

        return "redirect:/country";
    }

    @GetMapping("/country/delete/{countryId}")
    public String delete(@PathVariable String countryId) {
        long idCountry = Long.parseLong(countryId);
        try {
            System.out.println(customerBusiness.read());
            countryBusiness.delete(idCountry, customerBusiness.read());
        } catch (Exception ex) {
            Logger.getLogger(CountryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/country";
    }
}
