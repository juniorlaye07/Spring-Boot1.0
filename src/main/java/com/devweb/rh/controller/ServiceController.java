package com.devweb.rh.controller;

import com.devweb.rh.modele.Employe;
import com.devweb.rh.modele.Service;
import com.devweb.rh.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/service")
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;
    @GetMapping(value = "/listeServices")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Service> serviceList(){
        return serviceRepository.findAll();
    }
    @PostMapping(value = "/addService",consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAuthority(('ROLE_ADMIN'))")
    public  Service addService(@RequestBody(required = false) Service se){
        return serviceRepository.save(se);
    }
}
