package com.rosinen.noctis.base;

import com.rosinen.noctis.location.LocationService;
import com.rosinen.noctis.map.MarkerService;
import com.rosinen.noctis.noctisevents.ImageService;
import com.rosinen.noctis.noctisevents.MockNoctisEventService;
import com.rosinen.noctis.noctisevents.NoctisEventService;
import hugo.weaving.DebugLog;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EBean.Scope;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 23.03.2015.
 */
@EBean(scope = Scope.Singleton)
public class ServiceHandler {

    @Bean
    LocationService locationService;

//    @Bean
//    MockNoctisEventService mockNoctisEventService;

    @Bean
    ImageService imageService;

    @Bean
    MarkerService markerService;

    @Bean
    NoctisEventService noctisEventService;

    private List<AbstractService> services = new ArrayList<AbstractService>();

    @AfterInject
    public void loadServices(){
        services.add(locationService);
//        services.add(mockNoctisEventService);
        services.add(imageService);
        services.add(markerService);
        services.add(noctisEventService);
    }

    @DebugLog
    public void startServices(){
        for(AbstractService service :services){
            service.onStart();
        }
    }

    @DebugLog
    public void stopServices() {
        for (AbstractService service : services){
            service.onStop();
        }
    }
}
