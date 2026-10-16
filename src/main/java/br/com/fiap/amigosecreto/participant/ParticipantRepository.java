package br.com.fiap.amigosecreto.participant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    List<ParticipantView> findByNameContainingIgnoreCase(String name);

    @Query("SELECT p FROM Participant p WHERE p.wish IS NULL") // JPQL
    List<ParticipantView> findParticipantsWithoutWish();

    Optional<Participant> findByEmail(String email);
}
