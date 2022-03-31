package br.com.mercadolivre.desafioquality.dto.request;

import br.com.mercadolivre.desafioquality.models.Property;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CreatePropertyDTO {

    @NotEmpty(message = "O nome da propiedade não pode ficar vazio!\n")
    @Size(max = 30,message = "O comprimento do nome não pode exceder 30 caracteres!\n")
    @Pattern(regexp = "^\\b[A-Z].*\\b", message = "O nome da propiedade deve começar com uma letra maiúscula!\n")
    String propName;

    @NotEmpty(message = "O bairro não pode ficar vazio!")
    @Size(max = 45,message = "O comprimento do bairro não pode exceder 45 caracteres!\n")
    String propDistrict;

    List<@Valid CreateRoomsDTO> rooms;


    public Property toModel(){
        return Property
                .builder()
                .propName(this.propName)
                .propDistrict(this.propDistrict)
                .propRooms(this.rooms.stream().map(CreateRoomsDTO::toModel).collect(Collectors.toList()))
                .build();
    }
}
