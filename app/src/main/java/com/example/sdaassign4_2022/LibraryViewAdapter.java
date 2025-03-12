package com.example.sdaassign4_2022;

        /*
         * Copyright (C) 2016 The Android Open Source Project
         *
         * Licensed under the Apache License, Version 2.0 (the "License");
         * you may not use this file except in compliance with the License.
         * You may obtain a copy of the License at
         *
         *      http://www.apache.org/licenses/LICENSE-2.0
         *
         * Unless required by applicable law or agreed to in writing, software
         * distributed under the License is distributed on an "AS IS" BASIS,
         * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
         * See the License for the specific language governing permissions and
         * limitations under the License.
         */

        import static android.content.Context.MODE_PRIVATE;

        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import java.util.ArrayList;

        import com.bumptech.glide.Glide;


/*
 * @author Chris Coughlan 2019
 */
public class LibraryViewAdapter extends RecyclerView.Adapter<LibraryViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private Context mNewContext;

    //add array for each item\
    private ArrayList<String> mAuthor;
    private ArrayList<String> mTitle;
    private ArrayList<String> mImageID;

    //SharedPreferences for Checkout access
    private static final String BORROWER_NAME_KEY = "BORROWER_NAME_KEY";
    private static final String EMAIL_ADDRESS_KEY = "EMAIL_ADDRESS_KEY";
    private static final String BORROWER_ID_KEY = "BORROWER_ID_KEY";
    private EditText editBorrowerName;
    private EditText editEmailAddress;
    private EditText editBorrowerID;
    private SharedPreferences prefs;
    LibraryViewAdapter(Context mNewContext, ArrayList<String> author, ArrayList<String> title, ArrayList<String> imageId) {
        this.mNewContext = mNewContext;
        this.mAuthor = author;
        this.mTitle = title;
        this.mImageID = imageId;

    }

    //declare methods
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_list_item, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Log.d(TAG, "onBindViewHolder: was called");

        viewHolder.authorText.setText(mAuthor.get(position));
        viewHolder.titleText.setText(mTitle.get(position));

        String imageUrl = mImageID.get(position);

        // Convert to HTTPS URL links so Gldie can access
        if (imageUrl.startsWith("gs://")) {
            imageUrl = imageUrl.replace("gs://assign4-nicholasmaher-f0488.appspot.com",
                            "https://firebasestorage.googleapis.com/v0/b/assign4-nicholasmaher-f0488.appspot.com/o")
                    .replace("/", "%2F") + "?alt=media";
        }
        //Glide image retrieval
        Glide.with(viewHolder.imageItem.getContext())
                .load(imageUrl)
                .into(viewHolder.imageItem);

        //Button enable/disable logic
        prefs = mNewContext.getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String borrowerName = prefs.getString(BORROWER_NAME_KEY, "");
        String emailAddress = prefs.getString(EMAIL_ADDRESS_KEY, "");
        String borrowerID = prefs.getString(BORROWER_ID_KEY, "");

        viewHolder.checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (borrowerName.isEmpty() || emailAddress.isEmpty() || borrowerID.isEmpty()) {
                    Toast.makeText(mNewContext, "Add settings first", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mNewContext, mTitle.get(position), Toast.LENGTH_SHORT).show();
                    Intent myOrder = new Intent(mNewContext, CheckOut.class);
                    mNewContext.startActivity(myOrder);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mAuthor.size();
    }

    //view holder class for recycler_list_item.xml
    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageItem;
        TextView authorText;
        TextView titleText;
        Button checkOut;
        RelativeLayout itemParentLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            //grab the image, the text and the layout id's
            imageItem = itemView.findViewById(R.id.bookImage);
            authorText = itemView.findViewById(R.id.authorText);
            titleText = itemView.findViewById(R.id.bookTitle);
            checkOut = itemView.findViewById(R.id.out_button);
            itemParentLayout = itemView.findViewById(R.id.listItemLayout);

        }
    }
}
