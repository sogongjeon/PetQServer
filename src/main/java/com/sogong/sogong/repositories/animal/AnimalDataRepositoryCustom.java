package com.sogong.sogong.repositories.animal;

import com.sogong.sogong.entity.common.Criteria;
import com.sogong.sogong.model.animal.AnimalData;
//import com.sogong.sogong.entity.animal.AnimalSearchCriteria;
import com.sogong.sogong.entity.animal.AnimalSearchCriteria;
import org.springframework.data.domain.Page;

public interface AnimalDataRepositoryCustom {
    Page<AnimalData> listByCriteria(Criteria criteria);

    Page<AnimalData> listByAnimalCriteria(AnimalSearchCriteria criteria);
}
