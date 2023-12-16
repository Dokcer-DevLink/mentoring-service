package com.goorm.devlink.mentoringservice.util;


import com.goorm.devlink.mentoringservice.dto.MentoringApplyDto;
import com.goorm.devlink.mentoringservice.entity.MentoringApply;
import com.goorm.devlink.mentoringservice.vo.MentoringDetailResponse;
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

    public MentoringDetailResponse convertToMentoringDetailResponse(MentoringApply mentoringApply){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(mentoringApply, MentoringDetailResponse.class);
    }



}
