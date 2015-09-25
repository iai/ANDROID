package dbvtech.com.br.mypushproject.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Created by Paulo on 29/07/2015.
 */
public class GcmHelper {
    public static SharedPreferences getPreferences(Context context){
        SharedPreferences preferences = context.getSharedPreferences("preferencias_gcm", Context.MODE_PRIVATE);
        return preferences;
    }

    public static String registrarNoGoogle(Context context, String numeroProjeto){
        String idRegistro = "";

        try{
            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
            idRegistro = gcm.register(numeroProjeto);
        }
        catch (Exception ex){
            Log.e("GcmHelper", ex.getMessage());
        }

        return idRegistro;
    }

    public static void cancelaRegistro(Context context){
        try{
            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
            gcm.unregister();
            saveId(context, null);
        }
        catch (Exception ex){
            Log.e("GcmHelper", ex.getMessage());
        }
    }

    public static void saveId(Context context, String id){
        SharedPreferences preferences = getPreferences(context);
        preferences.edit().putString("registration_id", id).commit();
    }

    public static String getIdRegistro(Context context){
        String id = getPreferences(context).getString("registration_id", "");
        return (id == null || id.trim().length() == 0) ? "" : id;
    }

    public static boolean isAtivo(Context context){
        String id = getIdRegistro(context);
        return (id == null || id.trim().length() == 0) ? false : true;
    }
}
