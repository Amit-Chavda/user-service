package userservice.converter;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import userservice.dto.AddressDto;
import userservice.entity.Address;
import userservice.entity.User;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AddressConverter implements Converter<AddressDto, Address> {

    @Override
    public @NonNull Address convert(AddressDto source) {
        return new Address(0, source.getAddressLine1(), source.getAddressLine2(), source.getStreet(), source.getCity(), source.getState(), source.getCountry(), source.getPostCode(), null);
    }

    public @NonNull Address convert(AddressDto source, User user) {
        return new Address(0, source.getAddressLine1(), source.getAddressLine2(), source.getStreet(), source.getCity(), source.getState(), source.getCountry(), source.getPostCode(), user);
    }

    public @NonNull AddressDto addressDto(Address source) {
        return new AddressDto(source.getId(), source.getAddressLine1(), source.getAddressLine2(), source.getStreet(), source.getCity(), source.getState(), source.getCountry(), source.getPostCode());
    }

    public @NonNull List<AddressDto> addressDtos(List<Address> sources) {
        return sources.stream().map(this::addressDto).toList();
    }
}
