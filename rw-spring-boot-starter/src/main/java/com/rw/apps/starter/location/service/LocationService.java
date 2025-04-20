package com.rw.apps.starter.location.service;

import com.rw.apps.starter.common.api.util.HttpRequestUtil;
import com.rw.apps.starter.common.model.Country;
import com.rw.apps.starter.location.model.CountryInfo;
import com.rw.apps.starter.location.model.IpAddressCountryResponse;
import java.util.Set;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

@Service
public class LocationService {
    private static final Logger log = LoggerFactory.getLogger(LocationService.class);
    private static final Set<String> LOCAL_IP_ADDRESSES = Set.of("0.0.0.0", "127.0.0.1");
    private final RestOperations locationServiceRestOperations;

    public LocationService(RestOperations locationServiceRestOperations) {
        this.locationServiceRestOperations = locationServiceRestOperations;
    }

    public CountryInfo resolveCountryInfo() {
        String ipAddress = HttpRequestUtil.resolveClientIpAddress();
        Country country;
        if (LOCAL_IP_ADDRESSES.contains(ipAddress)) {
            country = Country.NL;
        } else {
            String countryFromIpAddress = resolveCountryFromIpAddress(ipAddress);
            country = resolveOperationCountry(countryFromIpAddress);
        }
        return new CountryInfo(country.name(), country.getCurrency().name());
    }

    String resolveCountryFromIpAddress(String ipAddress) {
        IpAddressCountryResponse countryResponse = locationServiceRestOperations.getForObject("/{ipAddress}",
                                                                                              IpAddressCountryResponse.class,
                                                                                              ipAddress);
        if (countryResponse == null) {
            return null;
        }
        return countryResponse.country();
    }

    private Country resolveOperationCountry(String countryCode) {
        Country country = EnumUtils.getEnum(Country.class, countryCode);
        if (country == null) {
            log.warn("Not country of operation '{}'", countryCode);
            throw new IllegalStateException("Sorry! We don't operate in your country");
        }
        return country;
    }
}
