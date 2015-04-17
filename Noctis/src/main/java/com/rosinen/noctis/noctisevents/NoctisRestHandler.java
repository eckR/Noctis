package com.rosinen.noctis.noctisevents;

import com.rosinen.noctis.Model.NoctisEvent;
import com.rosinen.noctis.login.event.UserTokenDTO;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.RequiresHeader;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;

/**
 * Created by Simon on 11.04.2015.
 */
@Rest(rootUrl = "http://noctisapi.elasticbeanstalk.com/api", converters = { MappingJackson2HttpMessageConverter.class })
public interface NoctisRestHandler  {

//    String CONTENT_TYPE = "Content-Type";
//    String APPLICATION_JSON = "application/json";


    /**
     * AndroidAnnotations method to access the headers
     * @param name
     * @param value
     */
    void setHeader(String name, String value);

    /**
     * AndroidAnnotations method to access the headers
     * @param name
     * @return
     */
    String getHeader(String name);

    @Get("/events/?longitude={longitude}&latitude={latitude}&radius={radius}&dayIndex={dayIndex}")
//    @RequiresHeader(CONTENT_TYPE)
    List<NoctisEvent> getNoctisEvents(double longitude, double latitude, int radius, int dayIndex);

    @Post("/users")
    @RequiresHeader("Content-Type")
    void postFBUserToken(UserTokenDTO userToken);
}
