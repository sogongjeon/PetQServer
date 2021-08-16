package com.sogong.sogong.repositories.animal;

import com.sogong.sogong.model.animal.AnimalKind;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

public class AnimalKindRepositoryImpl implements AnimalKindRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AnimalKind> listByType(String type) {
        // 건수
        String baseCountQuery = "select count(distinct a) from AnimalKind a";
        // 레코드 조회
        String baseQuery = "select a from AnimalKind a";

        StringBuilder countBuilderWhere = new StringBuilder();
        StringBuilder objectBuilderWhere = new StringBuilder();
        String whereQuery = " where 0 = 0 ";
        countBuilderWhere.append(whereQuery);
        objectBuilderWhere.append(whereQuery);

        if(type != null){
            String namedQuery = " and a.type = :type";
            countBuilderWhere.append(namedQuery);
            objectBuilderWhere.append(namedQuery);
        }

        //count query
        Query countQ = entityManager.createQuery(
                baseCountQuery + countBuilderWhere.toString());

        //object query
        Query objectQ = entityManager.createQuery(
                baseQuery + objectBuilderWhere.toString());

        if(type != null){
            countQ.setParameter("type", type);
            objectQ.setParameter("type", type);
        }

        return objectQ.getResultList();

//        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize());
//        Number totalCount = (Number) countQ.getSingleResult();
//        if (totalCount.intValue() == 0) {
//            return new PageImpl<>(Collections.emptyList(), pageable, totalCount.longValue());
//        }
//
//        objectQ.setFirstResult((int) pageable.getOffset());
//        if (criteria.getSize() > 0) {
//            if (criteria.getSize() < totalCount.intValue()) {
//                objectQ.setMaxResults(criteria.getSize());
//            } else {
//                objectQ.setMaxResults(totalCount.intValue());
//            }
//        }

        //noinspection unchecked
//        return new PageImpl<>(objectQ.getResultList(), pageable, totalCount.longValue());
    }
}
