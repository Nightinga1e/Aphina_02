package smaxd.aphina_02;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class number extends Activity {

    private GridView mGrid;
    private Grid_Adapter mAdapter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        mGrid = (GridView)findViewById(R.id.field);
        mGrid.setNumColumns(6);
        mGrid.setEnabled(true);

        mAdapter = new Grid_Adapter(this, 6, 6);
        mGrid.setAdapter(mAdapter);
    }
}