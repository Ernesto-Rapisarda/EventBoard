package it.sad.students.eventboard.configuration;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CustomVoter implements AccessDecisionVoter {


    public CustomVoter() {
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object o, Collection collection) {

        FilterInvocation filterInvocation = (FilterInvocation) o;

        System.out.println(filterInvocation.getRequestUrl());
        String url_chiamante = filterInvocation.getFullRequestUrl();
        String utente = authentication.getName();

        String token = filterInvocation.getRequest().getHeader("Authorization");
        if (token == null) {
            //sarebbero le chiamate public quindi faccio astenere il custom voter
            return ACCESS_ABSTAIN;
        }

        token = token.substring(7, token.length());


        return ACCESS_GRANTED;

    }

    @Override
    public boolean supports(Class aClass) {
        return true;
    }
}
