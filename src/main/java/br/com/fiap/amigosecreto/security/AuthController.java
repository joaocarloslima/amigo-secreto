package br.com.fiap.amigosecreto.security;

import br.com.fiap.amigosecreto.participant.Participant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final TokenService tokenService;

    public record Token(String token) {}
    public record LoginRequest(String email, String password) {}

    @PostMapping("/login")
    public Token login(@RequestBody LoginRequest request){
        // pedir para o Spring autenticar
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email, request.password)
        );

        var participant = (Participant) authentication.getPrincipal();
        //gerar e retornar o token
        var token = tokenService.generate(participant);
        return new Token(token);

    }


}
