package com.edatatest.RestAPI.services;

import com.edatatest.RestAPI.converters.UserConverter;
import com.edatatest.RestAPI.dto.UserDto;
import com.edatatest.RestAPI.entities.User;
import com.edatatest.RestAPI.exceptions.UserNotFoundException;
import com.edatatest.RestAPI.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void save(User user){userRepository.save(user);}

    public List<UserDto> findAll(Sort sort){
        return userRepository.findAll(sort).stream()
                .map(UserConverter::convert)
                .collect(Collectors.toList());
    }

    public Optional<User> findByName(String name) {return userRepository.findByName(name);}

    public UserDto findUserById(Long id){
        Optional<User> user = userRepository.findById(id);
        return user.map(UserConverter::convert).orElseThrow(UserNotFoundException::new);
    }
}
