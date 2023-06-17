package com.project.transferapi.infra.security.config;

import com.project.transferapi.domain.ports.FindUserByEmailPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomAuthenticationProviderTest {

    @InjectMocks
    CustomAuthenticationProvider customAuthenticationProvider;

    @Mock
    FindUserByEmailPort findUserByEmail;

    @Test
    void shouldReturnUserDetailsService() {
        UserDetailsService userDetailsService = customAuthenticationProvider.userDetailsService();
        assertNotNull(userDetailsService);
    }

    @Test
    void shouldReturnAuthenticationProvider() {
        AuthenticationProvider authenticationProvider = customAuthenticationProvider.authenticationProvider();
        assertNotNull(authenticationProvider);
    }

    @Test
    void shouldReturnAuthenticationManager() {
        try {
            AuthenticationConfiguration configuration = mock(AuthenticationConfiguration.class);
            when(configuration.getAuthenticationManager()).thenReturn(mock(AuthenticationManager.class));
            var manager = customAuthenticationProvider.authenticationManager(configuration);
            assertNotNull(manager);
        } catch (Exception e) {
            fail(e);
        }
    }


}
