package ro.siit.trips.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.siit.trips.models.Trip;
import ro.siit.trips.repositiories.TripRepository;

@Service
public class TripService {
    @Autowired
    TripRepository tripRepository;

    public Iterable<Trip> trips() {
        return tripRepository.findAll();
    }
}
