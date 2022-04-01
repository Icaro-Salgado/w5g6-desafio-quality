package br.com.mercadolivre.desafioquality.dto.response;

import br.com.mercadolivre.desafioquality.models.Neighborhood;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class NeighborhoodListItemDTO {

    private String nameDistrict;
    private BigDecimal valueDistrictM2;
    private URI uri;

    public static NeighborhoodListItemDTO modelToDTO(Neighborhood neighborhood, UriComponentsBuilder uriBuilder){
        UriComponentsBuilder uriBuilderComponent = uriBuilder.replaceQuery(null).replacePath("/api/v1/neighborhood/{id}");

        URI uri = uriBuilderComponent.build(neighborhood.getId());

        return new NeighborhoodListItemDTO(
                neighborhood.getNameDistrict(),
                neighborhood.getValueDistrictM2(),
                uri

        );
    }

    public static List<NeighborhoodListItemDTO> modelToDTO(List<Neighborhood> neighborhoodList, UriComponentsBuilder uriBuilder){
        return neighborhoodList.stream().map(n -> NeighborhoodListItemDTO.modelToDTO(n, uriBuilder)).collect(Collectors.toList());
    }
}
