package geotouer4.yoslab.net.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import geotouer4.yoslab.net.myapplication.IdList;
import geotouer4.yoslab.net.myapplication.model.Flag;
import geotouer4.yoslab.net.myapplication.model.Spot;

public class Spot_2Adapter extends ArrayAdapter<Spot> {

    private LayoutInflater layoutInflater;
    private ArrayList<Integer> idArray = new ArrayList<Integer>();
    int tag;
    private Flag flag;

    public Spot_2Adapter(Context context, int resource, ArrayList<Spot> spots) {
        super(context, resource, spots);
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
        @Override
        public View getView ( int position, View convertView, ViewGroup parent) {

            final Spot spot = (Spot)getItem(position);
            tag = spot.getId();
            IdList.idLists.add(tag);
            System.out.println("IdList.idLists==============="+IdList.idLists);
            return convertView;
        }
    }
