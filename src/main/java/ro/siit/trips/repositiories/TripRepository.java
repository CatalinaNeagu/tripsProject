package ro.siit.trips.repositiories;

        import org.springframework.data.repository.CrudRepository;
        import org.springframework.stereotype.Repository;
        import ro.siit.trips.models.Trip;


@Repository
public interface TripRepository extends CrudRepository<Trip, Long> {

}
