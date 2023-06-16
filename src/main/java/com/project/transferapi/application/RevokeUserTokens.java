package com.project.transferapi.application;

import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.ports.FindAllUserTokensRepositoryPort;
import com.project.transferapi.domain.ports.SaveTokenRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RevokeUserTokens {

    private final FindAllUserTokensRepositoryPort findAllUserTokensRepository;
    private final SaveTokenRepositoryPort saveTokenRepositoryPort;

    public void invoke(final User user) {
        var userTokens = findAllUserTokensRepository.findAll(user.getId());
        if (userTokens.isEmpty()) return;

        userTokens.forEach(t -> {
            t.setRevoked(true);
            t.setExpired(true);
            saveTokenRepositoryPort.save(t);
        });
    }

}
