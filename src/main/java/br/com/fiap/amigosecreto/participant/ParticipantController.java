package br.com.fiap.amigosecreto.participant;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/participants")
public class ParticipantController {

    private final ParticipantService participantService;
    private final ParticipantRepository participantRepository;

    @GetMapping
    public Page<Participant> findAllParticipants(@PageableDefault(size = 20, sort = "name") Pageable pageable) {
        return participantService.findAllParticipants(pageable);
    }

    @GetMapping("/search")
    public List<ParticipantView> findParticipantsByName(@RequestParam String name) {
        return participantService.findParticipantsByName(name);
    }

    @GetMapping("without-wish")
    public List<ParticipantView> findParticipantsWithoutWish() {
        return participantService.findParticipantsWithoutWish();
    }

    @PostMapping("/draw")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void draw() {
        participantService.drawn();
    }

    @GetMapping("/me/draw")
    public Participant myDraw(@AuthenticationPrincipal Jwt jwt) {
        var me = participantRepository.findByEmail(jwt.getSubject()).orElseThrow();
        if (me.getFriend() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O sorteio ainda não foi feito");
        }
        return me.getFriend();
    }

}
