package com.simplenotesapp.simplenotesapp.controller;

import com.simplenotesapp.simplenotesapp.dto.UserDto;
import com.simplenotesapp.simplenotesapp.mapper.UserDtoMapper;
import com.simplenotesapp.simplenotesapp.model.User;
import com.simplenotesapp.simplenotesapp.service.SessionService;
import com.simplenotesapp.simplenotesapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SessionController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @Autowired
    UserDtoMapper userDtoMapper;

    @Autowired
    private SessionService sessionService;

    @RequestMapping(value = "/api/security", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getLoggedUser() {
        User user = sessionService.getLoggedUser();
        if (user != null) {
            UserDto userDto = userDtoMapper.mapToDto(user);
            userDto.setRoles(sessionService.getLoggedUserRoles());
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
