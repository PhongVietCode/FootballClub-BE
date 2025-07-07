package com.example.footballclub.service;

import com.example.footballclub.dto.request.AuthenticationRequest;
import com.example.footballclub.dto.response.AuthenticationResponse;
import com.example.footballclub.dto.response.IntrospectResponse;
import com.example.footballclub.entity.User;
import com.example.footballclub.repository.UserRepository;
import com.example.footballclub.exception.AppException;
import com.example.footballclub.exception.ErrorCode;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    TokenService tokenService;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = userRepository
                .findByFullName(request.getUserName())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_USER));
        boolean isAuthenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!isAuthenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String token = tokenService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public IntrospectResponse introspect(String token) throws ParseException, JOSEException {
        try {
            tokenService.verifyToken(token, false);
        } catch (AppException e) {
            return IntrospectResponse.builder().valid(false).build();
        }
        return IntrospectResponse.builder().valid(true).build();
    }

//    public void logout(LogoutRequest request) throws ParseException, JOSEException {
//        try {
//            var signedToken = verifyToken(request.getToken(), true);
//            String jit = signedToken.getJWTClaimsSet().getJWTID();
//            Date expiryDate = signedToken.getJWTClaimsSet().getExpirationTime();
//
//            InvalidatedToken invalidatedToken =
//                    InvalidatedToken.builder().id(jit).expiryTime(expiryDate).build();
//            invalidatedTokenRepository.save(invalidatedToken);
//        } catch (AppException ignored) {
//
//        }
//    }
//
//    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
//        var signedJWT = verifyToken(request.getToken(), true);
//        String jit = signedJWT.getJWTClaimsSet().getJWTID();
//        Date expiryDate = signedJWT.getJWTClaimsSet().getExpirationTime();
//        InvalidatedToken invalidatedToken =
//                InvalidatedToken.builder().id(jit).expiryTime(expiryDate).build();
//        invalidatedTokenRepository.save(invalidatedToken);
//
//        var username = signedJWT.getJWTClaimsSet().getSubject();
//        var user =
//                userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
//
//        var token = generateToken(user);
//        return AuthenticationResponse.builder()
//                .isAuthenticated(true)
//                .token(token)
//                .build();
//    }
//
//    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
//        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
//        SignedJWT signedJWT = SignedJWT.parse(token);
//        boolean verified = signedJWT.verify(verifier);
//        String jit = signedJWT.getJWTClaimsSet().getJWTID();
//
//        Date expiryTime = isRefresh
//                ? new Date(signedJWT
//                        .getJWTClaimsSet()
//                        .getIssueTime()
//                        .toInstant()
//                        .plus(REFRESH_DURATION, ChronoUnit.SECONDS)
//                        .toEpochMilli())
//                : signedJWT.getJWTClaimsSet().getExpirationTime();
//        if (!(verified && expiryTime.after(new Date()))) {
//            throw new AppException(ErrorCode.UNAUTHENTICATED);
//        }
////        if (invalidatedTokenRepository.existsById(jit)) {
////            throw new AppException(ErrorCode.UNAUTHENTICATED);
////        }
//        return signedJWT;
//    }
//
//    String generateToken(User user) {
//        //        Define which algorithm will be used
//        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
//
//        //        Claim: data in body
//        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
//                .subject(user.getId()) // Used by authentication.getName()
//                .issuer("PhongVietCode")
//                .issueTime(new Date())
//                .expirationTime(new Date(
//                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
//                .claim("scope", buildScope(user))
//                .jwtID(UUID.randomUUID().toString())
//                .build();
//        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
//        //        Create jwt object
//        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
//
//        try {
//            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
//        } catch (JOSEException e) {
//            log.error("Cannot create token", e);
//            throw new RuntimeException(e);
//        }
//        return jwsObject.serialize();
//    }
//
//    private String buildScope(User user) {
//        StringJoiner stringJoiner = new StringJoiner(" ");
////        if (!CollectionUtils.isEmpty(user.getRoles())) {
////            user.getRoles().forEach(role -> {
////                stringJoiner.add("ROLE_" + role.getName());
////                if (!CollectionUtils.isEmpty(role.getPermissions())) {
////                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
////                }
////            });
////        }
//        return stringJoiner.toString();
//    }
}
