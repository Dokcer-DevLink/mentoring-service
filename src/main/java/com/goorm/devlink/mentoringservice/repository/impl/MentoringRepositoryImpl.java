package com.goorm.devlink.mentoringservice.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

public class MentoringRepositoryImpl {


    private JPAQueryFactory jpaQueryFactory;

    public MentoringRepositoryImpl(EntityManager entityManager){
        jpaQueryFactory = new JPAQueryFactory(entityManager);

    }

}
