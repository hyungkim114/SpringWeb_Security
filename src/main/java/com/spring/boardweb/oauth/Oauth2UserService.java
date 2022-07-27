package com.spring.boardweb.oauth;

import com.spring.boardweb.entity.CustomUserDetails;
import com.spring.boardweb.entity.User;
import com.spring.boardweb.oauth.provider.KakaoUserInfo;
import com.spring.boardweb.oauth.provider.Oauth2UserInfo;
import com.spring.boardweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service //7월27
public class Oauth2UserService extends DefaultOAuth2UserService {
    @Autowired //db에 바로 저장하는...
    private UserRepository userRepository;

    @Autowired // 암호화를 위해서 패스워드 인코딩으로..
    private PasswordEncoder passwordEncoder;

    // 토큰이 발행된 후 사용자 정보에 대한 후처리함수
    // 소셜로그인 버튼 클릭 -> 사용자 동의 창 -> 사용자 동의 후 로그인 완료 ->  code리턴 -> 토큰 수령
    // -> 토큰을 통한 유저 정보 획득 - > localUser 함수 자동 호출 -> 사용자 정보를 커스터 마이징
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String userName= "";
        String providerId = "";

        Oauth2UserInfo oauth2UserInfo = null;

        //소셜 카테고리 검정 // 구글이나 네이버로 다른 기능 넣을려면  else  사용
        if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")){
            oauth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
            providerId = oauth2UserInfo.getProviderId();
            userName = oauth2UserInfo.getName();

        } else {
            System.out.println("카카오 로그인만 지원합니다.");
        }
        //kakao
        String provider = oauth2UserInfo.getProvider();
        //kakao_123454235
        String userId = provider + "_" + providerId;
        String password = passwordEncoder.encode(oauth2UserInfo.getName());
        String email = oauth2UserInfo.getEmail();
        String  role = "ROLE_USER";

       //사용자가 가입되어 있는지 검사할 Entitiy 객체 상성
        User user;

        if(userRepository.findById(userId).isPresent()){
            //가입이 되어있으면 정보 담아줌
            user= userRepository.findById(userId).get();
        } else {
            //가입이 안되어 있으면 null 리턴하여 가입진행
            user = null;
        }

        //소셔 로그인 정보로 가입 진행
        if(user == null){
            user =new User();
            user.setUserId(userId);
            user.setUserPw(password);
            user.setUserNm(oauth2UserInfo.getName());
            user.setUserMail(email);
            user.setRole(role);

            //사용자 정보 DB에 저장
            userRepository.save(user);
        }
        //세셔에 로그인 정보 저장
        return new CustomUserDetails(user, oAuth2User.getAttributes());

    }

}
