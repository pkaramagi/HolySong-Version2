package com.karamagi.holysongs.holysong;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class HolysongContent{

    /**
     * An array of Audio items.
     */
    public  final List<HolySongItem> ITEMS = new ArrayList<HolySongItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public  final Map<String, HolySongItem> ITEM_MAP = new HashMap<String, HolySongItem>();



    public void addItem(HolySongItem item) {
        this.ITEMS.add(item);
        ITEM_MAP.put(item.getTitle(), item);
    }

    public  HolySongItem createHolySongItem(String title, String id, String url) {
        return new HolySongItem(title,id, url);
    }

   public String holySongNumber(int i){
        String number = null;
        String url = null;

        if( i<20){
            number = String.valueOf(i+1);

        }
        else if(i == 20){
            number = String.valueOf(i+1)+"a";
        }
        else if(i == 21){
            number = String.valueOf(i)+"b";
        }
        else if(i>21){
            number = String.valueOf(i);
        }
        else{

        }


        return number;
    }


    public String holySongUrl(int i){
        String url = null ;
        String zero = "0";
        String zeroZero = "00";

        if( i<9){
            url = zeroZero+String.valueOf(i+1);
        }

        else if(i>=9 && i<20){
            url = zero+String.valueOf(i+1);
        }
        else if(i == 20){
            url = zero+String.valueOf(i+1)+"a";
        }
        else if(i == 21){
            url = zero+String.valueOf(i)+"b";
        }
        else if(i>21){
            url = zero+String.valueOf(i);
        }
        else{

        }

        return url;
    }

}
