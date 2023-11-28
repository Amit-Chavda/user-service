package userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import userservice.dto.GenericResponse;
import userservice.dto.UserDto;
import userservice.service.UserService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<GenericResponse> createUser(@Valid @RequestBody UserDto userDto) {
        return new ResponseEntity<>(new GenericResponse(true, "User created successfully", userService.createUser(userDto), HttpStatus.OK.value(), LocalDateTime.now()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<GenericResponse> getAllUsers() {
        return new ResponseEntity<>(new GenericResponse(true, "Users returned successfully", userService.getAll(), HttpStatus.OK.value(), LocalDateTime.now()), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<GenericResponse> getUserById(@PathVariable long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-VALID", "ABC123");
        return new ResponseEntity<>(new GenericResponse(true, "User returned successfully", userService.getById(userId), HttpStatus.OK.value(), LocalDateTime.now()), headers, HttpStatus.OK);
    }
}
