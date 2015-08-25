package info.androidhive.materialdesign.FragmentDetial;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.activity.ListChatUser;
import info.androidhive.materialdesign.activity.LoginActivity;
import info.androidhive.materialdesign.activity.OrderCarListUser;
import info.androidhive.materialdesign.adapter.ImageHomeAdapter;
import info.androidhive.materialdesign.function_api.UserFunctions;
import info.androidhive.materialdesign.json_url.ManageActivityTag;
import info.androidhive.materialdesign.json_url.UrlJsonLink;

public class FragmentChildDetail extends Fragment implements View.OnClickListener{
    private Button BtnOrder,BtnChat;
    String filename = "ShareDataDetail";
    private SharedPreferences detailDataShare;
    private  static SharedPreferences user;
    private  String member_id;
	public FragmentChildDetail(){}

    //=====schema get share
    public static String getShareCarModel;
    public static String getShareCarYear;
    public static String getShareCarYearStart;
    public static String getShareCarYearTransmission;
    public static String getShareCountry;
    public static String getShareCarCity ;
    public static String getShareCaStockNo;
    public static String getShareChasnisNo;
    public static String getShareIconStatus;
    public static String getShareCarGrand;
    public static String getShareCarCC;
    public static String getShareCarMilleag;
    public static String getShareCarFuel ;
    public static String getShareCarColor ;
    public static String getShareCarSeat;
    public static String getShareCarBodyType ;
    public static String getShareCarDriveType ;
    public static String getShareCarCost;
    public static String getShareCarFOB;
    public static String getShareCarBuyingPrin;
    public static String getShareCarMake;
    public static String getShareCarFirstRag;
    public static String getShareCarBuyingCurr;


	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.detail_data, container, false);
        //**** Get data from shareReference ***********************************
        detailDataShare            = getActivity().getSharedPreferences(filename, 0);


        getShareCarModel           = detailDataShare.getString(UrlJsonLink.ShareCarModel, null);
        getShareCarYear            = detailDataShare.getString(UrlJsonLink.ShareCarYear, null);
        getShareCarYearStart       = detailDataShare.getString(UrlJsonLink.ShareCarYearStart,null);
        getShareCarYearTransmission= detailDataShare.getString(UrlJsonLink.ShareCarYearTransmission,null);
        getShareCountry            = detailDataShare.getString(UrlJsonLink.ShareCountry,null);
        getShareCarCity            = detailDataShare.getString(UrlJsonLink.ShareCarCity,null) ;
        getShareCaStockNo          = detailDataShare.getString(UrlJsonLink.ShareCaStockNo,null);
        getShareChasnisNo          = detailDataShare.getString(UrlJsonLink.ShareChasnisNo,null);
        getShareIconStatus         = detailDataShare.getString(UrlJsonLink.ShareIconStatus,null);
        getShareCarGrand           = detailDataShare.getString(UrlJsonLink.ShareCarGrand,null);
        getShareCarCC              = detailDataShare.getString(UrlJsonLink.ShareCarCC,null);
        getShareCarMilleag         = detailDataShare.getString(UrlJsonLink.ShareCarMilleag,null);
        getShareCarFuel            = detailDataShare.getString(UrlJsonLink.ShareCarFuel,null);
        getShareCarColor           = detailDataShare.getString(UrlJsonLink.ShareCarColor,null);
        getShareCarSeat            = detailDataShare.getString(UrlJsonLink.ShareCarSeat,null);
        getShareCarBodyType        = detailDataShare.getString(UrlJsonLink.ShareCarBodyType,null);
        getShareCarDriveType       = detailDataShare.getString(UrlJsonLink.ShareCarDriveType,null) ;
        getShareCarCost            = detailDataShare.getString(UrlJsonLink.ShareCarCost, null);
        getShareCarFOB             = detailDataShare.getString(UrlJsonLink.ShareCarFOB,null);
        getShareCarBuyingPrin      = detailDataShare.getString(UrlJsonLink.ShareCarBuyingPrin,null);
        getShareCarBuyingCurr      = detailDataShare.getString(UrlJsonLink.ShareCarBuyingCurr,null);
        getShareCarMake            = detailDataShare.getString(UrlJsonLink.ShareCarMake,null);
        getShareCarFirstRag        = detailDataShare.getString(UrlJsonLink.ShareCarFirstRag,null);
        getShareCarColor           = detailDataShare.getString(UrlJsonLink.ShareCarColor,null);

        TextView textViewTitle = (TextView)rootView.findViewById(R.id.txt_title_header);
        textViewTitle.setText(getShareCarMake + " " + getShareCarModel + " " + getShareCarYearStart);
        TextView textViewLocation = (TextView)rootView.findViewById(R.id.txt_Location);
        textViewLocation.setText(getShareCountry);

        TextView textViewCity = (TextView)rootView.findViewById(R.id.txt_City);
        textViewCity.setText(getShareCarCity);

        TextView textViewOfferNo = (TextView)rootView.findViewById(R.id.txt_offerNo);
        textViewOfferNo.setText("NO." + getShareCaStockNo);

        TextView textViewChasNo = (TextView)rootView.findViewById(R.id.txt_chassNo);
        textViewChasNo.setText(getShareChasnisNo);

        TextView textViewCarStatus = (TextView)rootView.findViewById(R.id.txt_carStatus);
        textViewCarStatus.setText(getShareIconStatus);

        TextView textViewMake = (TextView)rootView.findViewById(R.id.txt_Make);
        textViewMake.setText(getShareCarMake);

        TextView textViewModel = (TextView)rootView.findViewById(R.id.txt_Model);
        textViewModel.setText(getShareCarModel);

        TextView textViewGrand = (TextView)rootView.findViewById(R.id.txt_Grand);
        textViewGrand.setText(getShareCarGrand);

        TextView textViewModelYear = (TextView)rootView.findViewById(R.id.txt_ModelYear);
        textViewModelYear.setText(getShareCarYearStart+" YEAR");

        TextView textViewanuFacture = (TextView)rootView.findViewById(R.id.txt_ManuFacture);
        //textViewanuFacture.setText(obj.getString("country"));

        TextView textViewirstRag = (TextView)rootView.findViewById(R.id.txt_FirstRag);
        textViewirstRag.setText(getShareCarFirstRag+"(MONTH)/"+getShareCarYearStart+"(YEAR)");

        TextView textViewTransmission = (TextView)rootView.findViewById(R.id.TranSmisstionCar);
        textViewTransmission.setText(getShareCarYearTransmission);

        Log.d("getShareCarCC", "" + getShareCarCC);
        TextView textViewEnginSize = (TextView)rootView.findViewById(R.id.EnGinSize);
        if (getShareCarCC!=null) {
            if (!getShareCarCC.equals("0")) {
                textViewEnginSize.setText(getShareCarCC);
            } else {
                textViewEnginSize.setText("");
            }
        }
        Log.d("getShareCarMilleag",""+getShareCarMilleag);
        TextView textViewMileage = (TextView)rootView.findViewById(R.id.txt_Mileage);
        if (getShareCarMilleag!=null) {
            if (!getShareCarMilleag.equals("0")) {
                int numberMileag = Integer.parseInt(getShareCarMilleag.toString());
                DecimalFormat dfds = new DecimalFormat("#,###");
                String mealigeSize = dfds.format(numberMileag);
                textViewMileage.setText(mealigeSize + "KM");
            } else {
                textViewMileage.setText("");
            }
        }

        TextView textViewFuel = (TextView)rootView.findViewById(R.id.txt_Fuel);
        textViewFuel.setText(getShareCarFuel);

        TextView textViewColor = (TextView)rootView.findViewById(R.id.txt_Color);
        textViewColor.setText(getShareCarColor);

        TextView textViewCarSeat = (TextView)rootView.findViewById(R.id.txt_CarSeat);
        textViewCarSeat.setText(getShareCarSeat);

        TextView textViewodyType = (TextView)rootView.findViewById(R.id.txt_BodyType);
        textViewodyType.setText(getShareCarBodyType);

        TextView textViewDriverType = (TextView)rootView.findViewById(R.id.txt_DriverType);
        textViewDriverType.setText(getShareCarDriveType);

        TextView textViewFOBLocation = (TextView)rootView.findViewById(R.id.txt_FOB);
        Log.d("getShareCarCost",""+getShareCarCost);
        if (getShareCarCost != null) {
            Log.d("getShareCarCost",""+getShareCarCost);
            if (!getShareCarCost.equals("0")) {
                int numberMoney = Integer.parseInt(getShareCarCost.toString());
                DecimalFormat df = new DecimalFormat("#,###");
                String result = df.format(numberMoney);
                textViewFOBLocation.setText(result + " " + getShareCarFOB + "($)");
            }else{
                textViewFOBLocation.setText("Ask For Price");
            }
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //***************************Button Order And Chat***********************************
        BtnOrder = (Button)getActivity().findViewById(R.id.btnOrder);
        BtnOrder.setOnClickListener(this);
        //BtnChat = (Button)getActivity().findViewById(R.id.btnChat);
       // BtnChat.setOnClickListener(this);

    }

//********************View implement OnClick Action**********************************
    @Override
    public void onClick(View v) {
        //******Schema intnen ************
        Intent intentActivity;
        //******Check User Infor ************
        user = getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        member_id = user.getString("member_id", "");
        //******get Id View ************

        switch (v.getId()){
            case R.id.btnOrder:
                if (!member_id.equals("")){
                    new ProcessOrderPOST().execute();
                }else{
                    SharedPreferences.Editor  editorUser =  user.edit();
                    editorUser.putString(ManageActivityTag.TAG_DETAIL_ACTIVITY,ManageActivityTag.TAG_DETAIL_ACTIVITY);
                    editorUser.commit();
                    intentActivity = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intentActivity);
                    getActivity().overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
                }


                break;
//            case R.id.btnChat:
//                if (!member_id.equals("")){
//                    intentActivity = new Intent(getActivity(),ListChatUser.class);
//                    startActivity(intentActivity);
//                }else{
//                    SharedPreferences.Editor  editorUser =  user.edit();
//                    editorUser.putString(ManageActivityTag.TAG_CHAT_ACTIVITY,ManageActivityTag.TAG_CHAT_ACTIVITY);
//                    editorUser.commit();
//                    intentActivity = new Intent(getActivity(),LoginActivity.class);
//                    startActivity(intentActivity);
//                    getActivity().overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
//                }
//
//                break;
        }

    }


    private class ProcessOrderPOST extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog nDialog;
        private String member_no,remember_token,CarID;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(getActivity());
            nDialog.setMessage("Loading..");
            //nDialog.setTitle("Checking Internate");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();

            SharedPreferences user = getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
            SharedPreferences CheckCarID = getActivity().getSharedPreferences("CheckCarID", 0);
            member_no      = user.getString("member_no", "");
            remember_token = user.getString("remember_token", "");
            CarID          = CheckCarID.getString("indexID", "");
            Log.d("PostOrder",""+member_no+","+remember_token+","+CarID);
        }
        @Override
        protected JSONObject doInBackground(String... args) {
            UserFunctions userFunction = new UserFunctions();
            JSONObject json = userFunction.ReVersCar(member_no, remember_token,CarID );
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            String result;
            String sucessMsg;
            try {
                result = json.getString("success");
                //msg_error = json.getString("error_msg");
                sucessMsg = json.getString("msg");
                Log.e("Json_DAT", ""+result);
                Log.e("Json_DAT", ""+sucessMsg);

                if(result.equals("1")){
                    Log.e("JsonRe1", ""+result);
                    Log.e("Json_re1Er", ""+sucessMsg);
                    //pDialog.dismiss();
                    Toast.makeText(getActivity(), sucessMsg, Toast.LENGTH_LONG).show();
                }else if(result.equals("0")){
                    Log.e("JsonRe0", ""+result);
                    Log.e("Json_re0Er", ""+sucessMsg);
                    //pDialog.dismiss();
                    Toast.makeText(getActivity(), sucessMsg, Toast.LENGTH_LONG).show();
                }
                nDialog.dismiss();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onStop() {
        super.onStop();
      SharedPreferences.Editor dadb=  detailDataShare.edit();
        dadb.clear();
    }
}
