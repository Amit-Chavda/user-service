package userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import userservice.dto.AddressDto;
import userservice.dto.GenericResponse;
import userservice.service.UserService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {
    private final UserService userService;


    @PostMapping("/{userId}")
    public ResponseEntity<GenericResponse> addAddress(@PathVariable long userId, @Valid @RequestBody AddressDto addressDto) {
        return new ResponseEntity<>(new GenericResponse(true, "Address added successfully", userService.addAddress(userId, addressDto), HttpStatus.OK.value(), LocalDateTime.now()), HttpStatus.OK);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<GenericResponse> getAddressById(@PathVariable long addressId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-VALID", "ABC123");
        return new ResponseEntity<>(new GenericResponse(true, "Address returned successfully", userService.getAddressById(addressId), HttpStatus.OK.value(), LocalDateTime.now()), headers, HttpStatus.OK);
    }

}
