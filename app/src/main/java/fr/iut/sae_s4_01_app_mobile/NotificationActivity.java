package fr.iut.sae_s4_01_app_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

        List<Notification> notifications = notificationsdB.getAllNotifications(userID);

        nbNotification_.append("Vous avez " + totalNotifCount +  " notifications au total");

        NotificationAdapter adapter = new NotificationAdapter(this, notifications, R.layout.item_notification);
        listView.setAdapter(adapter);

        TextView sondageBtn = findViewById(R.id.sondage);

        for (Notification notification : notifications) {
            if (notification.getTitle().equalsIgnoreCase("Votre avis compte")) {
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

        ImageView btnAncienne = (ImageView) findViewById(R.id.ancienneBtn);
        ImageView btnProfil = (ImageView) findViewById(R.id.userBtn);
        ImageView btnNotif = (ImageView) findViewById(R.id.notifBtn);
        ImageView btnHome = (ImageView) findViewById(R.id.homeBtn);

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

        btnAncienne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationActivity.this, ancienneAlerteActivity.class);
                startActivity(intent);
            }
        });
        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationActivity.this, ProfilActivity.class);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationActivity.this, AccueilActivity.class);
                startActivity(intent);
            }
        });
    }

}
