package ru.sibhtc.educationdemo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import ru.sibhtc.educationdemo.R;
import ru.sibhtc.educationdemo.adapters.DetailResultItemAdapter;
import ru.sibhtc.educationdemo.models.EventResultModel;

/**
 * Created by nikpodrivnik on 29/09/15.
 */
public class LearningResultFragment extends Fragment {
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

        View view = inflater.inflate(R.layout.details_result_fragment, container, false);

        //заглушка для резултатов тестирования
        ArrayList<EventResultModel> eventResultModels = new ArrayList<>();
        EventResultModel eventResultModel = new EventResultModel();
        eventResultModels.add(eventResultModel);
        ListView listDetailResult;
        listDetailResult = (ListView) view.findViewById(R.id.resultList);
        DetailResultItemAdapter adapter = new DetailResultItemAdapter(getActivity(), R.layout.details_result_list_item, eventResultModels);
        adapter.setNotifyOnChange(true);
        listDetailResult.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return view;
    }
}
