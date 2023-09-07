package com.example.smallstore.Repository;

import com.example.smallstore.Entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, String> {
    boolean existsChatByChatFromAndChatTo(String chatFrom, String chatTo);

    Optional<Chat> findByChatFromAndChatTo(String chatFrom, String chatTo);

    Optional<Chat> findByChatId(String chatId);

    @Query("SELECT c FROM Chat c where c.chatFrom = :sender or c.chatTo = :sender ORDER BY c.createdAt desc")
    List<Chat> findByChatList(String sender);

    @Query("SELECT c.chatId FROM Chat c")
    List<String> findAllChatIds();
}
