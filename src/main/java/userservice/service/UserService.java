package userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import userservice.converter.AddressConverter;
import userservice.converter.UserConverter;
import userservice.dto.AddressDto;
import userservice.dto.UserDto;
import userservice.entity.Address;
import userservice.entity.User;
import userservice.exception.ResourceNotFoundException;
import userservice.exception.UserAlreadyExistException;
import userservice.repository.AddressRepository;
import userservice.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final UserConverter userConverter;
    private final AddressConverter addressConverter;

    public UserDto createUser(UserDto userDto) {

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistException("User with email already exist");
        }

        if (userRepository.existsByMobile(userDto.getMobile())) {
            throw new UserAlreadyExistException("User with mobile number already exist");
        }

        User user = userRepository.save(userConverter.convert(userDto));

        List<Address> addressList = userDto.getAddressDtos().stream().map(addressDto -> addressConverter.convert(addressDto, user)).toList();
        addressRepository.saveAll(addressList);

        userDto.setId(user.getId());
        return userDto;
    }

    public Object addAddress(long userId, AddressDto addressDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id '" + userId + "' not found"));
        Address address = addressRepository.save(addressConverter.convert(addressDto, user));
        addressDto.setId(address.getId());
        return addressDto;
    }

    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(user -> userConverter.userDto(user, addressRepository.findAllByUser(user))).toList();
    }

    public List<AddressDto> getAllAddresses(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id '" + userId + "' not found"));
        return addressRepository.findAllByUser(user).stream().map(addressConverter::addressDto).toList();
    }

    public UserDto getById(long userId) {

        return userRepository.findById(userId)
                .map(user -> {
                    List<Address> addressList = addressRepository.findAllByUser(user);
                    return userConverter.userDto(user, addressList);
                })
                .orElseThrow(() -> new ResourceNotFoundException("User with id '" + userId + "' not found"));
    }

    public AddressDto getAddressById(long addressId) {
        return addressRepository.findById(addressId)
                .map(addressConverter::addressDto)
                .orElseThrow(() -> new ResourceNotFoundException("Address with id '" + addressId + "' not found"));
    }
}
