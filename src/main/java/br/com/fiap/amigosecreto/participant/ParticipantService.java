package br.com.fiap.amigosecreto.participant;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    public Page<Participant> findAllParticipants(Pageable pageable) {
        return participantRepository.findAll(pageable);
    }

    public List<ParticipantView> findParticipantsByName(String name) {
        return participantRepository.findByNameContainingIgnoreCase(name);
    }

    public List<ParticipantView> findParticipantsWithoutWish() {
        return participantRepository.findParticipantsWithoutWish();
    }

    @Transactional
    public void drawn(){
        //buscar os participantes
        var all = participantRepository.findAll();

        //embaralhar a lista
        Collections.shuffle(all);

        //atribuir o amigo secreto
        for (int i = 0; i < all.size(); i++) {
            var participant = all.get(i);
            var friendIndex = (i + 1) % all.size();
            var friend = all.get(friendIndex);
            participant.setFriend(friend);
        }

    }
}
