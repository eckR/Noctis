package com.rosinen.noctis.noctisevents;

import com.rosinen.noctis.Model.NoctisEvent;
import com.rosinen.noctis.base.AbstractService;
import com.rosinen.noctis.noctisevents.event.NoctisEventsAvailableEvent;
import com.rosinen.noctis.noctisevents.event.RequestEventsEvent;
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


        NoctisEvent event1 = new NoctisEvent(601771249956926l, "Testevent1", new Date(), new Date(), "Wien", 48.212804, 16.37701,
                "https://www.facebook.com/events/1554994964748892/", 104,
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.   \n" +
                        "\n" +
                        "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.   \n" +
                        "\n" +
                        "Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.   \n" +
                        "\n" +
                        "Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer possim assum. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat.   \n" +
                        "\n" +
                        "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis.   \n" +
                        "\n" +
                        "At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, At accusam aliquyam diam diam dolore dolores duo eirmod eos erat, et nonumy sed tempor et et invidunt justo labore Stet clita ea et gubergren, kasd magna no rebum. sanctus sea sed takimata ut vero voluptua. est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur",
                "https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-xpa1/v/t1.0-9/11082505_599694640131085_5971199637910637214_n.jpg?oh=00cb80de925d7148764be233b4e12705&oe=5571CA14&__gda__=1436801964_a3b0d5ea72e74ad7d13cd82599ecfd4a",
                "https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-xpa1/v/t1.0-9/11082505_599694640131085_5971199637910637214_n.jpg?oh=00cb80de925d7148764be233b4e12705&oe=5571CA14&__gda__=1436801964_a3b0d5ea72e74ad7d13cd82599ecfd4a",
                5.0f);
        NoctisEvent event2 = new NoctisEvent(601771249956926l, "Testeventw2", new Date(), new Date(), "Wien", 48.221904, 16.37701,
                "https://www.facebook.com/events/1554994964748892/", 19, "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.   \n" +
                "\n" +
                "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.   \n" +
                "\n" +
                "Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.   \n" +
                "\n" +
                "Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer possim assum. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat.   \n" +
                "\n" +
                "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis.   \n" +
                "\n" +
                "At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, At accusam aliquyam diam diam dolore dolores duo eirmod eos erat, et nonumy sed tempor et et invidunt justo labore Stet clita ea et gubergren, kasd magna no rebum. sanctus sea sed takimata ut vero voluptua. est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur",
                "https://fbcdn-sphotos-c-a.akamaihd.net/hphotos-ak-xfa1/v/t1.0-9/11094680_864578546922454_8289196260921086913_n.jpg?oh=6aa3dacb1b6d2d68579710497cce64bf&oe=559BF229&__gda__=1436267807_7ce7d66730f966048408667745fc4580",
                "https://fbcdn-sphotos-c-a.akamaihd.net/hphotos-ak-xfa1/v/t1.0-9/11094680_864578546922454_8289196260921086913_n.jpg?oh=6aa3dacb1b6d2d68579710497cce64bf&oe=559BF229&__gda__=1436267807_7ce7d66730f966048408667745fc4580",
                5.0f);
        NoctisEvent event3 = new NoctisEvent(601771249956926l, "Testevent3e", new Date(), new Date(), "Wien", 48.231304, 16.37701,
                "https://www.facebook.com/events/1554994964748892/", 69, "TEST",
                "https://scontent-vie.xx.fbcdn.net/hphotos-xap1/v/t1.0-9/10945560_401326513375098_5170984536046170314_n.jpg?oh=78573be9082eb98ad16f52736f5025e2&oe=55ADCC83",
                "https://scontent-vie.xx.fbcdn.net/hphotos-xap1/v/t1.0-9/10945560_401326513375098_5170984536046170314_n.jpg?oh=78573be9082eb98ad16f52736f5025e2&oe=55ADCC83",
                5.0f);
        NoctisEvent event4 = new NoctisEvent(601771249956926l, "Testevent4d", new Date(), new Date(), "Wien", 48.201104, 16.37701,
                "https://www.facebook.com/events/1554994964748892/", 49, "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.   \n" +
                "\n" +
                "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.   \n" +
                "\n" +
                "Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.   \n" +
                "\n" +
                "Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer possim assum. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat.   \n" +
                "\n" +
                "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis.   \n" +
                "\n" +
                "At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, At accusam aliquyam diam diam dolore dolores duo eirmod eos erat, et nonumy sed tempor et et invidunt justo labore Stet clita ea et gubergren, kasd magna no rebum. sanctus sea sed takimata ut vero voluptua. est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur",
                "https://fbcdn-sphotos-f-a.akamaihd.net/hphotos-ak-xpf1/v/t1.0-9/11008396_1430382743921841_2449632854283043499_n.jpg?oh=63e9f77a454f4a04092d4eba3085b628&oe=55BAFAEA&__gda__=1437233621_44a42b4f494f29123a3752fa13f60bd5",
                "https://fbcdn-sphotos-f-a.akamaihd.net/hphotos-ak-xpf1/v/t1.0-9/11008396_1430382743921841_2449632854283043499_n.jpg?oh=63e9f77a454f4a04092d4eba3085b628&oe=55BAFAEA&__gda__=1437233621_44a42b4f494f29123a3752fa13f60bd5",
                5.0f);

        NoctisEvent event5 = new NoctisEvent(601771249956926l, "Testevent5a", new Date(), new Date(), "Wien", 48.211404, 16.36701,
                "https://www.facebook.com/events/1554994964748892/", 98, "TEST",
                "https://fbcdn-sphotos-h-a.akamaihd.net/hphotos-ak-xaf1/v/t1.0-9/11096699_1439173166376132_270458956778990565_n.jpg?oh=e5e51624ceeb93edb996e508693037f1&oe=55BA9B91&__gda__=1436520992_6d4a4563a9e38d102383e16fe3601fcb",
                "https://fbcdn-sphotos-h-a.akamaihd.net/hphotos-ak-xaf1/v/t1.0-9/11096699_1439173166376132_270458956778990565_n.jpg?oh=e5e51624ceeb93edb996e508693037f1&oe=55BA9B91&__gda__=1436520992_6d4a4563a9e38d102383e16fe3601fcb",
                5.0f);
        NoctisEvent event6 = new NoctisEvent(601771249956926l, "Testevent1d", new Date(), new Date(), "Wien", 48.211604, 16.35701,
                "https://www.facebook.com/events/1554994964748892/", 151, "TEST",
                "https://scontent-vie.xx.fbcdn.net/hphotos-xpf1/v/t1.0-9/19324_1436750913285024_6248826369697937571_n.jpg?oh=011a6d07ae1ce62a2c19cfdaa08314df&oe=55B0B899",
                "https://scontent-vie.xx.fbcdn.net/hphotos-xpf1/v/t1.0-9/19324_1436750913285024_6248826369697937571_n.jpg?oh=011a6d07ae1ce62a2c19cfdaa08314df&oe=55B0B899",
                5.0f);
        NoctisEvent event7 = new NoctisEvent(601771249956926l, "Testevent1c", new Date(), new Date(), "Wien", 48.210404, 16.34701,
                "https://www.facebook.com/events/1554994964748892/", 351, "TEST",
                "https://fbcdn-sphotos-b-a.akamaihd.net/hphotos-ak-xpf1/v/t1.0-9/10413309_335307276680152_2225400429427656336_n.jpg?oh=4d7980e931ddaee79397e2cba9ffbe2e&oe=55AF22B6&__gda__=1437530332_bfe4e7457db67a4840db2171cc173fd1",
                "https://fbcdn-sphotos-b-a.akamaihd.net/hphotos-ak-xpf1/v/t1.0-9/10413309_335307276680152_2225400429427656336_n.jpg?oh=4d7980e931ddaee79397e2cba9ffbe2e&oe=55AF22B6&__gda__=1437530332_bfe4e7457db67a4840db2171cc173fd1",
                5.0f);
        NoctisEvent event8 = new NoctisEvent(601771249956926l, "Testevent1a", new Date(), new Date(), "Wien", 48.211604, 16.33701,
                "https://www.facebook.com/events/1554994964748892/", 51, "TEST",
                "https://scontent-vie.xx.fbcdn.net/hphotos-xfa1/v/t1.0-9/11064623_805349862887192_4508974517486759257_n.jpg?oh=6973a72352d9ad09658b76d7d8f12cb9&oe=55AAD5B3",
                "https://scontent-vie.xx.fbcdn.net/hphotos-xfa1/v/t1.0-9/11064623_805349862887192_4508974517486759257_n.jpg?oh=6973a72352d9ad09658b76d7d8f12cb9&oe=55AAD5B3",
                5.0f);
        NoctisEvent event9 = new NoctisEvent(601771249956926l, "Testevent1b", new Date(), new Date(), "Wien", 48.212604, 16.37751,
                "https://www.facebook.com/events/1554994964748892/", 45, "TEST",
                "https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-xfa1/v/t1.0-9/11041763_686504998125104_100975474360033640_n.jpg?oh=64e213161129ba7b03ae8971b141ff87&oe=55B83F2B&__gda__=1437562235_30aa0ca336c45e65abc328452ea397fa",
                "https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-xfa1/v/t1.0-9/11041763_686504998125104_100975474360033640_n.jpg?oh=64e213161129ba7b03ae8971b141ff87&oe=55B83F2B&__gda__=1437562235_30aa0ca336c45e65abc328452ea397fa",
                5.0f);


        switch (requestEventsEvent.day){
            case 0:{
                events.add(event9);
                events.add(event4);
                events.add(event3);
            } break;
            case 1:{
                events.add(event2);
                events.add(event5);
                events.add(event6);
                events.add(event7);

            }break;
            case 2:{
                events.add(event8);
                events.add(event1);
            }break;
        }



        Random rand = new Random(System.currentTimeMillis());

        for(NoctisEvent e: events){
            e.setLat(e.getCoords().latitude + ((double) rand.nextInt(25)) / 1000);
            e.setLng(e.getCoords().longitude + ((double) rand.nextInt(25)) / 1000);
        }
        Collections.shuffle(events, new Random(System.currentTimeMillis()));


        mEventBus.post(new NoctisEventsAvailableEvent(requestEventsEvent,events));
    }


}
