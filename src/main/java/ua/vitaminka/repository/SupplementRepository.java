package ua.vitaminka.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.vitaminka.model.Supplement;

@Repository
public interface SupplementRepository extends CrudRepository<Supplement, Long> {

}
