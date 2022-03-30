package br.com.mercadolivre.desafioquality.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import br.com.mercadolivre.desafioquality.models.Room;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PropertyDTO {
    private UUID id;

    @NotEmpty(message = "O nome da propiedade não pode ficar vazio!\n")
    @Size(max = 30,message = "O comprimento do nome não pode exceder 30 caracteres!\n")
    @Pattern(regexp = "^\\b[A-Z].*\\b", message = "O nome da propiedade deve começar com uma letra maiúscula!\n")
    private String propName;

    @NotEmpty(message = "O bairro não pode ficar vazio!")
    @Size(max = 45,message = "O comprimento do bairro não pode exceder 45 caracteres!\n")
    private String propDistrict;

    private List<@Valid Room> propRooms;

}
