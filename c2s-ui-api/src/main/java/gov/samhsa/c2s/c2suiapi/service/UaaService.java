package gov.samhsa.c2s.c2suiapi.service;


import gov.samhsa.c2s.c2suiapi.service.dto.LoginRequestDto;

public interface UaaService {
    Object login(LoginRequestDto requestDto);
}
