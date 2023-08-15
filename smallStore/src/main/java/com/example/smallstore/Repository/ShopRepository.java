package com.example.smallstore.Repository;

import com.example.smallstore.Entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, String> {
//
//    @Modifying
//    @Query("DELETE FROM Shop s WHERE s.shopName = :shopName")
//    void deleteByShopName(@Param("shopName")String shopName);
//
//    @Modifying
//    @Query("DELETE FROM Shop s WHERE s.user.id = :userId")
//    void deleteByUserId(@Param("userId")Long userId);
}
