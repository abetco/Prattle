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
import java.util.List;

enum ContactTag {OFTEN, SOMETIMES, NEVER}

public class UpdateContacts extends AppCompatActivity {
    public static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    ContactAdapter contactAdapter = null;
    List<ContactInfo> contactInfoList;
    ListView listView;
    ImageButton helpButton;
    Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contacts);
        listView = findViewById(R.id.listContacts);
        contactInfoList = new ArrayList<>();
        getSavedContacts(UpdateContacts.this);
        if (contactInfoList.isEmpty()) {
            requestContactPermission();
        }
        contactAdapter = new ContactAdapter(UpdateContacts.this, R.layout.contact_info, contactInfoList);
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
            Intent mainIntent = new Intent(UpdateContacts.this, MainActivity.class);
            startActivity(mainIntent);
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        writeContacts(UpdateContacts.this);
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private String getStringFromBitmap(Bitmap bitmap) {
        final int COMPRESSION_QUALITY = 100;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    private Bitmap getBitmapFromString(String string) {
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
                contactInfoList.add(contactInfo);
            }
        }
        cursor.close();
    }

    public void requestContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
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

    public void getSavedContacts(Context context) {
        JSONArray savedContactsList = null;
        try {
            File f = new File("/data/data/" + context.getPackageName() + "/contacts.json");
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String mResponse = new String(buffer);
            savedContactsList = new JSONArray(mResponse);
        }
        catch (IOException | JSONException e){
            e.printStackTrace();
        }
        if (savedContactsList != null) {
            for (int i = 0; i < savedContactsList.length(); i++) {
                try {
                    JSONObject contact = savedContactsList.getJSONObject(i);
                    ContactInfo newContact = new ContactInfo();
                    newContact.setName(contact.getString("name"));
                    newContact.setTag(ContactTag.valueOf(contact.getString("tag")));
                    newContact.setPhoto(getBitmapFromString(contact.getString("photo")));
                    contactInfoList.add(newContact);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void writeContacts(Context context) {
        try {
            JSONArray savedContactsList = new JSONArray();
            for (int i = 0; i < contactInfoList.size(); i++) {
                JSONObject contact = new JSONObject();
                contact.put("name", contactInfoList.get(i).getName());
                contact.put("tag", contactInfoList.get(i).getTag().toString());
                contact.put("photo", getStringFromBitmap(contactInfoList.get(i).getPhoto()));
                savedContactsList.put(contact);
            }
            FileWriter file =
                    new FileWriter("/data/data/" + context.getPackageName() + "/contacts.json");
            file.write(savedContactsList.toString());
            file.flush();
            file.close();
        }
        catch(IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}