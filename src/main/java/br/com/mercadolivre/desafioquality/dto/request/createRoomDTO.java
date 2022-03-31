package br.com.mercadolivre.desafioquality.dto.request;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;

public class createRoomDTO {

    @NotEmpty(message = "O campo não pode estar vazio.\n")
    @Size(max = 30,message = "O comprimento do cômodo não pode exceder 30 caracteres.")
    @Pattern(regexp = "^\\b[A-Z].*\\b", message = "O nome do comodo deve começar com uma letra maiúscula!")
    private String roomName;


    @NotEmpty(message = "A largura do cômodo não pode estar vazia. \n")
    @DecimalMax(value = "25",message = "A largura máxima permitida por cômodo é de 25 metros.")
    private Double roomWidth;

    @NotEmpty(message = "O comprimento do cômodo não pode estar vazio. \n")
    @DecimalMax(value = "33",message = "O comprimento máximo permitido por cômodo é de 33 metros.")
    private Double roomLength;
}
