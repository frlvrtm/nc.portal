package com.nc.portal.service;

import com.nc.portal.model.AuthThreadLocal;
import com.nc.portal.model.PriceDto;
import com.nc.portal.model.RoleThreadLocal;
import com.nc.portal.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class PriceService {

    private final RestTemplate restTemplate = new RestTemplate();

    public Map getPrice(HttpServletResponse res, String pointFrom, String pointTo) {
        try {
            //создание куков
            CookieUtil.create(res, pointFrom, pointTo, false);

            PriceDto priceDto = new PriceDto(pointFrom, pointTo);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<PriceDto> entity = new HttpEntity<>(priceDto, headers);
            ResponseEntity<Map> response = restTemplate.exchange(GlobalConstants.URL_PRICE, HttpMethod.POST, entity, Map.class);
            return response.getBody();
        } catch (Exception e) {
            log.debug("** Exception: " + e.getMessage());
        }
        return new HashMap();
    }
}
