package br.com.mercadolivre.desafioquality.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LargerRoomDTO {
    String name;
    Double totalArea;
    Double width, length;
}
