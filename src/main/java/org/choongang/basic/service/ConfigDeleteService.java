package org.choongang.basic.service;

import lombok.RequiredArgsConstructor;
import org.choongang.basic.entities.Configs;
import org.choongang.basic.repositories.ConfigsRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigDeleteService {
    private final ConfigsRepository repository;

    public void delete(String code) {
        Configs config = repository.findById(code).orElse(null);
        if (config == null) {
            return;
        }

        repository.delete(config);
        repository.flush();
    }
}
