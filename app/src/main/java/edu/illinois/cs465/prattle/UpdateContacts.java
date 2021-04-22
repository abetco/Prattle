package edu.illinois.cs465.prattle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
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
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

enum ContactStatus {OFTEN, SOMETIMES, NEVER}

public class UpdateContacts extends AppCompatActivity {
    public static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    ContactAdapter contactAdapter = null;
    ListView listView;
    ImageButton helpButton;
    Button doneButton;
    List<ContactInfo> contactInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contacts);
        listView = findViewById(R.id.listContacts);
        listView.setAdapter(contactAdapter);
        requestContactPermission();

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

    private void getContacts(){
        String displayName;
        String imageUri;
        Bitmap photo;
        Drawable contactDrawable = getResources().getDrawable(R.drawable.ic_baseline_account_circle_gray_24);
        Bitmap defaultPhoto = getBitmapFromDrawable(contactDrawable);
        contactInfoList = new ArrayList<>();
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                imageUri = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));

                ContactInfo contactInfo = new ContactInfo();
                contactInfo.setName(displayName);
                contactInfo.setStatus(ContactStatus.NEVER);
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
        contactAdapter = new ContactAdapter(UpdateContacts.this, R.layout.contact_info, contactInfoList);
        listView.setAdapter(contactAdapter);
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
}