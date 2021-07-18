package com.sogong.sogong.repositories.district;

import com.sogong.sogong.model.district.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CityRepository extends JpaRepository<City, Long> {

    @Query("select c from City c where c.orgCode = ?1")
    City findByOrgCode(String code);

}
