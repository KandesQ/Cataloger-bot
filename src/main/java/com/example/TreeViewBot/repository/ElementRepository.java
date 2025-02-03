package com.example.TreeViewBot.repository;

import com.example.TreeViewBot.entity.Element;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElementRepository extends JpaRepository<Element, Long> {
    /**
     * @return список корневых эл-тов, у которых нет родителей
     */
    List<Element> findByParentElementIsNull();

    /**
     * @param name имя эл-та
     * @return сущность эл-та
     */
    Optional<Element> findByName(String name);

}
