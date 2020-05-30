package com.nyrhog.telegramShopping.repository;

import com.nyrhog.telegramShopping.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {

    List<Size> findAll();

    Size findByName(String name);
}
