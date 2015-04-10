package com.rosinen.noctis.eventdetail;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.rosinen.noctis.R;
import com.rosinen.noctis.Model.NoctisEvent;
import com.rosinen.noctis.activity.event.ShowDetailsEvent;
import com.rosinen.noctis.base.EventBusFragment;
import com.rosinen.noctis.noctisevents.event.ImageDownloadAvailableEvent;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by harald on 3/28/15.
 */
@EFragment(R.layout.fragment_event_detail_pager)
public class EventDetailPagerFragment extends EventBusFragment {

    //Test for Jenkins

    @ViewById
    ImageView eventImage;

    @ViewById
    TextView eventLocation;

    @ViewById
    TextView eventTitle;

    @ViewById
    ViewPager detailViewPager;

    EventDetailPagerAdapter adapter;

    private int day;

    @AfterViews
    void prepare() {
        adapter = new EventDetailPagerAdapter(getFragmentManager());
        detailViewPager.setAdapter(adapter);
        detailViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                updateHeader(adapter.getItemByPosition(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    //Onclick for Event Buttons
    @Click(R.id.eventFacebook)
    void facebookButtonClick() {
        Context context = getActivity().getApplicationContext();
        Intent intent;
        long fbId = adapter.getItemByPosition(detailViewPager.getCurrentItem()).getFBID();
        Log.d("FBID", fbId+"");
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://event/" + fbId));

        } catch (PackageManager.NameNotFoundException e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/events/" + fbId));
        }
        startActivity(intent);
    }

    @Click(R.id.eventJoin)
    void eventJoinClick() {
        Log.d("ClickTEst", "Event join clicked");
    }

    @Click(R.id.eventRoute)
    void eventRouteClick() {
        Context context = getActivity().getApplicationContext();
        NoctisEvent event = adapter.getItemByPosition(detailViewPager.getCurrentItem());
        String url;
        try {
            context.getPackageManager().getPackageInfo("com.google.android.apps.maps", 0);
            url="http://maps.google.com/maps?daddr="+event.getCoords().latitude+","+event.getCoords().longitude;
        } catch (PackageManager.NameNotFoundException e) {
            url="geo:" + event.getCoords().latitude + "," + event.getCoords().longitude;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);

    }


    public void onEventMainThread(ShowDetailsEvent event) {
        this.day =  event.getDay();
        adapter.setNoctisEventList(event.getEvents());
        adapter.notifyDataSetChanged();
        updateHeader(event.getEvents().get(event.getClickedPosition()));
        detailViewPager.setCurrentItem(event.getClickedPosition());

    }

    public void onEvent(final ImageDownloadAvailableEvent imgDownloadAvailableEvent) {
        if (imgDownloadAvailableEvent.day != day) {
            return;
        }
        adapter.notifyDataSetChanged();
        updateHeader(adapter.getItemByPosition(detailViewPager.getCurrentItem()));

    }

    private void updateHeader(NoctisEvent event) {
        eventLocation.setText(event.getLocation());
        eventTitle.setText(event.getName());
        eventImage.setImageBitmap(event.getPictureBig());
    }

    public void setDay(int day) {
        this.day = day;
    }

}
