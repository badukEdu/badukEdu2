package org.choongang.admin.gamecontent.service;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.gamecontent.repositories.GameContentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameContentDeleteService {

    private final GameContentRepository gameContentRepository;

    public void delete(Long num) {
        gameContentRepository.deleteById(num);
    }

}
