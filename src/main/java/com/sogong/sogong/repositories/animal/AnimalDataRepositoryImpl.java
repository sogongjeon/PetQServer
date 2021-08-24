package com.sogong.sogong.repositories.animal;

import com.sogong.sogong.entity.animal.AnimalSearchCriteria;
import com.sogong.sogong.entity.common.Criteria;
import com.sogong.sogong.model.animal.AnimalData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

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

        String query = " ORDER BY ad.createdAt desc";
        objectBuilderWhere.append(query);

        Query countQ = entityManager.createQuery(
                baseCountQuery + countBuilderWhere.toString());

        //object query
        Query objectQ = entityManager.createQuery(
                baseQuery + objectBuilderWhere.toString());

        Number totalCount = (Number) countQ.getSingleResult();
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize() > 0 ? criteria.getSize() : totalCount.intValue());
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
        }else if(criteria.getSize() == 0){
            objectQ.setMaxResults(totalCount.intValue());
        }

        //noinspection unchecked
        return new PageImpl<>(objectQ.getResultList(), pageable, totalCount.longValue());
//        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize());
//        Number totalCount = (Number) countQ.getSingleResult();
//
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
//
//        return new PageImpl<>(objectQ.getResultList(), pageable, totalCount.longValue());
    }

    @Override
    public Page<AnimalData> listByAnimalCriteria(AnimalSearchCriteria criteria) {
        String baseCountQuery = "select count(distinct ad) from AnimalData ad inner join Gu g on g.guCode = ad.guCode inner join AnimalKind ak on ak.kindCode = ad.animalKindCode";
        //아이템
        String baseQuery = "select ad from AnimalData ad inner join Gu g on g.guCode = ad.guCode inner join AnimalKind ak on ak.kindCode = ad.animalKindCode";

        StringBuilder countBuilderWhere = new StringBuilder();
        StringBuilder objectBuilderWhere = new StringBuilder();

        //create and append criteria
        String whereQuery = " where 0 = 0 ";

        countBuilderWhere.append(whereQuery);
        objectBuilderWhere.append(whereQuery);


        //보호타입(개인보호, 보호소, 발견)별 필터
        if (criteria.getProtectType() != null) {
            String itemTypeQuery = " and ad.protectType in :protectType";
            countBuilderWhere.append(itemTypeQuery);
            objectBuilderWhere.append(itemTypeQuery);
        }

        //시 코드(광주광역시, 서울특별시 등) 검색
        if (criteria.getCityCode() != null) {
            String type = " and g.cityCode = :cityCode";
            countBuilderWhere.append(type);
            objectBuilderWhere.append(type);
        }

        //구코드(동작구, 관악구 등) - List로 관리
        if (criteria.getGuCode() != null && criteria.getGuCode().size() > 0) {
            String type = " and ad.guCode in :guCode";
            countBuilderWhere.append(type);
            objectBuilderWhere.append(type);
        }

        //성별 필터
        if (criteria.getSex() != null) {
            String type = " and ad.sex = :sex";
            countBuilderWhere.append(type);
            objectBuilderWhere.append(type);
        }

        //축종 필터 - List로 관리
        if (criteria.getKind() != null && criteria.getKind().size() > 0) {
            String type = " and ak.type in :kind";
            countBuilderWhere.append(type);
            objectBuilderWhere.append(type);
        }

        //품종 필터 - List로 관리
        if (criteria.getKindDetail() != null && criteria.getKindDetail().size() > 0) {
            String type = " and ad.animalKindCode in :kindDetail";
            countBuilderWhere.append(type);
            objectBuilderWhere.append(type);
        }

        if (!StringUtils.isEmpty(criteria.getSearchPeriodFrom())) {
            String namedQuery = " and ad.happenedDate >= :searchPeriodFrom";
            countBuilderWhere.append(namedQuery);
            objectBuilderWhere.append(namedQuery);
        }

        if (!StringUtils.isEmpty(criteria.getSearchPeriodTo())) {
            String namedQuery = " and ad.happenedDate <= :searchPeriodTo";
            countBuilderWhere.append(namedQuery);
            objectBuilderWhere.append(namedQuery);
        }

        String orderByQuery = "";

        orderByQuery = " ORDER BY ad.createdAt desc";
        objectBuilderWhere.append(orderByQuery);

        Query countQ = entityManager.createQuery(
                baseCountQuery + countBuilderWhere.toString());

        //object query
        Query objectQ = entityManager.createQuery(
                baseQuery + objectBuilderWhere.toString());

        if (criteria.getProtectType() != null) {
            objectQ.setParameter("protectType", criteria.getProtectType());
            countQ.setParameter("protectType", criteria.getProtectType());
        }

        if (criteria.getCityCode() != null) {
            objectQ.setParameter("cityCode", criteria.getCityCode());
            countQ.setParameter("cityCode", criteria.getCityCode());
        }

        if (criteria.getGuCode() != null && criteria.getGuCode().size() > 0) {
            objectQ.setParameter("guCode", criteria.getGuCode());
            countQ.setParameter("guCode", criteria.getGuCode());
        }

        if (criteria.getSex() != null) {
            objectQ.setParameter("sex", criteria.getSex());
            countQ.setParameter("sex", criteria.getSex());
        }

        if (criteria.getKind() != null && criteria.getKind().size() > 0) {
            objectQ.setParameter("kind", criteria.getKind());
        }

        if (criteria.getKindDetail() != null && criteria.getKindDetail().size() > 0) {
            objectQ.setParameter("kindDetail", criteria.getKindDetail());
            countQ.setParameter("kindDetail", criteria.getKindDetail());
        }

        if (!StringUtils.isEmpty(criteria.getSearchPeriodFrom())) {
            objectQ.setParameter("searchPeriodFrom", criteria.getSearchPeriodFrom());
            countQ.setParameter("searchPeriodFrom", criteria.getSearchPeriodFrom());
        }
        if (!StringUtils.isEmpty(criteria.getSearchPeriodTo())) {
            objectQ.setParameter("searchPeriodTo", criteria.getSearchPeriodTo());
            countQ.setParameter("searchPeriodTo", criteria.getSearchPeriodTo());
        }

        Number totalCount = (Number) countQ.getSingleResult();
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize() > 0 ? criteria.getSize() : totalCount.intValue());
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
        }else if(criteria.getSize() == 0){
            objectQ.setMaxResults(totalCount.intValue());
        }

        //noinspection unchecked
        return new PageImpl<>(objectQ.getResultList(), pageable, totalCount.longValue());
    }
}
