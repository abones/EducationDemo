package ru.sibhtc.educationdemo.fragments;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import ru.sibhtc.educationdemo.R;
import ru.sibhtc.educationdemo.mock.StudentMock;

/**
 * Created by Антон on 17.09.2015.
 **/
public class TitlesFragment extends ListFragment {

    boolean mDualPane;
    int mCurCheckPosition = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Populate list with our static array of titles.
        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_activated_1, StudentMock.getStudentNameList()));

        // Check to see if we have a frame in which to embed the details
        // fragment directly in the containing UI.
        View detailsFrame = getActivity().findViewById(R.id.details);
        mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

        if (mDualPane) {
            // In dual-pane mode, the list view highlights the selected item.
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            // Make sure our UI is in the correct state.
            //showDetails(mCurCheckPosition);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDetails(position);
    }

    /**
     * Helper function to show the details of a selected item, either by
     * displaying a fragment in-place in the current UI, or starting a
     * whole new activity in which it is displayed.
     */
    void showDetails(int index) {
        mCurCheckPosition = index;

        if (mDualPane) {
            getListView().setItemChecked(index, true);

            DetailsFragment details = (DetailsFragment)getFragmentManager().findFragmentById(R.id.details);
            if (details == null || details.getShownIndex() != index) {
                details = DetailsFragment.newInstance(index);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if (index == 0) {
                    ft.replace(R.id.details, details);
                } else {
                    ft.replace(R.id.details, details);
                }
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }

            DetailsResultFragment detailsResultFragment = (DetailsResultFragment)getFragmentManager().findFragmentById(R.id.details_result);
            if (detailsResultFragment == null || detailsResultFragment.getShownIndex() != index) {
                detailsResultFragment = DetailsResultFragment.newInstance(index);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if (index == 0) {
                    ft.replace(R.id.details_result, detailsResultFragment);
                } else {
                    ft.replace(R.id.details_result, detailsResultFragment);
                }
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        }
    }
}
