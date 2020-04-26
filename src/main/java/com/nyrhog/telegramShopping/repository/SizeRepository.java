package com.nyrhog.telegramShopping.repository;

import com.nyrhog.telegramShopping.entity.Size;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SizeRepository extends CrudRepository<Size, Long> {

    List<Size> findAll();

}
