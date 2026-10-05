package info.bitsolucoes.portal.repository;

import info.bitsolucoes.portal.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}