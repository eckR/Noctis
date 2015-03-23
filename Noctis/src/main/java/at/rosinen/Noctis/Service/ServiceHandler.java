package at.rosinen.Noctis.Service;

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

    private List<AbstractService> services = new ArrayList<AbstractService>();

    @AfterInject
    public void loadServices(){
        services.add(locationService);
    }


    public void startServices() {
        for (AbstractService service : services){
            service.onStart();
        }
    }

    public void stopServices() {
        for (AbstractService service : services){
            service.onStop();
        }
    }

}
