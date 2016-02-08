package com.example.akosha.sample1.poc2.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.akosha.sample1.poc2.R;
import com.example.akosha.sample1.poc2.model.DatModel;
import com.example.akosha.sample1.poc2.mortarsupport.ActionBarModifier;
import com.example.akosha.sample1.poc2.mortarsupport.DaggerService;
import com.example.akosha.sample1.poc2.onProgress.JhampakDialog;
import com.example.akosha.sample1.poc2.presenter.CountriesPath;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kushagarlall on 02/02/16.
 */
public class CountriesListView extends LinearLayout implements ActionBarModifier, CountriesPath.View {

    private JhampakDialog progressDialog;
    @Inject
    CountriesPath.Presenter presenter;
    @Bind(R.id.list_view)
    ListView listView;

    private Context mContext;

    public CountriesListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        DaggerService.<CountriesPath.Component>getDaggerComponent(context).inject(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ButterKnife.bind(this);
        presenter.takeView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        presenter.dropView(this);
        super.onDetachedFromWindow();
    }

    @Override
    public String getTitle() {
        return "CountriesList";
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }


    @Override
    public void showList(ArrayList<DatModel.Country> results) {
        //set up list view here
        MyListAdapter adapter = new MyListAdapter(mContext, results);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.onItemClick(position);
            }
        });
    }

    @Override
    public void onProgress() {
        showProgressDialog();
    }

    @Override
    public void onHideProgress() {
        hideProgressDialog();
    }


    public class MyListAdapter extends ArrayAdapter<DatModel> {
        private ArrayList<DatModel.Country> items = new ArrayList<>();
        private Context mContext;

        public MyListAdapter(Context context, ArrayList<DatModel.Country> res) {
            super(context, R.layout.todo_list_row);
            this.items = res;
            this.mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.todo_list_row, parent, false);
            TextView description = (TextView) convertView.findViewById(R.id.content);
            description.setText(items.get(position).countryName);

            return convertView;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        public void addAll(ArrayList<DatModel.Country> countriesItems) {
            this.items.addAll(countriesItems);
        }
    }

    public void onLoad() {
        showProgressDialog();
    }

    protected void showProgressDialog() {
        if (progressDialog == null || !progressDialog.isShown()) {
//            View rootView = ((Activity) mContext).getWindow().getDecorView().findViewById(android.R.id.content);
            progressDialog = JhampakDialog.create(this);
            progressDialog.show();
        }
    }

    protected void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShown()) {
            progressDialog.dismiss();
        }
    }
}
