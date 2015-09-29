package ru.sibhtc.educationdemo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import ru.sibhtc.educationdemo.R;
import ru.sibhtc.educationdemo.adapters.DetailResultItemAdapter;
import ru.sibhtc.educationdemo.models.EventResultModel;

/**
 * Created by nikpodrivnik on 29/09/15.
 */
public class LearningResultFragment extends Fragment {
    private Button finishButton;


    public static DetailsResultFragment newInstance(int index) {
        DetailsResultFragment f = new DetailsResultFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        final View view = inflater.inflate(R.layout.fragment_learning_result, container, false);

        finishButton = (Button) view.findViewById(R.id.finish_button);

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        //заглушка для резултатов тестирования
        ArrayList<EventResultModel> eventResultModels = new ArrayList<>();
        EventResultModel eventResultModel = new EventResultModel();
        eventResultModels.add(eventResultModel);
        ListView listDetailResult;
        listDetailResult = (ListView) view.findViewById(R.id.fragment_learning_result_view);
        DetailResultItemAdapter adapter = new DetailResultItemAdapter(getActivity(), R.layout.details_result_list_item, eventResultModels);
        adapter.setNotifyOnChange(true);
        listDetailResult.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return view;
    }
}
