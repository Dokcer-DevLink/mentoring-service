package com.goorm.devlink.mentoringservice.util;


import com.goorm.devlink.mentoringservice.dto.MentoringApplyDto;
import com.goorm.devlink.mentoringservice.entity.Mentoring;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

@RequiredArgsConstructor
public class ModelMapperUtil {

    private final ModelMapper modelMapper;

    public Mentoring convertToMentoring(MentoringApplyDto mentoringApplyDto){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(mentoringApplyDto,Mentoring.class);
    }



}
