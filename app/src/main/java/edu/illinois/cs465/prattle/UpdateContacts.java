package edu.illinois.cs465.prattle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.illinois.cs465.prattle.data.HangoutModel;
import edu.illinois.cs465.prattle.data.MyHangoutsAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

enum ContactTag {OFTEN, SOMETIMES, NEVER}

public class UpdateContacts extends AppCompatActivity {
    public static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    ContactAdapter contactAdapter = null;
    List<ContactInfo> contactList;
    HashMap<String, ContactTag> savedContactTags;
    ListView listView;
    ImageButton helpButton;
    Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contacts);
        listView = findViewById(R.id.listContacts);

        contactList = new ArrayList<>();
        readUserContacts();
        savedContactTags = savedContactTagMap(getSavedContacts(UpdateContacts.this));
        for (ContactInfo contact : contactList) {
            if (savedContactTags.containsKey(contact.getName())) {
                contact.setTag(savedContactTags.get(contact.getName()));
            }
        }

        contactAdapter = new ContactAdapter(UpdateContacts.this, R.layout.contact_info, contactList);
        listView.setAdapter(contactAdapter);

        helpButton = findViewById(R.id.help_button);
        helpButton.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(UpdateContacts.this);
            builder.setTitle(getResources().getString(R.string.contacts_help_title));
            builder.setMessage(getResources().getString(R.string.contacts_help_message)).setPositiveButton("OK", (dialog, id) -> {});
            builder.create();
            builder.show();
        });
        doneButton = findViewById(R.id.doneEditingContacts);
        doneButton.setOnClickListener(view -> {
            writeContacts();
            Intent mainIntent = new Intent(UpdateContacts.this, MainActivity.class);
            startActivity(mainIntent);
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        writeContacts();
    }

    private static Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private static String getStringFromBitmap(Bitmap bitmap) {
        final int COMPRESSION_QUALITY = 100;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    private static Bitmap getBitmapFromString(String string) {
        byte[] decodedString = Base64.decode(string, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    private void getContacts() {
        String displayName;
        String imageUri;
        Bitmap photo;
        Drawable contactDrawable = getResources().getDrawable(R.drawable.ic_baseline_account_circle_gray_24);
        Bitmap defaultPhoto = getBitmapFromDrawable(contactDrawable);
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                imageUri = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));

                ContactInfo contactInfo = new ContactInfo();
                contactInfo.setName(displayName);
                contactInfo.setTag(ContactTag.NEVER);
                contactInfo.setPhoto(defaultPhoto);
                if (imageUri != null) {
                    try {
                        photo = MediaStore.Images.Media
                                .getBitmap(getApplicationContext().getContentResolver(),
                                        Uri.parse(imageUri));
                        contactInfo.setPhoto(photo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                contactList.add(contactInfo);
            }
        }
        cursor.close();
    }

    public void readUserContacts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_CONTACTS)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Contacts access needed.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setMessage("Please enable access to contacts.");
                builder.setOnDismissListener(dialog -> requestPermissions(
                        new String[]
                                {android.Manifest.permission.READ_CONTACTS}
                        , PERMISSIONS_REQUEST_READ_CONTACTS));
                builder.show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_CONTACTS},
                        PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        } else {
            getContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContacts();
                } else {
                    Toast.makeText(this, "You've disabled contacts permission", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    public static ArrayList<ContactInfo> getSavedContacts(Context context) {
        JSONArray savedContactsJson = null;
        ArrayList<ContactInfo> savedContacts = new ArrayList<>();
        try {
            File f = new File("/data/data/" + context.getPackageName() + "/contacts.json");
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String mResponse = new String(buffer);
            savedContactsJson = new JSONArray(mResponse);
        }
        catch (IOException | JSONException e){
            e.printStackTrace();
        }
        if (savedContactsJson != null) {
            for (int i = 0; i < savedContactsJson.length(); i++) {
                try {
                    JSONObject contact = savedContactsJson.getJSONObject(i);
                    ContactInfo newContact = new ContactInfo();
                    newContact.setName(contact.getString("name"));
                    newContact.setTag(ContactTag.valueOf(contact.getString("tag")));
                    newContact.setPhoto(getBitmapFromString(contact.getString("photo")));
                    savedContacts.add(newContact);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return savedContacts;
    }

    private HashMap<String, ContactTag> savedContactTagMap(List<ContactInfo> savedContactList) {
        HashMap<String, ContactTag> tagMap = new HashMap<>();
        for (ContactInfo contact : savedContactList) {
            tagMap.put(contact.getName(), contact.getTag());
        }
        return tagMap;
    }

    public void writeContacts() {
        try {
            JSONArray savedContactsJson = new JSONArray();
            for (int i = 0; i < contactList.size(); i++) {
                JSONObject contact = new JSONObject();
                contact.put("name", contactList.get(i).getName());
                contact.put("tag", contactList.get(i).getTag().toString());
                contact.put("photo", getStringFromBitmap(contactList.get(i).getPhoto()));
                savedContactsJson.put(contact);
            }
            FileWriter file =
                    new FileWriter("/data/data/" + UpdateContacts.this.getPackageName() + "/contacts.json");
            file.write(savedContactsJson.toString());
            file.flush();
            file.close();
        }
        catch(IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}