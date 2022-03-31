package br.com.mercadolivre.desafioquality.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreatePropertyDTO {
    String propName;
    String propDistrict;
    List<CreateRoomsDTO> rooms;
}
