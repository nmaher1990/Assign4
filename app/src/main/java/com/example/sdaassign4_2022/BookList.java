package com.example.sdaassign4_2022;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class BookList extends Fragment {

    public BookList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_book_list, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.bookView_view);

        // Database reference for book images
        DatabaseReference databaseRef = FirebaseDatabase.getInstance()
                .getReference("book_images");

        // Lists for book data
        ArrayList<String> mAuthor = new ArrayList<>();
        ArrayList<String> mTitle = new ArrayList<>();
        ArrayList<String> mImageURLs = new ArrayList<>();

        // Add local book data
        mAuthor.add("Edgar Rice Burroughs"); mTitle.add("Tarzan and the Golden Lion");
        mAuthor.add("Agatha Christie"); mTitle.add("The Murder on the Links");
        mAuthor.add("Winston S. Churchill"); mTitle.add("The World Crisis");
        mAuthor.add("E.e. cummings"); mTitle.add("Tulips and Chimneys");
        mAuthor.add("Robert Frost"); mTitle.add("New Hampshire");
        mAuthor.add("Kahlil Gibran"); mTitle.add("The Prophet");
        mAuthor.add("Aldous Huxley"); mTitle.add("Antic Hay");
        mAuthor.add("D.H. Lawrence"); mTitle.add("Kangaroo");
        mAuthor.add("Bertrand and Dora Russell"); mTitle.add("The Prospects of Industrial Civilization");
        mAuthor.add("Carl Sandberg"); mTitle.add("Rootabaga Pigeons");
        mAuthor.add("Edith Wharton"); mTitle.add("A Son at the Front");
        mAuthor.add("P.G. Wodehouse"); mTitle.add("The Inimitable Jeeves");
        mAuthor.add("P.G. Wodehouse"); mTitle.add("Leave it to Psmith");
        mAuthor.add("Virginia Woolf"); mTitle.add("Jacob's Room");



        // Get image URLs from Firebase Storage
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (int i = 1; i <= 14; i++) {
                    String sku = "sku1000" + i;
                    if (dataSnapshot.hasChild(sku)) {
                        String gsUrl = dataSnapshot.child(sku).getValue(String.class);
                        StorageReference gsReference = FirebaseStorage.getInstance().getReferenceFromUrl(gsUrl);
                        gsReference.getDownloadUrl().addOnSuccessListener(uri -> {
                            mImageURLs.add(uri.toString());

                            if (mImageURLs.size() == 14) { // Ensure all images are loaded before updating RecyclerView
                                LibraryViewAdapter recyclerViewAdapter = new LibraryViewAdapter(getContext(), mAuthor, mTitle, mImageURLs);
                                recyclerView.setAdapter(recyclerViewAdapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }
}
