package com.sogong.sogong.repositories.animal;

import com.sogong.sogong.entity.common.Criteria;
import com.sogong.sogong.model.animal.AnimalData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collections;

public class AnimalDataRepositoryImpl implements AnimalDataRepositoryCustom{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<AnimalData> listByCriteria(Criteria criteria) {
        String baseCountQuery = "select count(distinct ad) from AnimalData ad ";
        //아이템
        String baseQuery = "select ad from AnimalData ad ";

        StringBuilder countBuilderWhere = new StringBuilder();
        StringBuilder objectBuilderWhere = new StringBuilder();

        //create and append criteria
        String whereQuery = " where 0 = 0 ";

        countBuilderWhere.append(whereQuery);
        objectBuilderWhere.append(whereQuery);

        String query = " ORDER BY ad.id desc";
        objectBuilderWhere.append(query);

        Query countQ = entityManager.createQuery(
                baseCountQuery + countBuilderWhere.toString());

        //object query
        Query objectQ = entityManager.createQuery(
                baseQuery + objectBuilderWhere.toString());

        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize());
        Number totalCount = (Number) countQ.getSingleResult();

        if (totalCount.intValue() == 0) {
            return new PageImpl<>(Collections.emptyList(), pageable, totalCount.longValue());
        }

        objectQ.setFirstResult((int) pageable.getOffset());
        if (criteria.getSize() > 0) {
            if (criteria.getSize() < totalCount.intValue()) {
                objectQ.setMaxResults(criteria.getSize());
            } else {
                objectQ.setMaxResults(totalCount.intValue());
            }
        }

        return new PageImpl<>(objectQ.getResultList(), pageable, totalCount.longValue());
    }
}
