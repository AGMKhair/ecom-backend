package com.agmkhair.ecom.repository;

import com.agmkhair.ecom.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // MAIN MENU
    List<Category> findByParentIdIsNullAndSubParentIdIsNull();

    // SUB MENU (by parent)
    List<Category> findByParentIdAndSubParentIdIsNull(Long parentId);

    // SUB-SUB MENU (by sub parent)
    List<Category> findBySubParentId(Long subParentId);

    // Main menu (no parent_id and no sub_parent_id)
    @Query("SELECT c FROM Category c WHERE c.parentId IS NULL AND c.subParentId IS NULL ORDER BY c.priority ASC")
    List<Category> findMainMenus();

    // Sub menu (only parent_id)
    @Query("SELECT c FROM Category c WHERE c.parentId = :parentId AND c.subParentId IS NULL ORDER BY c.priority ASC")
    List<Category> findSubMenus(Long parentId);

    // Sub–sub menu (only sub_parent_id)
    @Query("SELECT c FROM Category c WHERE c.subParentId = :subParentId ORDER BY c.priority ASC")
    List<Category> findSubSubMenus(Long subParentId);

    @Query("SELECT COALESCE(MAX(c.priority), 0) FROM Category c WHERE " +
            "( :parentId IS NULL AND :subParentId IS NULL AND c.parentId IS NULL AND c.subParentId IS NULL ) OR " +
            "( :parentId IS NOT NULL AND :subParentId IS NULL AND c.parentId = :parentId AND c.subParentId IS NULL ) OR " +
            "( :subParentId IS NOT NULL AND c.subParentId = :subParentId )")
    Long getMaxPriority(Long parentId, Long subParentId);

}
