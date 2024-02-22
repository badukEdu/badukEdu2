package org.choongang.teacher.group.services.stGroup;

import lombok.RequiredArgsConstructor;
import org.choongang.teacher.stGrooup.repositories.StGroupRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SGDeleteService {

    private final StGroupRepository stGroupRepository;


    public void delete(Long num){

        stGroupRepository.deleteById(num);
    }

}
