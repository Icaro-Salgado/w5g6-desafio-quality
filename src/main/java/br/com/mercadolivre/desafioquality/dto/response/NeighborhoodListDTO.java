package br.com.mercadolivre.desafioquality.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.URI;
import java.util.List;

@Data
@AllArgsConstructor
public class NeighborhoodListDTO {

    private Integer page;
    private Integer totalPages;
    private URI nextPage;
    private List<NeighborhoodListItemDTO> neighborhoods;

    public static NeighborhoodListDTO modelToDTO(List<NeighborhoodListItemDTO> neighborhoodList, Integer page, Integer totalPages, URI nextPageURI ){
        return new NeighborhoodListDTO(page, totalPages, nextPageURI, neighborhoodList);
    }
}
