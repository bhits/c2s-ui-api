package gov.samhsa.c2s.c2suiapi.service;

public interface PlsService {

    Object searchProviders(String state, String city, String zipCode,
                           String firstName, String lastName, String genderCode,
                           String orgName, String phone, String page,
                           String size, String sort, String projection,
                           String xForwardedProto, String xForwardedHost,
                           String xForwardedPrefix, String xForwardedPort);

    Object getStateCodes();
}