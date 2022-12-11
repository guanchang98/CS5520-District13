package com.example.district13;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {
//    private final String TAG = "AccountFragment";

    //firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reference;

    //storage
    StorageReference storageReference;
    //path the images of user account to be stored
    String storagePath = "Users_Account_Imgs/";

    //views
    ImageView avatarIv;
    TextView userEmailTv, userNameTv, userPostsTv, userFollowingTv, userFollowersTv;

    //buttons
    Button logOut;
    FloatingActionButton editUser;
    Button goUserPosts;

    //progress dialog
    ProgressDialog pd;

    //permissions
    private static final int CAMERA_REQ_CODE = 14;
    private static final int STORAGE_REQ_CODE = 24;
    private static final int IMAGE_FROM_CAMERA_REQ_CODE = 34;
    private static final int IMAGE_FROM_GALLERY_REQ_CODE = 44;

    //array of permissions
    String[] cameraPermissions;
    String[] storagePermissions;

    //uri of image
    Uri imageUri;

    //key of avatar
    String accountUserAvatar;

    //int of user info display
    private int postCount, followingCount, followersCount;

    public AccountFragment() {
        //Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("appUsers");
        storageReference = FirebaseStorage.getInstance().getReference();

        //init views
        avatarIv = view.findViewById(R.id.user_selfie);
        userEmailTv = view.findViewById(R.id.user_email);
        userNameTv = view.findViewById(R.id.user_name);
        userPostsTv = view.findViewById(R.id.user_posts);
        userFollowingTv = view.findViewById(R.id.user_following);
        userFollowersTv = view.findViewById(R.id.user_followers);

        //init button
        logOut = view.findViewById(R.id.user_logout);
        editUser = view.findViewById(R.id.edit_user);
        goUserPosts = view.findViewById(R.id.go_to_posts);

        //init progress dialog
        pd = new ProgressDialog(getActivity());

        //init array permissions
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //Get user data from database
        Query query = reference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    //get data
                    String name = "" + dataSnapshot.child("name").getValue();
                    String email = "" + dataSnapshot.child("email").getValue();
                    String avatar = "" + dataSnapshot.child("avatar").getValue();
//                    String posts = "" + dataSnapshot.child("posts").getValue();
//                    String following = "" + dataSnapshot.child("following").getValue();
//                    String followers = "" + dataSnapshot.child("followers").getValue();

//                    Log.w(TAG, name);

                    //set data
                    userEmailTv.setText(email);
                    userNameTv.setText(name);

                    //display numbers of posts, followings and followers
                    postCount = (int) dataSnapshot.child("posts").getChildrenCount();
                    followingCount = (int) dataSnapshot.child("following").getChildrenCount();
                    followersCount = (int) dataSnapshot.child("followers").getChildrenCount();
                    userPostsTv.setText(postCount + "\nPosts");
                    userFollowingTv.setText(followingCount + "\nFollowing");
                    userFollowersTv.setText(followersCount + "\nFollowers");

                    //load user avatar
                    try {
                        //load avatar if set
                        Picasso.get().load(avatar).into(avatarIv);
                    } catch (Exception e) {
                        //otherwise load default
                        Picasso.get().load(R.drawable.ic_add_user_selfie).into(avatarIv);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //handle logout button
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
//                checkUserStatus();
                startActivity(new Intent(getActivity(), TeaTalksLoginActivity.class));
            }
        });

        //handle edit user float button
        editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditUserDialog();
            }
        });

        //handle goUserPosts button
        goUserPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UserPostsActivity.class));
            }
        });

        return view;

    }

    //method handle edit user button
    private void showEditUserDialog() {
        String[] options = {"Edit Your Avatar", "Edit Name"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose To Edit");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int option) {
                if (option == 0) {
                    //edit avatar
                    pd.setMessage("Updating Avatar Photo");
                    accountUserAvatar = "avatar";
                    showAvatarDialog();
                }
                else if (option == 1) {
                    //edit name
                    pd.setMessage("Updating User Name");
                    showPersonalInfoUpdateDialog("name");
                }
            }
        });
        builder.create().show();
    }

    //method updating personal info in fragment
    private void showPersonalInfoUpdateDialog(String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Update " + key);
        //set layout
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10, 10, 10, 10);
        //edit text
        EditText editText = new EditText(getActivity());
        editText.setHint("Enter " + key);
        linearLayout.addView(editText);

        builder.setView(linearLayout);

        //buttons
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String val = editText.getText().toString().trim();
                if (!TextUtils.isEmpty(val)) {
                    pd.show();
                    HashMap<String, Object> res = new HashMap<>();
                    res.put(key, val);

                    reference.child(user.getUid()).updateChildren(res)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    pd.dismiss();
                                    Toast.makeText(getActivity(), "" + "Updated", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(getActivity(), "" + "Please Enter " + key, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    //method handle edit avatar option
    private void showAvatarDialog() {
        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Your Photo From");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int option) {
                if (option == 0) {
                    //choose from camera
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    } else {
                        chooseFromCamera();
                    }
                }
                else if (option == 1) {
                    //choose from gallery
                    if (!checkStoragePermission()) {
                        requestStoragePermission();
                    } else {
                        chooseFromGallery();
                    }
                }
            }
        });
        builder.create().show();
    }

    //check if storage permission enabled
    private boolean checkStoragePermission() {
        boolean resStorage = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return resStorage;
    }

    //request storage permission at runtime
    private void requestStoragePermission() {
        requestPermissions(storagePermissions, STORAGE_REQ_CODE);
    }

    //check if camera permission enabled
    private boolean checkCameraPermission() {
        boolean resCamera = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);
        boolean resStorage = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return resCamera && resStorage;
    }

    //request camera permission at runtime
    private void requestCameraPermission() {
        requestPermissions(cameraPermissions, CAMERA_REQ_CODE);
    }

    //handle choose from camera or gallery options
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQ_CODE:{
                //choose from camera
                if (grantResults.length > 0) {
                    boolean cameraAccept = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccept = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccept && writeStorageAccept) {
                        //permission enabled
                        chooseFromCamera();
                    } else {
                        Toast.makeText(getActivity(), "Please Enable Camera And Storage Permission", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQ_CODE:{
                //choose from gallery
                if (grantResults.length > 0) {
                    boolean writeStorageAccept = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (writeStorageAccept) {
                        //permission enabled
                        chooseFromGallery();
                    } else {
                        Toast.makeText(getActivity(), "Please Enable Storage Permission", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //Choose image from camera
    private void chooseFromCamera() {
        ContentValues val = new ContentValues();
        val.put(MediaStore.Images.Media.TITLE, "Temp Pic");
        val.put(MediaStore.Images.Media.DESCRIPTION, "Temp Des");
        //put image uri
        imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, val);
        //intent to start camera
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, IMAGE_FROM_CAMERA_REQ_CODE);
    }

    //Choose image from gallery
    private void chooseFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_FROM_GALLERY_REQ_CODE);
    }

    //After choosing image from camera or gallery
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_FROM_GALLERY_REQ_CODE) {
//                assert data != null;
                imageUri = data.getData();
                uploadAvatar(imageUri);
            }
            if (requestCode == IMAGE_FROM_CAMERA_REQ_CODE) {
                uploadAvatar(imageUri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //Upload image to firebase storage and update it in user info
    private void uploadAvatar(Uri uri) {
        String filePathAndName = storagePath + "" + accountUserAvatar + "_" + user.getUid();
        StorageReference storageReferenceChild = storageReference.child((filePathAndName));
        storageReferenceChild.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //after image uploaded, update it in user's database info
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        Uri downloadUri = uriTask.getResult();
                        if (uriTask.isSuccessful()) {
                            HashMap<String, Object> res = new HashMap<>();
                            res.put(accountUserAvatar, downloadUri.toString());
                            reference.child(user.getUid()).updateChildren(res)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            pd.dismiss();
                                            Toast.makeText(getActivity(), "Image Updated!", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            pd.dismiss();
                                            Toast.makeText(getActivity(), "Error Updating Image", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            pd.dismiss();
                            Toast.makeText(getActivity(), "Error Occurred", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //show errors if failure
                        pd.dismiss();
                        Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}