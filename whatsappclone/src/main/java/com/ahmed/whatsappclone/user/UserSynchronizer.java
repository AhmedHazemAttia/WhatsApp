package com.ahmed.whatsappclone.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSynchronizer {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void synchronizeWithIdp(Jwt token) {
        log.info("Synchronizing with idp");
        getUserEmail(token).ifPresent(userEmail -> {
            log.info("Synchronizing user have email ", userEmail);
//            Optional<User> optionalUser =userRepository.findByEmail(userEmail);
            User user = userMapper.fromTokenAttribute(token.getClaims());
//            optionalUser.ifPresent(val -> user.setId(optionalUser.get().getId()));

            userRepository.save(user);
        });
    }

    private Optional<String> getUserEmail(Jwt token){
        Map<String, Object> attributes = token.getClaims();
        if(attributes.containsKey("email")){
            return Optional.of(attributes.get("email").toString());
        }
        return Optional.empty();
    }

}