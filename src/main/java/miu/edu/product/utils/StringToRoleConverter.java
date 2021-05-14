package miu.edu.product.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import miu.edu.product.domain.Role;
import org.springframework.core.convert.converter.Converter;

public class StringToRoleConverter implements Converter<String, Role> {


    @Override
    public Role convert(String source) {
        if (source.contains("1")) {
            return new Role(1L,0,"ADMIN");
        }

        if (source.contains("2")) {
            return new Role(2L,0,"USER");
        }
        return null;
    }
}
