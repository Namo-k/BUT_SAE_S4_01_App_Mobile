package fr.iut.sae_s4_01_app_mobile;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.List;

import fr.iut.sae_s4_01_app_mobile.bd.Notifications;

public class NotificationActivity extends AppCompatActivity {
    private ListView listView;
    private TextView nbNotification_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        listView = findViewById(R.id.notificationListView);

        nbNotification_ = findViewById(R.id.nbNotifications);

        UserId myApp = (UserId) getApplication();
        int userID = myApp.getUserID();

        Notifications notificationsdB = new Notifications(this);
        int totalNotifCount = notificationsdB.getNombreTotalAlertes(userID);

        List<Notification> notifications = notificationsdB.getAllNotifications(userID,this);

        nbNotification_.append(getResources().getString(R.string.avoir) + " " + totalNotifCount + " " + getResources().getString(R.string.nbNotif));

        NotificationAdapter adapter = new NotificationAdapter(this, notifications, R.layout.item_notification);
        listView.setAdapter(adapter);

        TextView sondageBtn = findViewById(R.id.sondage);

        for (Notification notification : notifications) {
            if (notification.getTitle().equalsIgnoreCase("Votre avis compte") || notification.getTitle().equalsIgnoreCase("Your opinion matters")) {
                sondageBtn.setVisibility(View.VISIBLE);
            }
        }

        sondageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationActivity.this, SondageActivity.class);
                startActivity(intent);
            }
        });


        ImageView btnNotif = (ImageView) findViewById(R.id.notifBtn);


        ImageView sedeconnecterBtn = (ImageView) findViewById(R.id.sedeconnecterBtn);
        sedeconnecterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnNotif.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });


    }


}
