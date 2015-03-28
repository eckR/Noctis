package at.rosinen.Noctis.noctisevents;

import at.rosinen.Noctis.Model.NoctisEvent;
import at.rosinen.Noctis.base.AbstractService;
import at.rosinen.Noctis.noctisevents.event.NoctisEventsAvailableEvent;
import at.rosinen.Noctis.noctisevents.event.RequestEventsEvent;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;
import org.androidannotations.annotations.EBean;

import java.util.*;

/**
 * Created by Simon on 24.03.2015.
 */
@EBean
public class MockNoctisEventService extends AbstractService implements INoctisEventObtainer{


    @Override
    public void onEventBackgroundThread(RequestEventsEvent requestEventsEvent) {

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<NoctisEvent> events = new ArrayList<NoctisEvent>();
        NoctisEvent event1 = new NoctisEvent(1, "Testevent1", new Date(), new Date(), "Wien", 48.212804, 16.37701,
                "https://www.facebook.com/events/1554994964748892/", 451, "TEST",
                "https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-xfa1/v/t1.0-9/11041763_686504998125104_100975474360033640_n.jpg?oh=64e213161129ba7b03ae8971b141ff87&oe=55B83F2B&__gda__=1437562235_30aa0ca336c45e65abc328452ea397fa",
                "https://fbcdn-sphotos-h-a.akamaihd.net/hphotos-ak-xfp1/v/t1.0-9/10984284_445289895634434_6812078693844205424_n.png?oh=9ec8c709f7e06be94157824e1d704826&oe=55738288&__gda__=1434431745_13021f23ee78ac7512b3b608b355447b",
                5.0f);
        NoctisEvent event2 = new NoctisEvent(2, "Testeventw2", new Date(), new Date(), "Wien", 48.221904, 16.37701,
                "https://www.facebook.com/events/1554994964748892/", 451, "TEST",
                "https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-xfa1/v/t1.0-9/11041763_686504998125104_100975474360033640_n.jpg?oh=64e213161129ba7b03ae8971b141ff87&oe=55B83F2B&__gda__=1437562235_30aa0ca336c45e65abc328452ea397fa",
                "https://fbcdn-sphotos-h-a.akamaihd.net/hphotos-ak-xfp1/v/t1.0-9/10984284_445289895634434_6812078693844205424_n.png?oh=9ec8c709f7e06be94157824e1d704826&oe=55738288&__gda__=1434431745_13021f23ee78ac7512b3b608b355447b",
                5.0f);
        NoctisEvent event3 = new NoctisEvent(3, "Testevent3e", new Date(), new Date(), "Wien", 48.231304, 16.37701,
                "https://www.facebook.com/events/1554994964748892/", 451, "TEST",
                "https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-xfa1/v/t1.0-9/11041763_686504998125104_100975474360033640_n.jpg?oh=64e213161129ba7b03ae8971b141ff87&oe=55B83F2B&__gda__=1437562235_30aa0ca336c45e65abc328452ea397fa",
                "https://fbcdn-sphotos-h-a.akamaihd.net/hphotos-ak-xfp1/v/t1.0-9/10984284_445289895634434_6812078693844205424_n.png?oh=9ec8c709f7e06be94157824e1d704826&oe=55738288&__gda__=1434431745_13021f23ee78ac7512b3b608b355447b",
                5.0f);
        NoctisEvent event4 = new NoctisEvent(4, "Testevent4d", new Date(), new Date(), "Wien", 48.201104, 16.37701,
                "https://www.facebook.com/events/1554994964748892/", 451, "TEST",
                "https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-xfa1/v/t1.0-9/11041763_686504998125104_100975474360033640_n.jpg?oh=64e213161129ba7b03ae8971b141ff87&oe=55B83F2B&__gda__=1437562235_30aa0ca336c45e65abc328452ea397fa",
                "https://fbcdn-sphotos-h-a.akamaihd.net/hphotos-ak-xfp1/v/t1.0-9/10984284_445289895634434_6812078693844205424_n.png?oh=9ec8c709f7e06be94157824e1d704826&oe=55738288&__gda__=1434431745_13021f23ee78ac7512b3b608b355447b",
                5.0f);

        NoctisEvent event5 = new NoctisEvent(5, "Testevent5a", new Date(), new Date(), "Wien", 48.211404, 16.36701,
                "https://www.facebook.com/events/1554994964748892/", 451, "TEST",
                "https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-xfa1/v/t1.0-9/11041763_686504998125104_100975474360033640_n.jpg?oh=64e213161129ba7b03ae8971b141ff87&oe=55B83F2B&__gda__=1437562235_30aa0ca336c45e65abc328452ea397fa",
                "https://fbcdn-sphotos-h-a.akamaihd.net/hphotos-ak-xfp1/v/t1.0-9/10984284_445289895634434_6812078693844205424_n.png?oh=9ec8c709f7e06be94157824e1d704826&oe=55738288&__gda__=1434431745_13021f23ee78ac7512b3b608b355447b",
                5.0f);
        NoctisEvent event6 = new NoctisEvent(6, "Testevent1d", new Date(), new Date(), "Wien", 48.211604, 16.35701,
                "https://www.facebook.com/events/1554994964748892/", 451, "TEST",
                "https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-xfa1/v/t1.0-9/11041763_686504998125104_100975474360033640_n.jpg?oh=64e213161129ba7b03ae8971b141ff87&oe=55B83F2B&__gda__=1437562235_30aa0ca336c45e65abc328452ea397fa",
                "https://fbcdn-sphotos-h-a.akamaihd.net/hphotos-ak-xfp1/v/t1.0-9/10984284_445289895634434_6812078693844205424_n.png?oh=9ec8c709f7e06be94157824e1d704826&oe=55738288&__gda__=1434431745_13021f23ee78ac7512b3b608b355447b",
                5.0f);
        NoctisEvent event7 = new NoctisEvent(7, "Testevent1c", new Date(), new Date(), "Wien", 48.210404, 16.34701,
                "https://www.facebook.com/events/1554994964748892/", 451, "TEST",
                "https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-xfa1/v/t1.0-9/11041763_686504998125104_100975474360033640_n.jpg?oh=64e213161129ba7b03ae8971b141ff87&oe=55B83F2B&__gda__=1437562235_30aa0ca336c45e65abc328452ea397fa",
                "https://fbcdn-sphotos-h-a.akamaihd.net/hphotos-ak-xfp1/v/t1.0-9/10984284_445289895634434_6812078693844205424_n.png?oh=9ec8c709f7e06be94157824e1d704826&oe=55738288&__gda__=1434431745_13021f23ee78ac7512b3b608b355447b",
                5.0f);
        NoctisEvent event8 = new NoctisEvent(8, "Testevent1a", new Date(), new Date(), "Wien", 48.211604, 16.33701,
                "https://www.facebook.com/events/1554994964748892/", 451, "TEST",
                "https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-xfa1/v/t1.0-9/11041763_686504998125104_100975474360033640_n.jpg?oh=64e213161129ba7b03ae8971b141ff87&oe=55B83F2B&__gda__=1437562235_30aa0ca336c45e65abc328452ea397fa",
                "https://fbcdn-sphotos-h-a.akamaihd.net/hphotos-ak-xfp1/v/t1.0-9/10984284_445289895634434_6812078693844205424_n.png?oh=9ec8c709f7e06be94157824e1d704826&oe=55738288&__gda__=1434431745_13021f23ee78ac7512b3b608b355447b",
                5.0f);
        NoctisEvent event9 = new NoctisEvent(9, "Testevent1b", new Date(), new Date(), "Wien", 48.212604, 16.37751,
                "https://www.facebook.com/events/1554994964748892/", 451, "TEST",
                "https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-xfa1/v/t1.0-9/11041763_686504998125104_100975474360033640_n.jpg?oh=64e213161129ba7b03ae8971b141ff87&oe=55B83F2B&__gda__=1437562235_30aa0ca336c45e65abc328452ea397fa",
                "https://fbcdn-sphotos-h-a.akamaihd.net/hphotos-ak-xfp1/v/t1.0-9/10984284_445289895634434_6812078693844205424_n.png?oh=9ec8c709f7e06be94157824e1d704826&oe=55738288&__gda__=1434431745_13021f23ee78ac7512b3b608b355447b",
                5.0f);




        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        events.add(event5);
        events.add(event6);
        events.add(event7);
        events.add(event8);
        events.add(event9);
        Random rand = new Random(System.currentTimeMillis());

        for(NoctisEvent e: events){
            e.setLat(e.getCoords().latitude + rand.nextInt(25) /100 );
            e.setLng(e.getCoords().longitude + rand.nextInt(25) / 100);
        }
        Collections.shuffle(events, new Random(System.currentTimeMillis()));


        mEventBus.post(new NoctisEventsAvailableEvent(requestEventsEvent,events));
    }


}
