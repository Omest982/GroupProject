package org.example.DTO;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Data
public class PageRequestDTO {
    private Integer pageNumber;
    private Integer sizePerPage;

    public PageRequest getPageRequest(){
        return PageRequest.of(pageNumber, sizePerPage, Sort.Direction.ASC, "id");
    }
}
