package ikish.aftab.ws.android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import android.content.SharedPreferences
import android.content.res.Configuration
import com.cedarstudios.cedarmapssdk.CedarMaps
import com.cedarstudios.cedarmapssdk.model.MapID
import com.securepreferences.SecurePreferences
import java.util.*

@HiltAndroidApp
class MyApp : Application() {
    @Inject
    lateinit var sharedPreferences: SecurePreferences


    override fun onCreate() {

        /*       if (retrofit == null) {

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build();

            Gson gson = new GsonBuilder()
                    .enableComplexMapKeySerialization()
                    .serializeNulls()
                    .setDateFormat(DateFormat.LONG)
                    .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                    .setPrettyPrinting()
                    .setVersion(1.0)
                    .create();

            String baseUrl = "http://ikish.aftabproducts.com/api/v1/";

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();

            api = retrofit.create(API.class);
        }*/
        super.onCreate()


        //  sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //  editor = sharedPreferences.edit();
        var uuid = sharedPreferences!!.getString("uuid", "")
        if (uuid == "") {
            uuid = UUID.randomUUID().toString()
            sharedPreferences.edit().putString("uuid", uuid).apply()
        }
        CedarMaps.getInstance()
            .setClientID("sportapp-6594917192157661130")
            .setClientSecret("b2uejHNwb3J0YXBw4V7hZnhRDiV3fQ8aqbTay-mjSd1IXllmWRN1EezGsss=")
            .setContext(this).mapID = MapID.MIX
    }
}