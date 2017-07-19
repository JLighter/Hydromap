package exam.hydromap.julienheroguelle.hydromap.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import exam.hydromap.julienheroguelle.hydromap.R;
import exam.hydromap.julienheroguelle.hydromap.Utils.map.MapHeatsActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextSearch = (EditText) findViewById(R.id.editTextSearch);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO : launch search
        // editTextSearch.getText().toString()
    }

    public void changeGradient(View view) {
        MapHeatsActivity.changeGradient();
    }
}
