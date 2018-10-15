package com.example.yzeng.Week3AssignYixin.list;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.yzeng.Week3AssignYixin.R;
import com.example.yzeng.Week3AssignYixin.data.TodoNote;
import com.example.yzeng.Week3AssignYixin.data.source.local.DataSourceContract;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnQuestionClickListener} interface
 * to handle interaction events.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment implements
        AdapterView.OnItemClickListener,ListContract.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
   /* private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";*/

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView listView;

    private OnQuestionClickListener mListener;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    ListPresenter presenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ListPresenter(this);
       /* if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_list, container, false);
        listView = view.findViewById(R.id.listview);
        presenter.getData();
        listView.setOnItemClickListener(this);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
   /* public void onButtonPressed(String uri) {
        if (mListener != null) {
            mListener.onQuestionClick(uri);
        }
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnQuestionClickListener) {
            mListener = (OnQuestionClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnQuestionClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
       TodoNote todoNote = (TodoNote) adapterView.getItemAtPosition(position);
       String  data = todoNote.getQuestion();
        //Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
        if (mListener != null) {
            mListener.onQuestionClick(data);
        }


    }

    @Override
    public void setData(Cursor cursor) {
        SimpleCursorAdapter cursorAdapter =
                new SimpleCursorAdapter(getContext(),
                        android.R.layout.simple_list_item_1,//row layout
                        cursor,//data
                        new String[]{DataSourceContract.TodoEntry.COLUMN_NAME_QUESTION},//from
                        new int[]{android.R.id.text1},//textview of the layout
                        0);
        listView.setAdapter(cursorAdapter);
    }


    public interface OnQuestionClickListener {
        // TODO: Update argument type and name
        void onQuestionClick(String question);
    }
}
