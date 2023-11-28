package com.example.smallstore.Repository;

import com.example.smallstore.Entity.Shop;
import com.example.smallstore.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    Optional<Shop> findByUser(User user);
    List<Shop> findAllByShopAddressLike(String shopAddress);
    List<Shop> findAllByShopNameContaining(String keyword);

    @Query(value = "SELECT * FROM Shop s " +
            "WHERE ST_DISTANCE_SPHERE(POINT(s.shop_lng, s.shop_lat), POINT(:longitude, :latitude)) < :distance",
            nativeQuery = true)
    List<Shop> findShopsWithinDistance(@Param("longitude") Double longitude,
                                         @Param("latitude") Double latitude,
                                         @Param("distance") Double distance);

}
