package ro.siit.trips.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ro.siit.trips.models.Person;
import ro.siit.trips.models.Trip;
import ro.siit.trips.repositiories.PersonRepository;
import ro.siit.trips.repositiories.TripRepository;
import ro.siit.trips.services.FileService;
import ro.siit.trips.services.TripService;

import javax.validation.Valid;
import java.util.UUID;


@Controller
public class TripController {

    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private FileService fileService;

    @Autowired
    private TripService tripService;


    @RequestMapping(value = "/trip")
    public String journey(Model model) {
        Trip trip = new Trip();
        model.addAttribute("trip", trip);
        return "addtrip";
    }

    @RequestMapping(value = "/addtrip", method = RequestMethod.POST)
    public ModelAndView addTrip( @RequestParam("file") MultipartFile file, @RequestParam("file2") MultipartFile file2, @Valid @ModelAttribute Trip trip, BindingResult bindingResult) {
        ModelAndView model = new ModelAndView("successtrip");
        ModelAndView model2 = new ModelAndView("/addtrip");

        if (bindingResult.hasErrors()) {
            return model2;
        }
        Person loggedPerson = personRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());


        trip.setPerson(loggedPerson);
        Trip savedTrip = tripRepository.save(trip);


        String uuid1 = UUID.randomUUID().toString();
        savedTrip.setPhoto1(uuid1);
        fileService.store(savedTrip.getPhoto1(), file);
        model.addObject("uuid1", savedTrip.getPhoto1());

        String uuid2 = UUID.randomUUID().toString();
        savedTrip.setPhoto2(uuid2);
        fileService.store(savedTrip.getPhoto2(), file2);
        model.addObject("uuid2", savedTrip.getPhoto2());
        tripRepository.save(savedTrip);
        return model;
    }

    @RequestMapping(path = "/trips")
    public ModelAndView getTrips(@RequestParam(name = "id", required = false) Long id) {
        ModelAndView model = new ModelAndView("tripTemplate");
        ModelAndView model2 = new ModelAndView("redirect:/trip");
        Person loggedPerson = personRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        if (loggedPerson.getTrips().size() == 0){
            return model2;
        }
        Trip currentTrip = null;
        if (id != null) {
            for (Trip trip : loggedPerson.getTrips()) {
                if (trip.getId().equals(id)) {
                    currentTrip = trip;
                    System.out.println("Found:" + trip);
                    break;
                }
            }
        }
        if (currentTrip == null) {
            currentTrip = loggedPerson.getTrips().iterator().next();

        }
        model.addObject("trips", loggedPerson.getTrips());
        model.addObject("selectedId", currentTrip.getId());
        model.addObject("currentTrip", currentTrip);
        return model;

    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView delete(@ModelAttribute Trip trip, @RequestParam(name = "id", required = false) Long id) {
        ModelAndView model = new ModelAndView("tripTemplate");
        tripRepository.deleteById(trip.getId());
        Person loggedPerson = personRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println(loggedPerson.getTrips());
        Trip currentTrip = null;
        if (id != null) {
            for (Trip trip2 : loggedPerson.getTrips()) {
                if (trip.getId().equals(id)) {
                    currentTrip = trip;
                    System.out.println("Found:" + trip);
                    break;
                }
            }
        }
        if (currentTrip == null) {
            currentTrip = loggedPerson.getTrips().iterator().next();

        }

        model.addObject("trips", loggedPerson.getTrips());
        model.addObject("selectedId", currentTrip.getId());
        model.addObject("currentTrip", currentTrip);
        return model;
    }


    @RequestMapping(value = "/tripToUpdate", method = RequestMethod.POST)
    public ModelAndView findingTripForEdit(@ModelAttribute Trip trip) {
        ModelAndView model = new ModelAndView("/updateTrip");
        Trip modifiedTrip = tripRepository.findById(trip.getId()).get();
        tripRepository.save(modifiedTrip);
        model.addObject("trip", modifiedTrip);
        return model;
    }

    @RequestMapping(value = "/updatedTrip", method = RequestMethod.POST)
    public ModelAndView editingTrip(@ModelAttribute Trip trip, @RequestParam(name = "id", required = false) Long id,
                                    @RequestParam("file") MultipartFile file,
                                    @RequestParam("file2") MultipartFile file2) {
        ModelAndView model = new ModelAndView("tripTemplate");
        Person loggedPerson = personRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        trip.setPerson(loggedPerson);
        Trip modifiedTrip = tripRepository.findById(trip.getId()).get();


        modifiedTrip.setTripName(trip.getTripName());

        modifiedTrip.setImpressions(trip.getImpressions());
        modifiedTrip.setStartDate(trip.getStartDate());
        modifiedTrip.setEndDate(trip.getEndDate());
        modifiedTrip.setLocation(trip.getLocation());
        if (file.isEmpty()) {
            modifiedTrip.setPhoto1(modifiedTrip.getPhoto1());
        } else {
            modifiedTrip.setPhoto1(UUID.randomUUID().toString());
            modifiedTrip.setPhoto1(UUID.randomUUID().toString());
            fileService.store(modifiedTrip.getPhoto1(), file);
        }
        if (file2.isEmpty()) {
            modifiedTrip.setPhoto2(modifiedTrip.getPhoto2());
        } else {
            modifiedTrip.setPhoto2(UUID.randomUUID().toString());
            fileService.store(modifiedTrip.getPhoto2(), file2);
        }
        Trip updatedTrip = tripRepository.save(modifiedTrip);
        Trip currentTrip = null;
        if (id != null) {
            for (Trip trip2 : loggedPerson.getTrips()) {
                if (trip.getId().equals(id)) {
                    currentTrip = trip;
                    System.out.println("Found:" + trip);
                    break;
                }
            }
        }
        if (currentTrip == null) {
            currentTrip = loggedPerson.getTrips().iterator().next();

        }
        model.addObject("trips", loggedPerson.getTrips());
        model.addObject("selectedId", currentTrip.getId());
        model.addObject("currentTrip", currentTrip);
        return model;
    }


}





