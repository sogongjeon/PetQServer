package com.sogong.sogong.repositories.animal;

import com.sogong.sogong.model.animal.AnimalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnimalDataRepository extends JpaRepository<AnimalData, Long>, AnimalDataRepositoryCustom {
    @Query("select ad from AnimalData ad where ad.desertionNo = ?1")
    AnimalData findByDesertionNo(String desertionNo);

}
