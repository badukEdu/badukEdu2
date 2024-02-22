package org.choongang.admin.education.service;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.education.repositories.EduDataRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EduContentDeleteService {

    private final EduDataRepository eduDataRepository;

    public void delete(Long num) {
        eduDataRepository.deleteById(num);
    }
}
