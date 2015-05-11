package geotouer4.yoslab.net.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import geotouer4.yoslab.net.myapplication.IdList;
import geotouer4.yoslab.net.myapplication.R;
import geotouer4.yoslab.net.myapplication.model.Flag;
import geotouer4.yoslab.net.myapplication.model.Spot;

public class SpotAdapter extends ArrayAdapter<Spot> {

    private LayoutInflater layoutInflater;
    private ArrayList<Integer> idArray = new ArrayList<Integer>();

    private Flag flag;

    public SpotAdapter(Context context, int resource, ArrayList<Spot> spots) {
        super(context, resource, spots);
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            // Viewがまだ作成していなければ、Viewを生成＝＞View
            convertView = layoutInflater.inflate(R.layout.spot_list_item, null);
        }


        /*-----------------------------
         * 値をViewにセット
         *---------------------------*/
        final Spot spot = (Spot)getItem(position);
        // スポット名をセット
        TextView title = (TextView)convertView.findViewById(R.id.name);
        title.setText(spot.getName());

        // setTagを使ってスポットのIDをセット
        final CheckBox mCheckBox = (CheckBox)convertView.findViewById(R.id.checkbox);
        mCheckBox.setTag(spot.getId());
        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                System.out.println("Adapterの中="+checkBox.getTag());

                boolean checked = checkBox.isChecked();
                System.out.println("checked_________"+checked);
                int tag = (int) checkBox.getTag(); // タップされたスポットID取得

                // チェックされたとき
                if(checked==true) {


                    IdList.idLists.add(tag);


                    // idArray.add(tag);
                   // System.out.println(idArray);
                } else {
                    IdList.idLists.remove(IdList.idLists.indexOf(tag));
                    System.out.println("removeされたよ");
                }


            }
        });
        return convertView;
    }
}
