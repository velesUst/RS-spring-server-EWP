package com.ewp.server.utils.partition;

import java.io.*;
import java.util.*;
import java.util.List;
import com.ewp.server.persistenceR2DB.entity.Candlestick;

public class PartitionAlgorithm {

    public static List vawes_identify( List workList, int N ) {
        List vawe_points = new LinkedList();

        if(workList.size()<N)
            vawe_points.addAll(workList);
        else {
            vawe_points.add( workList.get(0));
            vawe_points.add( workList.get(workList.size()-1));

            double koeff = 6.0f, dop_koeff = 20.0f;
            List vawe_points__ = vawe_points;    // - сами волны
            while(vawe_points__.size() < N*2 && koeff <= dop_koeff) {
                List vawe_points_ = new LinkedList();    // - промежуточная версия
                vawe_points_.add( vawe_points.get(0));
                //---------------------------------------------------
                double[] amplitudes = new double[vawe_points.size()-1];
                for(int i = 1; i < vawe_points.size(); i++) amplitudes[i-1] = getAmplitude(workList, ((Point)vawe_points.get(i-1)).temp_pos, ((Point)vawe_points.get(i)).temp_pos);
                double max_am = 0.0f; for(int i = 0; i < amplitudes.length; i++) if( amplitudes[i] >= max_am ) max_am = amplitudes[i];
                for(int i = 1; i < vawe_points.size(); i++) {
                    if(max_am/amplitudes[i-1] < koeff)
                        vawe_points_.addAll( (List)parse_intervalvawes_identify( workList, ((Point)vawe_points.get(i-1)).x, ((Point)vawe_points.get(i)).x, ((Point)vawe_points.get(i-1)).y, ((Point)vawe_points.get(i)).y, ((Point)vawe_points.get(i-1)).temp_pos, ((Point)vawe_points.get(i)).temp_pos, dop_koeff*2 ) );
                    vawe_points_.add( vawe_points.get(i));
                }

                if(vawe_points.size()==vawe_points_.size()) koeff+=0.1f;
                vawe_points = vawe_points_;
                vawe_points__ = split(vawe_points_);
            }
            while(vawe_points__.size() > N)
                Compress(vawe_points__);

            vawe_points = vawe_points__;
        }

        // - корректируем сдвиг (следствие наклона) -----------------------------
        for(int i = 1; i < vawe_points.size()-1; i++) {
            long tip = 0;
            if( ((Point)vawe_points.get(i)).y < ((Point)vawe_points.get(i-1)).y ) tip = -1; else tip = 1;
            Point corresp = (Point)vawe_points.get(i);

            for(int j = 0; j < workList.size(); j++)
                if( ((Point)vawe_points.get(i-1)).x < ((Point)workList.get(j)).x &&
                    ((Point)vawe_points.get(i+1)).x > ((Point)workList.get(j)).x ) {

                    if(tip==-1) if( ((Point)workList.get(j)).y < corresp.y ) corresp = (Point)workList.get(j);
                    if(tip==1) if( ((Point)workList.get(j)).y > corresp.y ) corresp = (Point)workList.get(j);
                }

            vawe_points.remove(i);
            vawe_points.add(i,corresp);
        }

        return vawe_points;
    }

    public static double getAmplitude( List workList, int left_pos, int right_pos ) {
        double ret = 0.0f;
        double min = 0.0f, max = 0.0f;
        for(int i = left_pos; i <= right_pos; i++) {

            if(min==0.0f) min = ((Point)workList.get(i)).y;
            if(max==0.0f) max = ((Point)workList.get(i)).y;

            if(min > ((Point)workList.get(i)).y) min = ((Point)workList.get(i)).y;
            if(max < ((Point)workList.get(i)).y) max = ((Point)workList.get(i)).y;
        }
        ret = max - min;
        return ret;
    }

    public static void Compress( List workList ) {  // - неявная обработка
        int a = 0, b = 0;
        double min_v = 0.0f;
        for(int i = 2; i < workList.size()-1; i++) {
            double current_v = Math.abs( ((Point)workList.get(i)).y - ((Point)workList.get(i-1)).y);
            if(a==0&&b==0) { min_v = current_v; a = i-1; b = i; }

            if(min_v>=current_v) { min_v = current_v; a = i-1; b = i; }
        }
        workList.remove(b);
        workList.remove(a);
        workList = split(workList);
    }

    public static List parse_intervalvawes_identify( List workList, long left, long right, double left_v, double right_v, int left_pos, int right_pos, double dop_koeff ) {  // - List workList это чтоб весь список ??????????????????
        double k = (double)(right_v-left_v)/(right-left);
        int ord = 0; // 1 - max/min  2 - min/max
        Point min = null, max = null;

        for(int i = left_pos+1; i < right_pos; i++) {
            Point point = (Point)workList.get(i);

            double naklon_val = point.y - (left_v + k*(point.x - left));  // приведение к нулю; (-) тк значения графиков всегда > 0
            point.temp_val = naklon_val;

            double min_v = 0.0f; if(null!=min) min_v = min.temp_val;
            double max_v = 0.0f; if(null!=max) max_v = max.temp_val;
            if( naklon_val < min_v ) { min = point; ord = 1; }
            if( naklon_val > max_v ) { max = point; ord = 2; }
        }
        if(null!=max && null!=min) {
            if(Math.abs(max.temp_val/min.temp_val) > dop_koeff) { min = null; ord = 2; }
            else
            if(Math.abs(min.temp_val/max.temp_val) > dop_koeff) { max = null; ord = 1; }
        }

        List ret_Points = new LinkedList();
            if(ord==1) { if(null!=max) ret_Points.add(max); ret_Points.add(min); }
            if(ord==2) { if(null!=min) ret_Points.add(min); ret_Points.add(max); }
        return ret_Points;
    }

    public static List  split( List inpupList ) {
        List ret = new LinkedList();
            ret.addAll(inpupList);
        for(int i = 0; i < ret.size()-2; i++) {
            double a = ((Point)ret.get(i)).y,
                b = ((Point)ret.get(i+1)).y,
                c = ((Point)ret.get(i+2)).y;

            if( ( a <= b && b <= c ) || ( a >= b && b >= c ) )  {
                ret.remove(i+1);
                i--;
            }
        }
        return ret;
    }

}
