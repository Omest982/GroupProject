package org.example.repository;

import org.example.entity.Brand;
import org.example.entity.Category;
import org.example.entity.Product;
import org.example.entity.ProductVariation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Set;

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
    List<Product> findAllByParams(@Param("param") String param);
}
