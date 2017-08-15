package gov.samhsa.c2s.c2suiapi.service;

import gov.samhsa.c2s.c2suiapi.infrastructure.UmsLookupClient;
import gov.samhsa.c2s.c2suiapi.service.dto.UserCreationLookupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UmsLookupServiceImpl implements UmsLookupService {

    @Autowired
    private UmsLookupClient umsLookupClient;

    @Override
    public UserCreationLookupDto getUserCreationLookupInfo() {
        Locale locale = LocaleContextHolder.getLocale();
        return UserCreationLookupDto.builder()
                .genderCodes(umsLookupClient.getGenderCodes(locale))
                .stateCodes(umsLookupClient.getStateCodes())
                .countryCodes(umsLookupClient.getCountryCodes())
                .locales(umsLookupClient.getLocales())
                .roles(umsLookupClient.getRoles(locale))
                .identifierSystems(umsLookupClient.getIdentifierSystem())
                .build();
    }
}
