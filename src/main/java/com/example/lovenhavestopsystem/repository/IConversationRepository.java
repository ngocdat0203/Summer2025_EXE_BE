package com.example.lovenhavestopsystem.repository;

import com.example.lovenhavestopsystem.model.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IConversationRepository extends JpaRepository<Conversation, Integer> {

    Conversation findByIdAndDeletedTimeIsNull(int id);

    //Conversation findByNameAndDeletedTimeIsNull(String name);

    Optional<Conversation> findByParticipantHash(String participantHash);

    @Query("""
    SELECT c FROM Conversation c
    JOIN c.participants p
    WHERE p.id = :userId AND c.deletedTime IS NULL
    """)
    List<Conversation> findByAccountId(int userId);

    @Query("""
    SELECT c FROM Conversation c
    JOIN c.participants p
    WHERE p.name = :userName AND c.deletedTime IS NULL
    """)
    List<Conversation> findByAccountName( String userName);

    @Query("""
    SELECT c FROM Conversation c
    JOIN c.participants p
    WHERE p.email = :userName AND c.deletedTime IS NULL
    """)
    List<Conversation> findByAccountEmail( String userName);

    @Query("SELECT c FROM Conversation c JOIN FETCH c.participants WHERE c.id = :id")
    Optional<Conversation> findByIdWithParticipants(@Param("id") int id);

}