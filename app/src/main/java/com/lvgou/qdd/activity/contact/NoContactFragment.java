package com.lvgou.qdd.activity.contact;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.http.URLConst;
import com.lvgou.qdd.http.VolleyRequest;
import com.lvgou.qdd.util.Logger;
import com.lvgou.qdd.util.ToastUtil;
import com.lvgou.qdd.util.TokenUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoContactFragment extends Fragment {

//    private Button clearTextBytton;

    private Button searchButton;

    private EditText editText;

    private TextView noUserTextView;

    private CallBackValue callBackValue;

    public  interface  CallBackValue{
        public void doListener(JSONObject map);
    }

    /**
     * fragment与activity产生关联是  回调这个方法
     */
    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        //当前fragment从activity重写了回调接口  得到接口的实例化
        // 对象
        callBackValue =(CallBackValue) getActivity();
    }

    public NoContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_no_contact, container, false);


//        clearTextBytton = (Button) view.findViewById(R.id.AddContactActivity_delete);
        searchButton = (Button) view.findViewById(R.id.AddContactActivity_search);
        editText = (EditText) view.findViewById(R.id.AddContactActivity_edit);
        noUserTextView = (TextView) view.findViewById(R.id.AddContactActivity_no_user);
        noUserTextView.setVisibility(View.INVISIBLE);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()==0){
                    noUserTextView.setVisibility(View.INVISIBLE);
                }
            }
        });

//        clearTextBytton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editText.setText("");
//            }
//        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netRequest();
            }
        });

        return view;
    }


    private void netRequest(){
        VolleyRequest request = new VolleyRequest();

        request.url = URLConst.URL_SEARCH_USER + TokenUtil.token;
        final Map<String,String>  map = new HashMap<>();
        map.put("idname",editText.getText().toString());

        Logger.getInstance(getActivity().getApplicationContext()).info("查找联系人参数是 :" + map.toString());

        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getActivity().getApplicationContext()).info("查找联系人成功");

                Map<String,Object> map = JSON.parseObject(response,new HashMap<String,Object>().getClass());

                Map<String,Object> data = (Map<String,Object>) map.get("data");
                if (null == data){
                    noUserTextView.setVisibility(View.VISIBLE);
                    return;
                }

                if (! (data.get("res") instanceof Map)){
                    ToastUtil.showToast(getActivity().getApplicationContext(),"联系人已添加");
                    return;
                }

                JSONObject jsonObject = (JSONObject)data.get("res");
                jsonObject.put("search",editText.getText());

                //利用接口回调传值
                callBackValue.doListener(jsonObject);

            }

            @Override
            public void fail(String response) {

                ((BaseActivity)getActivity()).gotoLoginActivity();
            }
        });
        Logger.getInstance(getActivity().getApplicationContext()).info("查找联系人列表：" + request.url);
        request.postRequest(getActivity().getApplicationContext(),map);
    }

}
