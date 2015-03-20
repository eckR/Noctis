package at.rosinen.Noctis;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;
import at.rosinen.Noctis.events.TestEvent;
import de.greenrobot.event.EventBus;
import org.androidannotations.annotations.*;

@EActivity(R.layout.activity_splash_screen)
public class SplashScreenActivity extends Activity {

    @ViewById
    TextView hello_world;

    @AfterInject
    public void afterInject(){
        EventBus.getDefault().register(this);
        backgrndTest();
        Log.d("XXXX","afterInject");    }



    @Background
    public void backgrndTest(){
        try {
            Thread.sleep(5000);
            EventBus.getDefault().post(new TestEvent("Hallo"));
        }catch (Exception e){

        }
    }

    @UiThread
    public void uiTrhread(){
        hello_world.setText("Hallo :)");
    }


    public void onEvent(TestEvent ev){
        uiTrhread();
    }
}
