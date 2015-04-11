package com.rosinen.noctis.noctisevents;

import com.rosinen.noctis.Model.NoctisEvent;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;

/**
 * Created by Simon on 11.04.2015.
 */
@Rest(rootUrl = "http://noctisapi.elasticbeanstalk.com/api", converters = { MappingJackson2HttpMessageConverter.class })

public interface NoctisRestHandler  {

    @Get("/events/?longitude={longitude}&latitude={latitude}&radius={radius}&dayIndex={dayIndex}")
    List<NoctisEvent> getNoctisEvents(double longitude, double latitude, int radius, int dayIndex);
}
