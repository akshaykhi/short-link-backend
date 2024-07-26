package com.example.Hackathon.Hackathon.repository;

import com.example.Hackathon.Hackathon.entity.GoLinkDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GoLinkRepository extends JpaRepository<GoLinkDetails, Integer> {
    @Query(value = "select destination_url from golink_details where alias = ?1 and is_active = ?2",nativeQuery = true)
    String findByAlias(String alias, boolean isActive);
}
