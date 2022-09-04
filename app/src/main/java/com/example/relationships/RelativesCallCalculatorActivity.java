package com.example.relationships;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RelativesCallCalculatorActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private List<Button> buttonList = new ArrayList<>();
    ImageButton iv_del;
    Switch sw_sex;
    TextView tv_input;
    TextView tv_results;

    // 初始值的性别
    private boolean init_sex;
    // 当前结果的性别
    private boolean sex;
    // 操作数数组
    private List<String> callList = new ArrayList<>();
    // 显示的文本内容
    private String showText = "";
    // 当前运算结果
    private String resultsText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取控件，并添加点击事件
        findViewById(R.id.iv_back).setOnClickListener(this);

        buttonList.add(findViewById(R.id.btn_h));
        buttonList.add(findViewById(R.id.btn_w));
        buttonList.add(findViewById(R.id.btn_f));
        buttonList.add(findViewById(R.id.btn_m));
        buttonList.add(findViewById(R.id.btn_ob));
        buttonList.add(findViewById(R.id.btn_lb));
        buttonList.add(findViewById(R.id.btn_os));
        buttonList.add(findViewById(R.id.btn_ls));
        buttonList.add(findViewById(R.id.btn_s));
        buttonList.add(findViewById(R.id.btn_d));
        buttonList.add(findViewById(R.id.btn_each));
        buttonList.add(findViewById(R.id.btn_eq));
        buttonList.add(findViewById(R.id.btn_clr));
        iv_del = findViewById(R.id.iv_del);
        sw_sex = findViewById(R.id.sw_sex);
        tv_input = findViewById(R.id.tv_input);
        tv_results = findViewById(R.id.tv_results);

        callList.add("我");
        init_sex = sex = sw_sex.isChecked();
        sw_sex.setOnCheckedChangeListener(this);
        // 给按钮设置的点击事件
        for (Button button : buttonList) {
            button.setOnClickListener(this);
        }
        iv_del.setOnClickListener(this);
        forbiddenButton();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() != R.id.iv_back && v.getId() != R.id.btn_clr
                && v.getId() != R.id.iv_del && v.getId() != R.id.btn_eq
                && resultsText.equals("关系有点远，年长就叫老祖宗~\n同龄人就叫帅哥美女吧"))
            return;
        switch (v.getId()) {
            // 点击了返回按钮
            case R.id.iv_back:
                finish();
                break;

            // 点击了清除按钮
            case R.id.btn_clr:
                emphasisShowInput();
                init_sex = sex = sw_sex.isChecked();
                forbiddenButton();
                clear();
                break;

            // 点击了删除按钮
            case R.id.iv_del:
                emphasisShowInput();
                delete();
                if (callList.size() > 1) {
                    if (isMan(callList.get(callList.size() - 1)))
                        sex = false;
                    else
                        sex = true;
                } else {
                    sex = sw_sex.isChecked();
                }
                forbiddenButton();
                break;

            // 点击了等于按钮
            case R.id.btn_eq:
                if (callList.size() <= 1)
                    break;
                emphasisShowResults();
                if (!showText.equals("TA称呼我") && !resultsText.equals("关系有点远，年长就叫老祖宗~\n同龄人就叫帅哥美女吧")) {
                    buttonList.get(10).setEnabled(true);
                    buttonList.get(10).setTextColor(Color.parseColor("#FF000000"));
                    refreshText();
                }
                break;

            // 点击了互查按钮
            case R.id.btn_each:
                if (!showText.equals("TA称呼我")) {
                    emphasisShowResults();
                    peerReview();
                } else {
                    emphasisShowInput();
                    refreshText();
                    buttonList.get(10).setEnabled(false);
                    buttonList.get(10).setTextColor(Color.parseColor("#FFD6D6D6"));
                }
                break;

            // 点击了亲戚关系按钮
            case R.id.btn_h:
                emphasisShowInput();
                callList.add("丈夫");
                sex = false;
                forbiddenButton();
                refreshText();
                break;

            case R.id.btn_w:
                emphasisShowInput();
                callList.add("妻子");
                sex = true;
                forbiddenButton();
                refreshText();
                break;

            case R.id.btn_f:
                emphasisShowInput();
                callList.add("爸爸");
                sex = false;
                forbiddenButton();
                refreshText();
                break;

            case R.id.btn_m:
                emphasisShowInput();
                callList.add("妈妈");
                sex = true;
                forbiddenButton();
                refreshText();
                break;

            case R.id.btn_ob:
                emphasisShowInput();
                callList.add("哥哥");
                sex = false;
                forbiddenButton();
                refreshText();
                break;

            case R.id.btn_lb:
                emphasisShowInput();
                callList.add("弟弟");
                sex = false;
                forbiddenButton();
                refreshText();
                break;

            case R.id.btn_os:
                emphasisShowInput();
                callList.add("姐姐");
                sex = true;
                forbiddenButton();
                refreshText();
                break;

            case R.id.btn_ls:
                emphasisShowInput();
                callList.add("妹妹");
                sex = true;
                forbiddenButton();
                refreshText();
                break;

            case R.id.btn_s:
                emphasisShowInput();
                callList.add("儿子");
                sex = false;
                forbiddenButton();
                refreshText();
                break;

            case R.id.btn_d:
                emphasisShowInput();
                callList.add("女儿");
                sex = true;
                forbiddenButton();
                refreshText();
                break;
        }
    }

    // 清空并初始化
    private void clear() {
        callList.clear();
        callList.add("我");
        resultsText = "";
        refreshText();
    }

    // 刷新文本显示
    private void refreshText() {
        showText = "";
        resultsText = "";

        for (int i = 0; i < callList.size(); i++) {
            showText += callList.get(i);
            if (i == callList.size() - 1)
                break;
            showText += "的";
        }
        if (callList.size() > 8)
            resultsText = "关系有点远，年长就叫老祖宗~\n同龄人就叫帅哥美女吧";
        else if (callList.size() > 1)
            operation(callList, init_sex);

        tv_input.setText(showText);
        tv_results.setText(resultsText);
    }

    // 回退
    private void delete() {
        if (callList.size() > 1) {
            callList.remove(callList.size() - 1);
            operation(callList, init_sex);
            refreshText();
        }
    }

    // 运算
    private void operation(List<String> list, boolean b) {
        String[][] relationshipData;
        if (b)
            relationshipData = new RelationShipData().getRelationShipDataByWoman();
        else
            relationshipData = new RelationShipData().getRelationShipDataByMan();
        int column = 0, row = 0;
        String resultValue = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            for (int m = 0; m < relationshipData.length; ++m) {
                if (relationshipData[m][0].equals(resultValue)) {
                    row = m;
                    break;
                }
            }
            for (int n = 0; n < relationshipData[0].length; n++) {
                if (relationshipData[0][n].equals(list.get(i))) {
                    column = n;
                    break;
                }
            }
            resultValue = relationshipData[row][column];
            if (!isExist(resultValue, relationshipData)) {
                resultValue = "未知亲戚";
                break;
            }
        }
        if (resultValue.equals("未知亲戚") || resultValue.equals(""))
            resultsText = "关系有点远，年长就叫老祖宗~\n同龄人就叫帅哥美女吧";
        else
            resultsText = resultValue;
    }

    // 判断某个值在二维数组中的行首值中是否存在
    public boolean isExist(String value, String[][] array) {
        for (int i = 0; i < array.length; i++) {
            if (value.equals(array[i][0])) {
                return true;
            }
        }
        return false;
    }

    // 互查
    private void peerReview() {
        showText = "TA称呼我";
        List<String> tempList = new ArrayList<>();
        boolean tempSex;
        tempList.add("我");
        for (int i = callList.size() - 1; i > 0; i--) {
            if ((callList.get(i - 1).equals("我") && !init_sex) || isMan(callList.get(i - 1))) {
                if (callList.get(i).equals("儿子") || callList.get(i).equals("女儿"))
                    tempList.add("爸爸");
                else if (callList.get(i).equals("弟弟") || callList.get(i).equals("妹妹"))
                    tempList.add("哥哥");
                else if (callList.get(i).equals("哥哥") || callList.get(i).equals("姐姐"))
                    tempList.add("弟弟");
                else if (callList.get(i).equals("爸爸") || callList.get(i).equals("妈妈"))
                    tempList.add("儿子");
                else if (callList.get(i).equals("妻子"))
                    tempList.add("丈夫");
            } else {
                if (callList.get(i).equals("儿子") || callList.get(i).equals("女儿"))
                    tempList.add("妈妈");
                else if (callList.get(i).equals("弟弟") || callList.get(i).equals("妹妹"))
                    tempList.add("姐姐");
                else if (callList.get(i).equals("哥哥") || callList.get(i).equals("姐姐"))
                    tempList.add("妹妹");
                else if (callList.get(i).equals("爸爸") || callList.get(i).equals("妈妈"))
                    tempList.add("女儿");
                else if (callList.get(i).equals("丈夫"))
                    tempList.add("妻子");
            }
        }
        // 判断“我”的性别
        if (isMan(callList.get(callList.size() - 1)))
            tempSex = false;
        else
            tempSex = true;
        operation(tempList, tempSex);
        tv_input.setText(showText);
        tv_results.setText(resultsText);
    }

    // 判断该亲戚是否为男性
    private boolean isMan(String s) {
        if (s.equals("丈夫") || s.equals("爸爸") || s.equals("哥哥")
                || s.equals("弟弟") || s.equals("儿子"))
            return true;
        else
            return false;
    }

    // 重点显示输入
    private void emphasisShowInput() {
        tv_input.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        tv_input.setTextColor(Color.parseColor("#FF000000"));
        tv_results.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        tv_results.setTextColor(Color.parseColor("#FF959595"));
        buttonList.get(10).setEnabled(false);
        buttonList.get(10).setTextColor(Color.parseColor("#FFD6D6D6"));
    }

    // 重点显示结果
    private void emphasisShowResults() {
        tv_results.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        tv_results.setTextColor(Color.parseColor("#FF000000"));
        tv_input.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        tv_input.setTextColor(Color.parseColor("#FF959595"));
    }

    // 禁用夫 \ 妻按钮
    private void forbiddenButton() {
        if (sex) {
            buttonList.get(1).setTextColor(Color.parseColor("#FFD6D6D6"));
            buttonList.get(1).setEnabled(false);
            buttonList.get(0).setTextColor(Color.parseColor("#FF000000"));
            buttonList.get(0).setEnabled(true);
        } else {
            buttonList.get(0).setTextColor(Color.parseColor("#FFD6D6D6"));
            buttonList.get(0).setEnabled(false);
            buttonList.get(1).setTextColor(Color.parseColor("#FF000000"));
            buttonList.get(1).setEnabled(true);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        init_sex = sex = b;
        clear();
        forbiddenButton();
        emphasisShowInput();
    }
}
