package controller;

import dao.ICustomerDAO;
import model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private ICustomerDAO customerDAO;

    @GetMapping("/")
    public ModelAndView showList(){
        return new ModelAndView("index","customers",customerDAO.findAll());
    }

    @GetMapping("/create")
    public ModelAndView create(){
        return new ModelAndView("create","customer",new Customer());
    }

    @PostMapping("/create-customer")
    public ModelAndView create(@ModelAttribute Customer customer, RedirectAttributes redirectAttributes){
        customer.setId(Math.round(Math.random()*10000));
        customerDAO.add(customer);
        redirectAttributes.addFlashAttribute("createSuccess","Create new customer successful!");
        return new ModelAndView("redirect:/customer/create");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id){
        return new ModelAndView("edit","customer",customerDAO.findOne(id));
    }

    @PostMapping("/edit")
    public ModelAndView edit(@ModelAttribute Customer customer, RedirectAttributes redirectAttributes){
        customerDAO.save(customer);
        redirectAttributes.addFlashAttribute("editSuccess","Saved customer successfully!");
        return new ModelAndView("redirect:/customer/edit/"+customer.getId());
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id){
        customerDAO.delete(id);
        return new ModelAndView("redirect:/customer/");

    }

}
