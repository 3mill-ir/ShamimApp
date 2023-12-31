package ir.hezareh.park.Component;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;

import ir.hezareh.park.R;
import ir.hezareh.park.Util.Utils;
import ir.hezareh.park.models.Item;
import ir.hezareh.park.models.ModelComponent;

public class MyPieChart {
    private PieChart chart;
    private TextView questionText;
    private View pollDiagramLayout;

    public MyPieChart(Context c, int width, int height, ModelComponent modelComponent) {

        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        pollDiagramLayout = inflater.inflate(R.layout.diagram_item, null);

        //cardView=pollDiagramLayout.findViewById(R.id.card_view);
        //cardView=new CardView(c);
        //pollDiagramLayout=new LinearLayout(c);

        chart = pollDiagramLayout.findViewById(R.id.pollDiagram);
        questionText = pollDiagramLayout.findViewById(R.id.question_text);

        LinearLayout.LayoutParams ChartLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, width / 2 + 100);
        chart.setLayoutParams(ChartLayoutParams);

        questionText.setText(modelComponent.getQuestion());


        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);

        //chart.setCenterTextTypeface(mTfLight);
        //chart.setCenterText(generateCenterSpannableText());

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(40);
        chart.setTransparentCircleRadius(50);

        chart.setDrawCenterText(true);

        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);
        chart.setEntryLabelTypeface(new Utils(c).font_set("BYekan"));
        chart.setNoDataTextTypeface(new Utils(c).font_set("BYekan"));

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
        //chart.setOnChartValueSelectedListener(con);

        ArrayList<Float> Votes = new ArrayList<>();
        ArrayList<String> Answers = new ArrayList<>();

        for (Item item : modelComponent.getItem()) {
            Votes.add((float) item.getVote());
            Answers.add(item.getText());
        }

        setData(Votes, Answers);

        chart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        chart.spin(2000, 0, 360, Easing.EasingOption.EaseInExpo);

        //mSeekBarX.setOnSeekBarChangeListener(this);
        //mSeekBarY.setOnSeekBarChangeListener(this);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setTypeface(new Utils(c).font_set("BYekan"));

        // entry label styling
        chart.setEntryLabelColor(Color.BLACK);
        //chart.setEntryLabelTypeface(mTfRegular);
        chart.setEntryLabelTextSize(12f);

        //pollDiagramLayout.addView(chart);
        //cardView.addView(pollDiagramLayout);

    }

    public View getItem() {
        return pollDiagramLayout;
    }

    private void setData(ArrayList<Float> Value, ArrayList<String> Label) {

        List<PieEntry> entries = new ArrayList<>();
        int index = 0;
        for (float item : Value) {
            entries.add(new PieEntry(item, Label.get(index++)));
        }
        PieDataSet dataSet = new PieDataSet(entries, "نتایج نظرسنجی");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(12);
        data.setValueTextColor(Color.BLACK);
        //data.setValueTypeface(mTfLight);
        // undo all highlights
        chart.highlightValues(null);

        chart.setData(data);
        chart.invalidate(); // refresh
    }
}
