package ua.vitaminka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.vitaminka.model.Supplement;
import ua.vitaminka.repository.SupplementRepository;

import java.util.List;

@Service
public class SupplementService {

    @Autowired
    SupplementRepository supplementRepository;

    public List<Supplement> getAllSupplements() {
        return (List<Supplement>)supplementRepository.findAll();
    }
}
