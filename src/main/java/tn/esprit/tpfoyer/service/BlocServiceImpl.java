package tn.esprit.tpfoyer.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.repository.BlocRepository;


@Service
@AllArgsConstructor
@Slf4j  // Simple Loggining Façade For Java
public class BlocServiceImpl  implements IBlocService {


    BlocRepository blocRepository;



}
