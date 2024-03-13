package viamao.api.domain.trip;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TripRepository extends JpaRepository<Trip, Long> {
	
	@Query("SELECT t from Trip t ORDER BY t.startDate ASC")
	Page<Trip> findAll(Pageable pagination);
	
}
