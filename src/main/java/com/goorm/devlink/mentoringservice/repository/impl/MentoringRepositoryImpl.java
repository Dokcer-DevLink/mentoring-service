package com.goorm.devlink.mentoringservice.repository.impl;

import com.goorm.devlink.mentoringservice.entity.MentoringApply;
import com.goorm.devlink.mentoringservice.repository.MentoringRepositoryCustom;
import com.goorm.devlink.mentoringservice.vo.MentoringStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Slice;

import javax.persistence.EntityManager;


public class MentoringRepositoryImpl implements MentoringRepositoryCustom {


    private JPAQueryFactory queryFactory;

    public MentoringRepositoryImpl(EntityManager entityManager){
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Slice<MentoringApply> findMentoringListByUserUuidAndStatus(String userUuid, MentoringStatus mentoringStatus) {
        return null;
    }
}
