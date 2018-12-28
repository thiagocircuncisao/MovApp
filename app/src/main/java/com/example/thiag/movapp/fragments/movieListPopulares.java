package com.example.thiag.movapp.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thiag.movapp.MoviesAdapter;
import com.example.thiag.movapp.R;
import com.example.thiag.movapp.TMDBConnect;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link movieListPopulares.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link movieListPopulares#newInstance} factory method to
 * create an instance of this fragment.
 */
public class movieListPopulares extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public movieListPopulares() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private TMDBConnect tmdbConnect;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment movieListCartaz.
     */
    // TODO: Rename and change types and number of parameters
    public static movieListPopulares newInstance(String param1, String param2) {
        movieListPopulares fragment = new movieListPopulares();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private boolean isConnected;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
            isConnected = (networkInfo != null) && (networkInfo.isConnectedOrConnecting());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movie_list_populares, container, false);
        if(isConnected) {
            recyclerView = (RecyclerView) v.findViewById(R.id.list_populares);
            adapter = new MoviesAdapter(v);
            tmdbConnect = new TMDBConnect(recyclerView, adapter);
            tmdbConnect.listMostPopularMovies();
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
