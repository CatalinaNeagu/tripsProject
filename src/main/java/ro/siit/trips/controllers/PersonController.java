package ro.siit.trips.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.siit.trips.models.Person;
import ro.siit.trips.repositiories.PersonRepository;

import javax.validation.Valid;


@Controller
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping(value= "/person")
    public String person(Model model){
        Person person = new Person();
        model.addAttribute("person", person);
        return "addperson";
    }

    @RequestMapping(value="/addperson", method = RequestMethod.POST)
    public String addPerson(@Valid @ModelAttribute Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/addperson";
        }
        String encodedPassword = new BCryptPasswordEncoder().encode(person.getPassword());
        person.setPassword(encodedPassword);

        Person saved = personRepository.save(person);
        return "login";
    }
    @RequestMapping(value = "/personToUpdate", method = RequestMethod.POST)
    public ModelAndView edit(@ModelAttribute Person person) {
        ModelAndView model = new ModelAndView("updatePerson");
        Person loggedPerson = personRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        loggedPerson.getId();
        personRepository.save(loggedPerson);
        model.addObject("person", loggedPerson);
        return model;
    }

    @RequestMapping(value="/updatedPerson", method = RequestMethod.POST)
    public ModelAndView editPerson(@Valid @ModelAttribute Person person, BindingResult bindingResult) {
        ModelAndView model = new ModelAndView("login");
        ModelAndView model2 = new ModelAndView("updatePerson");
        if (bindingResult.hasErrors()) {
            return model2;
        }

        Person loggedPerson = personRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        loggedPerson.setFirstName(person.getFirstName());
        loggedPerson.setLastName(person.getLastName());
        loggedPerson.setAddress(person.getAddress());
        loggedPerson.setCity(person.getCity());
        loggedPerson.setPhone(person.getPhone());
        String encodedPassword = new BCryptPasswordEncoder().encode(person.getPassword());
        person.setPassword(encodedPassword);
        loggedPerson.setPassword(person.getPassword());
        personRepository.save(loggedPerson);

        return model;
    }
    }

