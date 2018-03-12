package edu.hope.cs.petertimperman.voice_id_demo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import io.voiceit.voiceit.VoiceIt;


public class MainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO}, 0);
        }

        final Verifier verifier = new Verifier();
        final Profile jipping = new Profile("Micheal", "Jipping");
        //verifier.createUser(jipping);
        final Button closeButton = (Button) this.findViewById(R.id.verify);
        closeButton.setBackgroundColor(Color.parseColor("#ff99cc00"));
        //closeButton.setPadding(400,400,400,400);
        closeButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                closeButton.setBackgroundColor(Color.parseColor("#ff0000"));
                verifier.authenicateUser(jipping);
                //verifier.enrollUser(peter);
                closeButton.setBackgroundColor(Color.parseColor("#ff99cc00"));
            }
        });
        final Button enrollButton = (Button) this.findViewById(R.id.enroll);
        enrollButton.setBackgroundColor(Color.parseColor("#ff99cc00"));
        //closeButton.setPadding(400,400,400,400);
        enrollButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                enrollButton.setBackgroundColor(Color.parseColor("#ff0000"));
                //verifier.authenicateUser(peter);
                verifier.enrollUser(jipping);
                enrollButton.setBackgroundColor(Color.parseColor("#ff99cc00"));

            }
        });
    }


}

class Verifier {
    private final VoiceIt myVoiceIt;
    public Verifier(){
        this.myVoiceIt = new VoiceIt("a5e80eb0c49b45d9a40b28f13b6747d4");



    }
    public void createUser(Profile profile){
        this.myVoiceIt.createUser(profile.getUserID(), profile.getPassword(),new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("JSONResult : " + response.toString());
            }


            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse){
                if (errorResponse != null) {
                    System.out.println("JSONResult : " + errorResponse.toString());
                }
            }

        });
    }

    public void enrollUser(Profile profile) {
        myVoiceIt.createEnrollment(profile.getUserID(), profile.getPassword(), "en-US", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("JSONResult : " + response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (errorResponse != null) {
                    System.out.println("JSONResult : " + errorResponse.toString());
                }
            }

        });
    }

    public void authenicateUser(Profile profile) {
        myVoiceIt.authentication(profile.getUserID(), profile.getPassword(), "en-US", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("JSONResult : " + response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (errorResponse != null) {
                    System.out.println("JSONResult : " + errorResponse.toString());
                }
            }

        });
    }


}
 class Profile{
    private  String firstName;
    private String lastName;
    private String userID;
    private String password;
    //private someThing masterVoicePrint;



    public Profile(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = "Demo";
        this.userID = new String(Integer.toString((firstName+lastName).hashCode()));

    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    @Override
    public String toString() {
        return "Profile{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Profile profile = (Profile) o;

        if (!getFirstName().equals(profile.getFirstName())) return false;
        return lastName.equals(profile.lastName);
    }

    @Override
    public int hashCode() {
        int result = getFirstName().hashCode();
        result = 31 * result + lastName.hashCode();
        return result;
    }
}