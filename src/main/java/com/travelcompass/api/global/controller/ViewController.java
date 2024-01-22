package com.travelcompass.api.global.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/users")
public class ViewController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}

/* html없는 로그인은 불가능하다는 걸 깨달음. 주소로 다이렉트로 꽂히게도 시도해봤는데 에러남.
@Controller
@RequiredArgsConstructor
//@RequestMapping("/users")
public class ViewController {
    private final ClientRegistrationRepository clientRegistrationRepository;
    /*public ViewController(ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @GetMapping("/users/login")
    public String login(HttpServletRequest request) {
        String providerId = "naver";

        // 클라이언트 등록 정보 가져오기
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(providerId);

        // OAuth2 권한 요청 만들기 - client_id, client_secret를 환경변수로 설정하면 에러남
        OAuth2AuthorizationRequest authorizationRequest = OAuth2AuthorizationRequest
                .authorizationCode()
                .authorizationUri(clientRegistration.getProviderDetails().getAuthorizationUri()) //authorization/naver
                .clientId(clientRegistration.getClientId())
                .redirectUri("http://localhost:8080/login/oauth2/code/naver")
                .state("asdfasdf")
                .build();

        //System.out.println(authorizationRequest.getAuthorizationRequestUri());
        return "redirect:" + authorizationRequest.getAuthorizationRequestUri();
    }
}
*/