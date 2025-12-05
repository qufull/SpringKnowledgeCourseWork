package com.example.curshach.repository;

import com.example.curshach.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VersionRepository extends JpaRepository<Version,Long> {
    Version findByNumber(String number);
}
