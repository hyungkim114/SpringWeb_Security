package com.spring.boardweb.oauth.provider;


import java.util.Map;

public class KakaoUserInfo implements Oauth2UserInfo {
    Map<String, Object> attributes;
    Map<String, Object> properties;

    public KakaoUserInfo(Map<String, Object> attributes){
        this.attributes = attributes;
        this.properties = (Map<String, Object>)attributes.get("kakao_account");
    }

    @Override
    public String getProviderId(){
        return attributes.get("id")+"";
    }

    @Override
    public String getProvider(){
        return "kakao";
    }

    @Override
    public String getEmail(){
        return (String) properties.get("email");
    }

    @Override
    public String getName(){ //강제형변환
        Map profile = (Map)properties.get("profile");
        return (String) profile.get("nickname");
    }
}
