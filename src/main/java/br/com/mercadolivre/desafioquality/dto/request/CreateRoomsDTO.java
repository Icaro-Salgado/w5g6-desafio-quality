package br.com.mercadolivre.desafioquality.dto.request;

import br.com.mercadolivre.desafioquality.models.Room;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
public class CreateRoomsDTO {
    @NotEmpty(message = "O campo não pode estar vazio.\n")
    @Size(max = 30,message = "O comprimento do cômodo não pode exceder 30 caracteres.")
    @Pattern(regexp = "^\\b[A-Z].*\\b", message = "O nome do comodo deve começar com uma letra maiúscula!")
    String name;

    @NotNull(message = "A largura do cômodo não pode estar vazia. \n")
    @DecimalMax(value = "25",message = "A largura máxima permitida por cômodo é de 25 metros.")
    Double width;

    @NotNull(message = "O comprimento do cômodo não pode estar vazio. \n")
    @DecimalMax(value = "33",message = "O comprimento máximo permitido por cômodo é de 33 metros.")
    Double length;

    public Room toModel() {
        return Room
                .builder()
                .roomName(this.name)
                .roomWidth(this.width)
                .roomLength(this.length)
                .build();
    }
}
