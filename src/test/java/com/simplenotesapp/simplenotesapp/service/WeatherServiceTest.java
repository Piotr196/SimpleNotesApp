package com.simplenotesapp.simplenotesapp.service;

import com.google.gson.JsonSyntaxException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(WeatherService.class)
@RunWith(SpringRunner.class)
public class WeatherServiceTest {
    @Autowired
    private WeatherService weatherService;

    @Autowired
    private MockRestServiceServer mockServer;

    private static String CLEAR_SKY = "clear sky";
    private static String BASE_URL = "http://weather";
    private static String CITY = "Lodz";
    private static String API_KEY = "XXX";
    private static String REQUEST_PARAMS = "?q=%s&APPID=%s";
    private static String RESPONSE = "{\"coord\":{\"lon\":19.47,\"lat\":51.75},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"base\":\"stations\",\"main\":{\"temp\":290.15,\"pressure\":1015,\"humidity\":72,\"temp_min\":290.15,\"temp_max\":290.15},\"visibility\":10000,\"wind\":{\"speed\":1},\"clouds\":{\"all\":0},\"dt\":1529276400,\"sys\":{\"type\":1,\"id\":5358,\"message\":0.0035,\"country\":\"PL\",\"sunrise\":1529202175,\"sunset\":1529262220},\"id\":3093133,\"name\":\"Lodz\",\"cod\":200}";
    private static String MALFORMED_RESPONSE = RESPONSE.split(",")[0] + '}';

    @Before
    public void setUp() {
        setField(weatherService, "baseUrl", BASE_URL);
        setField(weatherService, "apiKey", API_KEY);
    }

    @Test
    public void shouldReturnClearSky() {
        //given
        mockServer.expect(requestTo(BASE_URL + String.format(REQUEST_PARAMS, CITY, API_KEY)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(RESPONSE, MediaType.TEXT_PLAIN));
        //when
        String description = weatherService.getWeatherForCity(CITY);
        //then
        Assert.assertEquals(CLEAR_SKY, description);
    }

    @Test
    public void shouldThrowJsonSyntaxExceptionExceptionOnMalformedJsonParsing() {
        //given
        mockServer.expect(requestTo(BASE_URL + String.format(REQUEST_PARAMS, CITY, API_KEY)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(MALFORMED_RESPONSE, MediaType.TEXT_PLAIN));
        //when
        //then
        assertThatThrownBy(() -> weatherService.getWeatherForCity(CITY)).isInstanceOf(JsonSyntaxException.class)
        .hasMessage("java.io.EOFException: End of input at line 1 column 23 path $.coord");
    }
}