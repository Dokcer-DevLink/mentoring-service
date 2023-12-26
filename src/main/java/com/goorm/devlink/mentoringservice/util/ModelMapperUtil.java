package com.goorm.devlink.mentoringservice.util;


import com.goorm.devlink.mentoringservice.dto.MentoringApplyDto;
import com.goorm.devlink.mentoringservice.entity.Mentoring;
import com.goorm.devlink.mentoringservice.entity.MentoringApply;
import com.goorm.devlink.mentoringservice.vo.response.MentoringDetailResponse;
import com.goorm.devlink.mentoringservice.vo.response.MentoringSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

@RequiredArgsConstructor
public class ModelMapperUtil {

    private final ModelMapper modelMapper;

    public MentoringApply convertToMentoringApply(MentoringApplyDto mentoringApplyDto){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(mentoringApplyDto, MentoringApply.class);
    }

    public MentoringDetailResponse convertToMentoringDetailResponse(Mentoring mentoring){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(mentoring, MentoringDetailResponse.class);
    }

    public MentoringSimpleResponse convertToMentoringSimpleResponse(Mentoring mentoring){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(mentoring, MentoringSimpleResponse.class);
    }



}
