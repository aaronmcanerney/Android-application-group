package com.example.aaron.fragmenttest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class OnEventCreationThree extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_on_event_creation_three
                , container, false);
        return v;
    }
    public static OnEventCreationThree newInstance(String text) {

        OnEventCreationThree f = new OnEventCreationThree();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
   /* public void onStart(){
        Button button = new Button(this.getActivity());
        button.setText("Next");
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                toEventCreationThree(v);
            }
        });
        RelativeLayout rl = (RelativeLayout) this.getActivity().findViewById(R.id.event_creation_three);
        rl.addView(button);

        super.onStart();
    }
    public void toEventCreationThree(View view){
        Fragment fragment = new OnEventCreationThree();
        FragmentTransaction ft = this.getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.event_container, fragment).addToBackStack(null).commit();
    }*/

}
