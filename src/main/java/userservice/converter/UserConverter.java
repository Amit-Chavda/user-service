package userservice.converter;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import userservice.dto.UserDto;
import userservice.entity.Address;
import userservice.entity.User;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserConverter implements Converter<UserDto, User> {

    private final AddressConverter addressConverter;

    @Override
    public @NonNull User convert(UserDto source) {
        return new User(0, source.getFirstName(), source.getLastName(), source.getMobile(), source.getEmail());
    }

    public UserDto userDto(User user, List<Address> addresses) {
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getMobile(), user.getEmail(), addressConverter.addressDtos(addresses));
    }
}
