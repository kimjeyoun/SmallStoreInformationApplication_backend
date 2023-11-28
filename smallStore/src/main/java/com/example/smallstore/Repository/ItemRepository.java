package com.example.smallstore.Repository;

import com.example.smallstore.Entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByShopShopNum(Long shopNum);

    @Modifying
    @Transactional
    @Query("DELETE FROM Item i WHERE i.num = :num")
    void deleteByNum(@Param("num")Long num);
}
