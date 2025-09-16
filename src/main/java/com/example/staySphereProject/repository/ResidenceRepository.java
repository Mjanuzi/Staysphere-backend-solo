package com.example.staySphereProject.repository;

import com.example.staySphereProject.models.Residence;
import com.example.staySphereProject.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ResidenceRepository extends MongoRepository<Residence, String> {
    List<Residence> findByHost(User host);
    List<Residence> findByHostId(String hostId);
}
