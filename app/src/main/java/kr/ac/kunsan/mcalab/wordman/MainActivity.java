package kr.ac.kunsan.mcalab.wordman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Map<String, ArrayList<String[]>> data = new HashMap<>();
    TextView txtvEnglish, txtvKorean, txtvMax;
    CheckBox cbRandom, cbHide;
    Spinner spWold;
    ArrayList<String[]> selData;

    int maxState, nowState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init(){
        txtvEnglish = findViewById(R.id.txtvEnglish);
        txtvKorean = findViewById(R.id.txtvKorean);
        txtvMax = findViewById(R.id.txtvMax);
        cbRandom = findViewById(R.id.cbRandom);
        cbHide = findViewById(R.id.cbHide);
        spWold = findViewById(R.id.spWold);

        setData();
        setWordBook();
        loadWordBook();
    }

    private void setData(){
        for(int day=1;day<=3;day++){
            data.put("Day"+day, createData());
        }
    }

    private ArrayList<String[]> createData(){
        ArrayList<String[]> data =new ArrayList<>();
        String[] kordata = new String[] {
                "동의하다","수술하다","설명하다","증가하다","질투하다 시기하다","알리다 밀고하다",
                "생각나게 하다","빼앗다","빼앗다","자국","부족한","짧은 글","공립 도서관",
                "인지 아닌지","부족하다","바꾸어 말하자면(즉)","지겨운 따분한","에 매혹되다",
                "특징 성격","감각","강의","기억하다","방해하다","반대로","에 집중하다",
                "(정신이) 산만해지다","메스꺼워지는","고함치다","개발자","새우","굴 양식장",
                "생계비를 벌다","재난","습격하다","억수 폭우","적시다","(엔진의) 냉각기",
                "차고 수리소 주유소","불행하게도 ~하다","양철 깡통","미끼","기대다",
                "갈고리로 걸다","무너지다(=fall down)","기다","도난 방지 자물쇠","참석하다",
                "회의 회담","물러가다","훨 날다","동의하다","수술하다","설명하다","증가하다",
                "질투하다 시기하다","알리다 밀고하다","도난 방지 자물쇠","참석하다",
                "생각나게 하다","빼앗다","빼앗다","자국","부족한","짧은 글","공립 도서관",
                "인지 아닌지","부족하다","바꾸어 말하자면(즉)","지겨운 따분한","에 매혹되다",
                "특징 성격","감각","강의","기억하다","방해하다","반대로","에 집중하다",
                "(정신이) 산만해지다","메스꺼워지는","고함치다","개발자","새우","굴 양식장",
                "생계비를 벌다","재난","습격하다","억수 폭우","적시다","(엔진의) 냉각기",
                "차고 수리소 주유소","불행하게도 ~하다","양철 깡통","미끼","기대다",
                "갈고리로 걸다","무너지다(=fall down)","기다","회의 회담","물러가다","훨 날다"};
        String[] eordata = new String[] {
                "content on","operate on","account to ","add to","envy","inform","remind","rob",
                "deprive","imprint","insufficient","notation","public library","whether","lack",
                "in other words","tiresome","be attracted to","character","sense","lecture",
                "retain","interfere","on the contray","focus on","wander","disgusting","shout",
                "developer","shrimp","oyster bed","make one's living","disaster","invade",
                "downpour","soak","radiator","garage","have the misfortune to","tin","bait",
                "lean","hook","collapse","crawl","anti-thief lock","attend","conference","retire",
                "flutter","content on","operate on","account to ","add to","envy","inform","remind"
                ,"rob","deprive","imprint","insufficient","notation","public library","whether",
                "in other words","tiresome","be attracted to","character","sense","lecture",
                "retain","interfere","on the contray","focus on","wander","disgusting","shout",
                "developer","shrimp","oyster bed","make one's living","disaster","invade",
                "downpour","soak","radiator","garage","have the misfortune to","tin","bait",
                "lean","hook","collapse","crawl","anti-thief lock","attend","conference","retire",
                "flutter","lack"};
        for(int i=0;i<kordata.length;i++) {
            data.add(new String[]{kordata[i], eordata[i]});
        }
        return data;
    }

    private void setWordBook(){
        ArrayList arrayList = new ArrayList();
        for(int i=0;i<data.keySet().size();i++){
            arrayList.add(new String(data.keySet().toArray()[i].toString()));
        }
        Collections.sort(arrayList);
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayList);
        spWold.setAdapter(arrayAdapter);
        spWold.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadWordBook();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void loadWordBook(){
        String title = spWold.getSelectedItem().toString();
        selData = (ArrayList<String[]>)data.get(title).clone();
        maxState = selData.size();
        nowState = 0;
        txtvMax.setText(maxState+"/"+(nowState));
        txtvKorean.setText(title);
        txtvEnglish.setText("Next 버튼을 눌러주세요!");
    }

    public void setRandomBook(){
        long seed = System.nanoTime();
        Collections.shuffle(selData, new Random(seed));
    }

    public void setNormalBook(){
        String title = spWold.getSelectedItem().toString();
        selData = (ArrayList<String[]>)data.get(title).clone();
    }

    public void onLeftClick(View v){
        int left = 1;
        if(nowState>1){
            left=nowState-1;
        }
        showData(left);
    }

    public void onRightClick(View v){
        int right = 100;
        if(nowState<99){
            right=nowState+1;
        }
        showData(right);
    }

    public void onRandomClick(View v){
        if(cbRandom.isChecked()==true)
            setRandomBook();
        else {
            setNormalBook();
        }
    }

    public void showAnswer(View v){
        txtvEnglish.setText(selData.get(nowState-1)[1]);
    }

    public void showData(int i){
        nowState = i;
        txtvMax.setText(maxState+"/"+(nowState));
        txtvKorean.setText(selData.get(i-1)[0]);
        if(cbHide.isChecked()==true)
            txtvEnglish.setText("Click!");
        else {
            txtvEnglish.setText(selData.get(i-1)[1]);
        }
    }
}
