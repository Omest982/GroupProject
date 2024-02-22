package org.example.repository;

import org.example.entity.Category;
import org.example.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategoriesIn(Iterable<Category> categories);

    Page<Product> findAllByCategoriesIn(Iterable<Category> categories, Pageable pageRequest);

    @Query(value = """
                SELECT p FROM Product p
                WHERE upper(p.name) LIKE upper(concat('%', :param, '%'))
                OR upper(p.productGroup) LIKE upper(concat('%', :param, '%'))
                OR upper(p.brand.name) LIKE upper(concat('%', :param, '%'))
                OR upper(p.sex) LIKE upper(concat('%', :param, '%'))
                OR upper(p.classification) LIKE upper(concat('%', :param, '%'))
        """
    )
    List<Product> findAllByParam(@Param("param") String param);

    Page<Product> findAllByIdIn(Iterable<Long> productId, Pageable pageable);
}
