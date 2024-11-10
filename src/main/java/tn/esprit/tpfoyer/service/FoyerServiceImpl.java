package tn.esprit.tpfoyer.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.repository.FoyerRepository;

@Service
@AllArgsConstructor
public class FoyerServiceImpl implements IFoyerService {

    FoyerRepository foyerRepository;

}
