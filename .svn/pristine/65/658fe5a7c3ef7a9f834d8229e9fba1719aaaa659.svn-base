package vtc.game.app.vcoin.vtcpay.view.account;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.nfc.tech.NfcBarcode;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.strategy.intecom.vtc.common.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import vtc.game.app.vcoin.vtcpay.R;
import vtc.game.app.vcoin.vtcpay.common.AppBase;
import vtc.game.app.vcoin.vtcpay.common.VtcString;
import vtc.game.app.vcoin.vtcpay.connection.VtcConnection;
import vtc.game.app.vcoin.vtcpay.connection.VtcHttpConnection;
import vtc.game.app.vcoin.vtcpay.enums.TypeActionConnection;
import vtc.game.app.vcoin.vtcpay.interfaces.RequestListener;
import vtc.game.app.vcoin.vtcpay.model.City;
import vtc.game.app.vcoin.vtcpay.model.District;
import vtc.game.app.vcoin.vtcpay.model.SecretQuestion;
import vtc.game.app.vcoin.vtcpay.model.Ward;
import vtc.game.app.vcoin.vtcpay.utils.Const;
import vtc.game.app.vcoin.vtcpay.utils.ConstParam;

/**
 * Created by ThuyChi on 10/5/2016.
 */
public class FMProfileAccount extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, RequestListener {
    private View viewRoot;
    private TextView tvTitleView, tvSurplus, tvVcoinAvailable;
    private TextView tvAccountNo, tvRegisterDate, tvEmail, tvPhoneNo, tvError, tvOtpGuide;
    private LinearLayout loutAccountDetail, loutAccountOwnerDetail, loutSecretQuestion;
    private ImageView imgArrowAccount, imgArrowOwnerAccount;
    private TextView tvDob;
    private DatePickerDialog dobPickerDialog;
    private EditText editAccountName, editEmail, editPhoneNo, editAddress, editSecretQuestion, editOtpCode, editPassport;
    private Spinner spSex, spCity, spDistrict, spCommune, spSecretQuestion;
    private Bundle bundle = new Bundle();
    private VtcConnection vtcConnection;
    private RelativeLayout loutEmailActived, loutPhoneActive, loutEmailEdit, loutPhoneEdit, loutOtp, loutPassportEdit;
    private int isUpdate = 0; // 0 is insert, 1 is update
    private int locationId = 0;
    private int districtId = 0;
    private int wardId = 0;
    private Button btnPhoneVerify;


    public FMProfileAccount() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.ui_profile_account, container, false);
        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vtcConnection = new VtcConnection(getContext(), this);

        tvTitleView = (TextView) view.findViewById(R.id.tv_Titile_View);
        tvTitleView.setText(getResources().getString(R.string.title_View_Profile_Account));

        ImageView imgBack = (ImageView) view.findViewById(R.id.img_Back);
        imgBack.setOnClickListener(this);

        RelativeLayout loutProfileInformation = (RelativeLayout) view.findViewById(R.id.lout_Profile_Information);
        loutProfileInformation.setOnClickListener(this);

        RelativeLayout loutProfileOwnerInformation = (RelativeLayout) view.findViewById(R.id.lout_Profile_Onwer_Information);
        loutProfileOwnerInformation.setOnClickListener(this);

        imgArrowAccount = (ImageView) view.findViewById(R.id.img_Arrow_Account);
        imgArrowOwnerAccount = (ImageView) view.findViewById(R.id.img_Arrow_Owner_Account);

        loutAccountDetail = (LinearLayout) view.findViewById(R.id.lout_Account_Detail);
        loutAccountDetail.setVisibility(View.VISIBLE);
        loutAccountOwnerDetail = (LinearLayout) view.findViewById(R.id.lout_Owner_Account_Detail);
        loutAccountOwnerDetail.setVisibility(View.VISIBLE);
        loutSecretQuestion = (LinearLayout) view.findViewById(R.id.lout_Secret_Question);

        loutEmailActived = (RelativeLayout) view.findViewById(R.id.lout_Email_Acived);
        loutEmailActived.setVisibility(View.GONE);
        if (Const.ACCOUNT_INFOR_EMAIL == Const.HIDE_VALUE) {
            loutEmailActived.setVisibility(View.GONE);
        } else {
            loutEmailActived.setVisibility(View.VISIBLE);
        }

        loutPhoneActive = (RelativeLayout) view.findViewById(R.id.lout_Phone_actived);
        loutPhoneActive.setVisibility(View.GONE);
        if (Const.ACCOUNT_INFOR_PHONE_NO == Const.HIDE_VALUE) {
            loutPhoneActive.setVisibility(View.GONE);
        } else {
            loutPhoneActive.setVisibility(View.VISIBLE);
        }

        loutEmailEdit = (RelativeLayout) view.findViewById(R.id.lout_Edit_Email);
        loutPhoneEdit = (RelativeLayout) view.findViewById(R.id.lout_Edit_Phone_No);
        loutOtp = (RelativeLayout) view.findViewById(R.id.lout_Otp);
        loutOtp.setVisibility(View.GONE);
        loutPassportEdit = (RelativeLayout) view.findViewById(R.id.lout_Edit_Passport);
        loutPassportEdit.setVisibility(View.GONE);

        tvSurplus = (TextView) view.findViewById(R.id.tv_Vcoin_Surplus);
        tvVcoinAvailable = (TextView) view.findViewById(R.id.tv_Vcoin_Available);

        tvAccountNo = (TextView) view.findViewById(R.id.tv_Account_No);
        tvRegisterDate = (TextView) view.findViewById(R.id.tv_Register_Date);
        tvEmail = (TextView) view.findViewById(R.id.tv_Email);
        tvPhoneNo = (TextView) view.findViewById(R.id.tv_Phone_No);
        tvError = (TextView) view.findViewById(R.id.tv_Error_Message);
        tvOtpGuide = (TextView) view.findViewById(R.id.tv_Otp_Guide);
        tvOtpGuide.setVisibility(View.GONE);

        editAccountName = (EditText) view.findViewById(R.id.edit_Account_Name);
        editEmail = (EditText) view.findViewById(R.id.edit_Email);
        editPhoneNo = (EditText) view.findViewById(R.id.edit_Phone_No);
        editAddress = (EditText) view.findViewById(R.id.edit_Address);
        editSecretQuestion = (EditText) view.findViewById(R.id.edit_Secret_Question);
        editOtpCode = (EditText) view.findViewById(R.id.edit_Otp_Code);
        editPassport = (EditText) view.findViewById(R.id.edit_Passport);

        spSex = (Spinner) view.findViewById(R.id.sp_Sex);
        String sexArray[] = getResources().getStringArray(R.array.sex_type);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, sexArray) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }

            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(com.strategy.intecom.vtc.vtclogin.R.layout.sdk_spiner_dropdown);
        spSex.setAdapter(spinnerArrayAdapter);
        spSex.setOnItemSelectedListener(this);

        spCity = (Spinner) view.findViewById(R.id.sp_City);
//        String cityArray[] = getResources().getStringArray(R.array.sex_type);
//        List<String> cityList = new ArrayList<String>();
//        cityList.add(0, getResources().getString(R.string.spinner_Label_City));
//        Collections.addAll(cityList, cityArray);
//        ArrayAdapter<String> cityArrayAdt = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, cityList);
//        spinnerArrayAdapter.setDropDownViewResource(com.strategy.intecom.vtc.vtclogin.R.layout.sdk_spiner_dropdown);
//        spCity.setAdapter(cityArrayAdt);
//        spCity.setOnItemSelectedListener(this);

        spDistrict = (Spinner) view.findViewById(R.id.sp_District);
//        spDistrict.setEnabled(false);
//        String districtArray[] = getResources().getStringArray(R.array.sex_type);
//        List<String> districtList = new ArrayList<String>();
//        districtList.add(0, getResources().getString(R.string.spinner_Label_District));
//        Collections.addAll(districtList, districtArray);
//        ArrayAdapter<String> districtArrayAdt = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, districtList);
//        spinnerArrayAdapter.setDropDownViewResource(com.strategy.intecom.vtc.vtclogin.R.layout.sdk_spiner_dropdown);
//        spDistrict.setAdapter(districtArrayAdt);
//        spDistrict.setOnItemSelectedListener(this);

        spCommune = (Spinner) view.findViewById(R.id.sp_Commune);
//        spCommune.setEnabled(false);
//        String communeArray[] = getResources().getStringArray(R.array.sex_type);
//        List<String> communeList = new ArrayList<String>();
//        communeList.add(0, getResources().getString(R.string.spinner_Label_Commune));
//        Collections.addAll(communeList, communeArray);
//        ArrayAdapter<String> communeArrayAdt = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, communeList);
//        spinnerArrayAdapter.setDropDownViewResource(com.strategy.intecom.vtc.vtclogin.R.layout.sdk_spiner_dropdown);
//        spCommune.setAdapter(communeArrayAdt);
//        spCommune.setOnItemSelectedListener(this);

        spSecretQuestion = (Spinner) view.findViewById(R.id.sp_Secret_Question);
//        String questionArray[] = getResources().getStringArray(R.array.sex_type);
//        List<String> questionList = new ArrayList<String>();
//        communeList.add(0, getResources().getString(R.string.spinner_Label_Secret_Question));
//        Collections.addAll(questionList, questionArray);
//        ArrayAdapter<String> questionArrayAdt = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, questionList);
//        spinnerArrayAdapter.setDropDownViewResource(com.strategy.intecom.vtc.vtclogin.R.layout.sdk_spiner_dropdown);
//        spSecretQuestion.setAdapter(questionArrayAdt);
//        spSecretQuestion.setOnItemSelectedListener(this);

        Button btnUpdate = (Button) view.findViewById(R.id.btn_Update);
        btnUpdate.setOnClickListener(this);

        Button btnEmailEdit = (Button) view.findViewById(R.id.btn_Email_Edit);
        btnEmailEdit.setOnClickListener(this);
        btnEmailEdit.setVisibility(View.GONE);
        if (Const.ACCOUNT_EDIT_EMAIL == Const.HIDE_VALUE) {
            btnEmailEdit.setVisibility(View.GONE);
        } else {
            btnEmailEdit.setVisibility(View.VISIBLE);
        }


        Button btnPhoneEdit = (Button) view.findViewById(R.id.btn_Phone_Edit);
        btnPhoneEdit.setOnClickListener(this);
        btnPhoneEdit.setVisibility(View.GONE);
        if (Const.ACCOUNT_EDIT_PHONE_NO == Const.HIDE_VALUE) {
            btnPhoneEdit.setVisibility(View.GONE);
        } else {
            btnPhoneEdit.setVisibility(View.VISIBLE);
        }

        Button btnPhoneRemove = (Button) view.findViewById(R.id.btn_Phone_Remove);
        btnPhoneRemove.setOnClickListener(this);
        btnPhoneRemove.setVisibility(View.GONE);
        if (Const.ACCOUNT_REMOVE_PHONE_NO == Const.HIDE_VALUE) {
            btnPhoneRemove.setVisibility(View.INVISIBLE);
        } else {
            btnPhoneRemove.setVisibility(View.VISIBLE);
        }

        btnPhoneVerify = (Button) view.findViewById(R.id.btn_Phone_Verify);
        btnPhoneVerify.setOnClickListener(this);
        btnPhoneVerify.setVisibility(View.GONE);
        if (Const.ACCOUNT_VERIFY_PHONE_NO == Const.HIDE_VALUE) {
            btnPhoneVerify.setVisibility(View.INVISIBLE);
        } else {
            btnPhoneVerify.setVisibility(View.VISIBLE);
        }

        Button btnEmailVerify = (Button) view.findViewById(R.id.btn_Email_Verify);
        btnEmailVerify.setOnClickListener(this);

        tvDob = (TextView) view.findViewById(R.id.tv_Dob);
        tvDob.setOnClickListener(this);

//        String mail = "phamluanhanu2010@gmail.com";
//        String substr2 = mail.substring(mail.indexOf("@"), mail.indexOf("@") + 2);
//        String substr = mail.substring(0, 3);
//        mail = substr + "***" + substr2 + "***";
//        tvEmail.setText(mail);
//
//        String phone = "0984676445";
//        String subphone = phone.substring(0, 3);
//        phone = subphone + "********";
//        tvPhoneNo.setText(phone);

        Calendar calendar = Calendar.getInstance();
        dobPickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_Material_Light_Dialog_Alert, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int month = monthOfYear + 1;
                String dob = dayOfMonth + "/" + month + "/" + year;
                tvDob.setText(dob);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));


        initGetAccountDetail();
        initDistrictHandle("[]");
        initWardHandle("[]");
    }

    /**
     * Request secret question data
     */
    private void initGetSecretQuestion() {
        if (vtcConnection != null) {
            vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_GET_SECRET_QUESTION, RequestListener.API_CONNECTION_GET_SECRET_QUESTION, true, false);
        }
    }

    /**
     * Request City data
     */
    private void initGetCity() {
        if (vtcConnection != null) {
            vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_GET_CITY, RequestListener.API_CONNECTION_GET_CITY, true, false);
        }
    }

    /**
     * Request district data
     *
     * @param locationId City id
     */
    private void initGetDistrict(int locationId) {
        Map<String, String> mapDistrict = new HashMap<>();
        mapDistrict.put(ConstParam.PARAM_REQUEST_LOCATION_ID, String.valueOf(locationId));

        if (vtcConnection != null) {
            vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_GET_DISTRICT, RequestListener.API_CONNECTION_GET_DISTRICT + VtcHttpConnection.urlEncodeUTF8(mapDistrict), true, false);
        }
    }

    /**
     * Request Ward data
     *
     * @param districtId District id
     */
    private void initGetWard(int districtId) {
        Map<String, String> mapDistrict = new HashMap<>();
        mapDistrict.put(ConstParam.PARAM_REQUEST_DISTRICT_ID, String.valueOf(districtId));

        if (vtcConnection != null) {
            vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_GET_WARD, RequestListener.API_CONNECTION_GET_WARD + VtcHttpConnection.urlEncodeUTF8(mapDistrict), true, false);
        }
    }

    /**
     * Add secret question data into spinner
     *
     * @param data Secret question data
     */
    private void initQuestionHandle(String data) {
        try {
            JSONArray jsonArrayData = new JSONArray(data);
            ArrayList<SecretQuestion> question = new ArrayList<>();

            question.add(new SecretQuestion("Câu hỏi bảo mật", 0));
            for (int i = 0; i < jsonArrayData.length(); i++) {
                question.add(new SecretQuestion(jsonArrayData.optJSONObject(i).optString("questionName"), jsonArrayData.optJSONObject(i).optInt("questionID")));

            }


            ArrayAdapter<SecretQuestion> adapter =
                    new ArrayAdapter<SecretQuestion>(getContext(), R.layout.spinner_item, question) {
                        @Override
                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if (position == 0) {
                                // Set the hint text color gray
                                tv.setTextColor(Color.GRAY);
                            } else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }

                        @Override
                        public boolean isEnabled(int position) {
                            if (position == 0) {
                                // Disable the first item from Spinner
                                // First item will be use for hint
                                return false;
                            } else {
                                return true;
                            }
                        }
                    };
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

            spSecretQuestion.setAdapter(adapter);
            spSecretQuestion.setOnItemSelectedListener(this);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initCityHandle(String data) {
        try {
            JSONArray jsonArrayData = new JSONArray(data);
            ArrayList<City> cityArrayList = new ArrayList<>();

            cityArrayList.add(new City("Tỉnh thành", 0));
            for (int i = 0; i < jsonArrayData.length(); i++) {
                cityArrayList.add(new City(jsonArrayData.optJSONObject(i).optString("locationName"), jsonArrayData.optJSONObject(i).optInt("locationID")));
            }


            ArrayAdapter<City> adapter =
                    new ArrayAdapter<City>(getContext(), R.layout.spinner_item, cityArrayList) {
                        @Override
                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if (position == 0) {
                                // Set the hint text color gray
                                tv.setTextColor(Color.GRAY);
                            } else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }

                        @Override
                        public boolean isEnabled(int position) {
                            if (position == 0) {
                                // Disable the first item from Spinner
                                // First item will be use for hint

                                return false;
                            } else {

                                return true;
                            }
                        }
                    };
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

            spCity.setAdapter(adapter);
            spCity.setOnItemSelectedListener(this);

            for (int i = 0; i < spCity.getCount(); i++) {
                City city = (City) spCity.getItemAtPosition(i);
                if (city.getLocationID() == locationId) {
                    spCity.setSelection(i);
                    break;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initDistrictHandle(String data) {
        try {
            JSONArray jsonArrayData = new JSONArray(data);
            ArrayList<District> question = new ArrayList<>();

            question.add(new District("Quận huyện", 0));
            for (int i = 0; i < jsonArrayData.length(); i++) {
                question.add(new District(jsonArrayData.optJSONObject(i).optString(ConstParam.DISTRICT_NAME), jsonArrayData.optJSONObject(i).optInt("districtID")));

            }


            ArrayAdapter<District> adapter =
                    new ArrayAdapter<District>(getContext(), R.layout.spinner_item, question) {
                        @Override
                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if (position == 0) {
                                // Set the hint text color gray
                                tv.setTextColor(Color.GRAY);
                            } else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }

                        @Override
                        public boolean isEnabled(int position) {
                            if (position == 0) {
                                // Disable the first item from Spinner
                                // First item will be use for hint
                                return false;
                            } else {
                                return true;
                            }
                        }
                    };
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

            spDistrict.setAdapter(adapter);
            spDistrict.setOnItemSelectedListener(this);

            for (int i = 0; i < spDistrict.getCount(); i++) {
                District district = (District) spDistrict.getItemAtPosition(i);
                if (district.getDistrictId() == districtId) {
                    spDistrict.setSelection(i);
                    break;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initWardHandle(String data) {
        try {
            JSONArray jsonArrayData = new JSONArray(data);
            ArrayList<Ward> wardsArray = new ArrayList<>();

            wardsArray.add(new Ward("Phường, xã", 0));
            for (int i = 0; i < jsonArrayData.length(); i++) {
                wardsArray.add(new Ward(jsonArrayData.optJSONObject(i).optString(ConstParam.WARD_NAME), jsonArrayData.optJSONObject(i).optInt("wardID")));

            }


            ArrayAdapter<Ward> adapter =
                    new ArrayAdapter<Ward>(getContext(), R.layout.spinner_item, wardsArray) {
                        @Override
                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if (position == 0) {
                                // Set the hint text color gray
                                tv.setTextColor(Color.GRAY);
                            } else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }

                        @Override
                        public boolean isEnabled(int position) {
                            if (position == 0) {
                                // Disable the first item from Spinner
                                // First item will be use for hint
                                return false;
                            } else {
                                return true;
                            }
                        }
                    };
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

            spCommune.setAdapter(adapter);
            spCommune.setOnItemSelectedListener(this);

            for (int i = 0; i < spCommune.getCount(); i++) {
                Ward ward = (Ward) spCommune.getItemAtPosition(i);
                if (ward.getWardId() == wardId) {
                    spCommune.setSelection(i);
                    break;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void selectSpinnerValue(Spinner spinner, int objectId) {
        for (int i = 0; i < spCity.getCount(); i++) {
            City city = (City) spinner.getItemAtPosition(i);
            if (city.getLocationID() == objectId) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    private void initGetAccountDetail() {
        Map<String, String> mapGetDetail = new HashMap<>();
        mapGetDetail.put(ConstParam.PARAM_REQUEST_USERNAME, VtcString.USER_NAME);
        mapGetDetail.put(ConstParam.PARAM_REQUEST_ACCESS_TOKEN, VtcString.ACCESS_TOKEN);

        if (vtcConnection != null) {
            vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_GET_ACCOUNT_DETAIL, RequestListener.API_CONNECTION_GET_DETAIL + VtcHttpConnection.urlEncodeUTF8(mapGetDetail), false, false);
        }
    }

    /**
     * initUpdateHandle method handle update profile or insert profile action
     */
    private void initUpdateHandle() {

        City city = (City) spCity.getSelectedItem();
        District district = (District) spDistrict.getSelectedItem();
        Ward ward = (Ward) spCommune.getSelectedItem();

        Map<String, String> mapProfile = new HashMap<>();
        mapProfile.put(ConstParam.PARAM_REQUEST_USERNAME, VtcString.USER_NAME);
        mapProfile.put(ConstParam.PARAM_REQUEST_ACCESS_TOKEN, VtcString.ACCESS_TOKEN);
        mapProfile.put(ConstParam.PARAM_API_ACCOUNT_ID, VtcString.ACCOUNT_ID);
        mapProfile.put(ConstParam.PARAM_API_ADDRESS, String.valueOf(editAddress.getText()));
        mapProfile.put(ConstParam.PARAM_API_BIRTHDAY, String.valueOf(AppBase.dateTimeToLong(String.valueOf(tvDob.getText()))));
        mapProfile.put(ConstParam.PARAM_API_CLIENT_IP, VtcString.IP_ADDRESS);
        mapProfile.put(ConstParam.PARAM_API_DISTRICT_ID, String.valueOf(district.getDistrictId()));
        mapProfile.put(ConstParam.PARAM_API_FULL_NAME, String.valueOf(editAccountName.getText()));
        mapProfile.put(ConstParam.PARAM_API_GENDER, String.valueOf(spSex.getSelectedItemId() - 1));
        mapProfile.put(ConstParam.PARAM_API_LOCATION_ID, String.valueOf(city.getLocationID()));
        mapProfile.put(ConstParam.PARAM_API_MOBILE, String.valueOf(editPhoneNo.getText()));
        mapProfile.put(ConstParam.PARAM_API_WARD_ID, String.valueOf(ward.getWardId()));

        AppBase.showLog("0000000000:" + VtcHttpConnection.urlEncodeUTF8(mapProfile));
        if (isUpdate == 0) {

            SecretQuestion secretQuestion = (SecretQuestion) spSecretQuestion.getSelectedItem();
            mapProfile.put(ConstParam.PARAM_API_QUESTION_ID, String.valueOf(secretQuestion.getQuestionId()));
            mapProfile.put(ConstParam.PARAM_API_ANSWER, String.valueOf(editSecretQuestion.getText()));
            mapProfile.put(ConstParam.PARAM_API_EMAIL, String.valueOf(editEmail.getText()));
            mapProfile.put(ConstParam.PARAM_API_PASSPORT, String.valueOf(editPassport.getText()));

            if (Const.PHONE_NUMBER_IS_ACTIVED == 1) {
                mapProfile.put(ConstParam.PARAM_API_SECURE_CODE, String.valueOf(editOtpCode.getText()));
            } else {
                mapProfile.put(ConstParam.PARAM_API_SECURE_CODE, "");
            }
            vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_INSERT_PROFILE, RequestListener.API_CONNECTION_INSERT_PROFILE + VtcHttpConnection.urlEncodeUTF8(mapProfile), true);
        } else {
            mapProfile.put(ConstParam.PARAM_API_SIGN, "");
            vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_UPDATE_PROFILE, RequestListener.API_CONNECTION_UPDATE_PROFILE + VtcHttpConnection.urlEncodeUTF8(mapProfile), true);
        }

    }

    private void initInformationHandle(String data) {
        try {
            JSONObject jsonDetail = new JSONObject(data);
            if (jsonDetail != null) {
                String vcoin = AppBase.splitByLength(jsonDetail.optString(ConstParam.VCOIN_PAYMENT), 3);
                tvSurplus.setText(vcoin);
                tvVcoinAvailable.setText(vcoin);

                tvAccountNo.setText(String.valueOf(jsonDetail.optLong(ConstParam.ACCOUNT_ID)));
                tvRegisterDate.setText(AppBase.longToDateTime(jsonDetail.optLong(ConstParam.JOINED_TIME)));
                editAccountName.setText(AppBase.initCheckString(jsonDetail, ConstParam.FULL_NAME));
                editPhoneNo.setText(AppBase.initCheckString(jsonDetail, ConstParam.MOBILE));
                editAddress.setText(AppBase.initCheckString(jsonDetail, ConstParam.ADDRESS));

                long birthdayLong = jsonDetail.optLong(ConstParam.BIRTHDAY);
                if (birthdayLong > 0) {
                    tvDob.setText(AppBase.longToDateTime(birthdayLong));
                }


                locationId = jsonDetail.optInt(ConstParam.LOCATION_ID);
                districtId = jsonDetail.optInt(ConstParam.DISTRICT_ID);
                wardId = jsonDetail.optInt(ConstParam.WARD_ID);


                int gender = jsonDetail.optInt(ConstParam.GENDER);
                if (gender == 0) { // 0 is Nam, 1 is Nu
                    spSex.setSelection(1);
                } else if (gender == 1) {
                    spSex.setSelection(2);
                }

                String accountEmail = AppBase.initCheckString(jsonDetail, ConstParam.ACCOUNT_EMAIL);
                int phoneVerify = jsonDetail.optInt(ConstParam.VERIFY_TYPE);
                if (accountEmail.equals("") || accountEmail.equals("null")) {
                    Const.EMAIL_IS_ACTIVED = 0;
                    isUpdate = 0;
                    if (Const.ACCOUNT_INFOR_EMAIL != Const.HIDE_VALUE) {
                        loutEmailActived.setVisibility(View.GONE);
                    }

                    loutEmailEdit.setVisibility(View.VISIBLE);
                    loutSecretQuestion.setVisibility(View.VISIBLE);
                    loutPassportEdit.setVisibility(View.VISIBLE);

                    if (phoneVerify == 3 || phoneVerify == 4) {
                        tvOtpGuide.setVisibility(View.VISIBLE);
                        loutOtp.setVisibility(View.VISIBLE);
                        tvOtpGuide.setText(Html.fromHtml(VtcString.GUIDE_OTP));
                        if (Const.ACCOUNT_INFOR_PHONE_NO != Const.HIDE_VALUE) {
                            loutPhoneActive.setVisibility(View.VISIBLE);
                        }

                        tvPhoneNo.setText(jsonDetail.optString(ConstParam.MOBILE));

                        loutPhoneEdit.setVisibility(View.GONE);
                        Const.PHONE_NUMBER_IS_ACTIVED = 1;
                    } else {
                        if (Const.ACCOUNT_VERIFY_PHONE_NO != Const.HIDE_VALUE) {
                            btnPhoneVerify.setVisibility(View.GONE);
                        }
                        loutPhoneEdit.setVisibility(View.VISIBLE);

                        if (Const.ACCOUNT_INFOR_PHONE_NO != Const.HIDE_VALUE) {
                            loutPhoneActive.setVisibility(View.GONE);
                        }

                        Const.PHONE_NUMBER_IS_ACTIVED = 0;
                    }
                } else {
                    Const.EMAIL_IS_ACTIVED = 1;
                    isUpdate = 1;
                    loutSecretQuestion.setVisibility(View.GONE);
                    if (Const.ACCOUNT_INFOR_EMAIL != Const.HIDE_VALUE) {
                        loutEmailActived.setVisibility(View.VISIBLE);
                    }

                    if (Const.ACCOUNT_VERIFY_PHONE_NO != Const.HIDE_VALUE) {
                        btnPhoneVerify.setVisibility(View.VISIBLE);
                    }
                    tvEmail.setText(accountEmail);
                    loutPassportEdit.setVisibility(View.GONE);

                    loutEmailEdit.setVisibility(View.GONE);
                    if (phoneVerify == 3 || phoneVerify == 4) {
                        loutPhoneActive.setVisibility(View.VISIBLE);
                        loutPhoneEdit.setVisibility(View.GONE);

                        tvPhoneNo.setText(jsonDetail.optString(ConstParam.MOBILE));

                        Const.PHONE_NUMBER_IS_ACTIVED = 1;

                        if (Const.ACCOUNT_INFOR_PHONE_NO == Const.HIDE_VALUE) {
                            loutPhoneActive.setVisibility(View.GONE);
                        }
                    } else {
                        loutPhoneActive.setVisibility(View.GONE);
                        loutPhoneEdit.setVisibility(View.VISIBLE);

                        Const.PHONE_NUMBER_IS_ACTIVED = 0;
                    }
                }

                initGetSecretQuestion();
                initGetCity();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean initCheckInputEmpty() {
        if (String.valueOf(editAccountName.getText()).equals("")) {
            tvError.setText(getResources().getString(R.string.error_UserName_Null));
            return false;
        }

        if (Const.EMAIL_IS_ACTIVED == 0) {
            if (String.valueOf(editPassport.getText()).equals("")) {
                tvError.setText(getResources().getString(R.string.error_Passport_Null));
                return false;
            }
        }

        if (loutEmailEdit.getVisibility() == View.VISIBLE) {
            if (String.valueOf(editEmail.getText()).equals("")) {
                tvError.setText(getResources().getString(R.string.error_Email_Null));
                return false;
            }
        }

        if (loutPhoneEdit.getVisibility() == View.VISIBLE) {
            if (String.valueOf(editPhoneNo.getText()).equals("")) {
                tvError.setText(getResources().getString(R.string.error_Phone_Null));
                return false;
            }
        }

        if (String.valueOf(tvDob.getText()).equals("--/--/--")) {
            tvError.setText(getResources().getString(R.string.error_Birthday_Null));
            return false;
        }

        if (spCity.getSelectedItemId() == 0) {
            tvError.setText(getResources().getString(R.string.error_City_Null));
            return false;
        }

        if (spDistrict.getSelectedItemId() == 0) {
            tvError.setText(getResources().getString(R.string.error_District_Null));
            return false;
        }

        if (spCommune.getSelectedItemId() == 0) {
            tvError.setText(getResources().getString(R.string.error_Ward_Null));
            return false;
        }

        if (String.valueOf(editAddress.getText()).equals("")) {
            tvError.setText(getResources().getString(R.string.error_Address_Null));
            return false;
        }

        if (loutSecretQuestion.getVisibility() == View.VISIBLE) {
            if (spSecretQuestion.getSelectedItemId() == 0) {
                tvError.setText(getResources().getString(R.string.error_Secret_Question_Null));
                return false;
            }

            if (String.valueOf(editSecretQuestion.getText()).equals("")) {
                tvError.setText(getResources().getString(R.string.error_Answer_Null));
                return false;
            }
        }

        return true;
    }


    @Override
    public void onClick(View v) {
        AppBase.initHideKeyboard(v, getContext());
        switch (v.getId()) {
            case R.id.img_Back:
                AppBase.initBack();
                break;
            case R.id.lout_Profile_Information:
                if (loutAccountDetail.getVisibility() == View.GONE) {
                    loutAccountDetail.setVisibility(View.VISIBLE);
                    imgArrowAccount.setRotation(0);
                } else {
                    loutAccountDetail.setVisibility(View.GONE);
                    imgArrowAccount.setRotation(270);
                }
                break;
            case R.id.lout_Profile_Onwer_Information:
                if (loutAccountOwnerDetail.getVisibility() == View.GONE) {
                    loutAccountOwnerDetail.setVisibility(View.VISIBLE);
                    imgArrowOwnerAccount.setRotation(0);
                } else {
                    loutAccountOwnerDetail.setVisibility(View.GONE);
                    imgArrowOwnerAccount.setRotation(270);
                }
                break;
            case R.id.tv_Dob:
                dobPickerDialog.show();
                break;
            case R.id.btn_Email_Edit:
                bundle.putInt(ConstParam.BUNDLE_TYPE_KEY, Const.FUNC_EMAIL_EDIT);
                AppBase.callNewFragment(Const.UI_ACCOUNT_VERIFY, bundle, true);
                break;
            case R.id.btn_Phone_Edit:
                bundle.putInt(ConstParam.BUNDLE_TYPE_KEY, Const.FUNC_PHONE_EDIT);
                AppBase.callNewFragment(Const.UI_ACCOUNT_VERIFY, bundle, true);
                break;
            case R.id.btn_Phone_Remove:
                bundle.putInt(ConstParam.BUNDLE_TYPE_KEY, Const.FUNC_PHONE_REMOVE);
                AppBase.callNewFragment(Const.UI_ACCOUNT_VERIFY, bundle, true);
                break;
            case R.id.btn_Update:
                tvError.setText("");

                if (initCheckInputEmpty()) {
                    initUpdateHandle();
                }

                break;
            case R.id.btn_Email_Verify:

                break;
            case R.id.btn_Phone_Verify:
                bundle.putInt(ConstParam.BUNDLE_TYPE_KEY, Const.FUNC_PHONE_VERIFY);
                AppBase.callNewFragment(Const.UI_ACCOUNT_VERIFY, bundle, true);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        switch (spinner.getId()) {
            case R.id.sp_Secret_Question:
                break;
            case R.id.sp_City:
                if (spCity.getSelectedItemId() == 0) {
                    spDistrict.setEnabled(false);
                } else {
                    spDistrict.setEnabled(true);
                    City city = (City) spCity.getSelectedItem();
                    initGetDistrict(city.getLocationID());
                    initWardHandle("[]");
                }

                break;

            case R.id.sp_District:
                if (spDistrict.getSelectedItemId() == 0) {
                    spCommune.setEnabled(false);
                } else {
                    spCommune.setEnabled(true);
                    District district = (District) spDistrict.getSelectedItem();
                    initGetWard(district.getDistrictId());
                }

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPostExecuteError(TypeActionConnection keyType, String errorcode, String msg, String info, String reponseFullData) {
        switch (keyType) {
            case TYPE_ACTION_UPDATE_PROFILE:
                tvError.setText(msg);
                break;
            case TYPE_ACTION_INSERT_PROFILE:
                tvError.setText(msg);
                break;
        }
    }

    @Override
    public void onPostExecuteSuccess(TypeActionConnection keyType, String message, String info, String reponseFullData) {
        switch (keyType) {
            case TYPE_ACTION_GET_SECRET_QUESTION:
                if ((!info.equals("")) && (!info.equals("null"))) {
                    initQuestionHandle(info);
                }
                break;
            case TYPE_ACTION_GET_CITY:
                if ((!info.equals("")) && (!info.equals("null"))) {
                    initCityHandle(info);
                }
                break;
            case TYPE_ACTION_GET_DISTRICT:
                if ((!info.equals("")) && (!info.equals("null"))) {
                    initDistrictHandle(info);
                }
                break;
            case TYPE_ACTION_GET_WARD:
                if ((!info.equals("")) && (!info.equals("null"))) {
                    initWardHandle(info);
                }
                break;
            case TYPE_ACTION_GET_ACCOUNT_DETAIL:
                if ((!info.equals("")) && (!info.equals("null"))) {
                    initInformationHandle(info);
                }
                break;
            case TYPE_ACTION_UPDATE_PROFILE:
                AppBase.initShowMessageAlert(getContext(), Const.ALERT_DONE, VtcString.LABEL_BTN_DONE, message);
                break;
            case TYPE_ACTION_INSERT_PROFILE:
                tvError.setText(message);
                AppBase.initShowMessageAlert(getContext(), Const.ALERT_DONE, VtcString.LABEL_BTN_DONE, message);
                break;

        }
    }
}
