package smaxd.aphina_02;

import android.content.Intent;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

public class ApplicationVk extends android.app.Application {

        VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
            @Override
            public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
                if (newToken == null) {
                    Intent Intent = new Intent(ApplicationVk.this,MainActivity.class);
                    Intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(Intent);
                }
            }
        };
        @Override
        public void onCreate() {
            super.onCreate();

            vkAccessTokenTracker.startTracking();
            VKSdk.initialize(this);
        }
    }
//kekshpektest