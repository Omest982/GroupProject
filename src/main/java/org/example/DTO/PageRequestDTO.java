package org.example.DTO;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {
    private Integer pageNumber;
    private Integer sizePerPage;
    public PageRequest getPageRequest(){
        return PageRequest.of(pageNumber, sizePerPage, Sort.by(Sort.Direction.ASC, "id"));
    }
}
