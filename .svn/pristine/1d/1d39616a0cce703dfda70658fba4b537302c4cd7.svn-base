package vtc.game.app.vcoin.vtcpay.connection;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

import org.json.JSONObject;

import vtc.game.app.vcoin.vtcpay.R;
import vtc.game.app.vcoin.vtcpay.common.AppBase;
import vtc.game.app.vcoin.vtcpay.common.ParserJson;
import vtc.game.app.vcoin.vtcpay.common.VtcString;
import vtc.game.app.vcoin.vtcpay.enums.TypeActionConnection;
import vtc.game.app.vcoin.vtcpay.enums.TypeErrorConnection;
import vtc.game.app.vcoin.vtcpay.interfaces.RequestListener;


/**
 * Created by Thuy Chi on 7/7/16.
 */
public class VtcConnection extends VtcHttpConnection {

    private ProgressDialog progress;

    public VtcConnection(Context context, RequestListener requestConnection) {
        super(context, requestConnection);
    }

    /**
     * <d>Call when request Api server</d>
     * <d>show dialog process</d>
     */
    private void initShowDialogProcess() {


        if (getContext() == null) {
            return;
        }
        initCloseDialogProcess();
        progress = ProgressDialog.show(getContext(),
                getContext().getResources().getString(R.string.title_Dialog_Message),
                getContext().getResources().getString(R.string.title_Dialog_Process_Confirm), true);
    }

    /**
     * <d>Call when request Api server</d>
     * <d>Dismiss dialog process</d>
     */
    private void initCloseDialogProcess() {
        if (progress != null) {
            progress.dismiss();
            progress = null;
        }
    }

    /**
     * <d>Call when wan connect server request API</d>
     *
     * @param actionConnection Type connection you wan request
     * @param sAPi             link Api connect to server
     * @param isPost           setting method post
     */
    public void onExcuteProcess(TypeActionConnection actionConnection, String sAPi, boolean isPost) {

        onExcuteProcess(actionConnection, sAPi, null, isPost, true, "");
    }

    public void onExcuteProcess(TypeActionConnection actionConnection, String sAPi, boolean isPost, boolean isShowProcess) {

        onExcuteProcess(actionConnection, sAPi, null, isPost, isShowProcess, "");
    }


    public void onExcuteProcess(TypeActionConnection actionConnection, String sAPi, boolean isPost, String authorization) {

        onExcuteProcess(actionConnection, sAPi, null, isPost, true, authorization);
    }


    /**
     * <d>Call when wan connect server request API</d>
     *
     * @param actionConnection Type connection you wan request
     * @param sAPi             link Api connect to server
     */
    public void onExcuteProcess(TypeActionConnection actionConnection, String sAPi) {

        onExcuteProcess(actionConnection, sAPi, null, true, true, "");
    }

    /**
     * <d>Call when wan connect server request API</d>
     *
     * @param actionConnection Type connection you wan request
     * @param parameter        out put
     * @param sAPi             link Api connect to server
     */
    public void onExcuteProcess(TypeActionConnection actionConnection, JSONObject parameter, String sAPi) {

        onExcuteProcess(actionConnection, sAPi, parameter, true, true, "");
    }

    public void onExcuteProcess(TypeActionConnection actionConnection, String sAPi, JSONObject parameter, boolean isPost, String authorization) {

        onExcuteProcess(actionConnection, sAPi, parameter, isPost, false, "");
    }

    /**
     * <d>Call when wan connect server request API</d>
     *
     * @param actionConnection Type connection you wan request
     * @param parameter        out put
     * @param sAPi             link Api connect to server
     * @param isPost           setting method post
     */
    public void onExcuteProcess(TypeActionConnection actionConnection, JSONObject parameter, String sAPi, boolean isPost) {

        onExcuteProcess(actionConnection, sAPi, parameter, isPost, true, "");
    }

    /**
     * <d>Call when wan connect server request API</d>
     *
     * @param actionConnection Type connection you wan request
     * @param sAPi             link Api connect to server
     * @param param            out put
     * @param isShowProcess    = true then show dialog process,
     *                         = false then dismiss dialog process.
     */
    public void onExcuteProcess(TypeActionConnection actionConnection, String sAPi, JSONObject param, boolean isPost, boolean isShowProcess, String authorization) {

        VtcModelConnect vtcModelConnect = new VtcModelConnect();
        vtcModelConnect.setActionConnection(actionConnection);
        vtcModelConnect.setAPI(sAPi);
        vtcModelConnect.setParameter(param);
        vtcModelConnect.setPost(isPost);
        vtcModelConnect.setShowProcess(isShowProcess);
        vtcModelConnect.setAuthorization(authorization);


        onExcute(vtcModelConnect);
    }

    private void onExcute(VtcModelConnect vtcModelConnect) {
        ProcessConnection execute = new ProcessConnection(vtcModelConnect);
        if (execute.getStatus() == AsyncTask.Status.RUNNING) {

            setPoolQueue(vtcModelConnect.getAPI(), vtcModelConnect);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

                execute.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                execute.execute();
            }
        }
    }

    /**
     * <d>Call when wan connect server request API</d>
     * <p>
     * <d>Thread process connect and receiver data from server</d>
     */
    private class ProcessConnection extends AsyncTask<String, String, String> {

        private VtcModelConnect vtcModelConnect;

        public ProcessConnection(VtcModelConnect vtcModelConnect) {
            this.vtcModelConnect = vtcModelConnect;
        }

        private boolean isCheckNull() {
            if (vtcModelConnect == null) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            AppBase.showLog("Log : " + "-------------------------------------------------");
            if (isCheckNull() && vtcModelConnect.isShowProcess()) {
                initShowDialogProcess();
            }

        }

        @Override
        protected String doInBackground(String... params) {
            String sData = "";
            AppBase.showLog("Log : " + "------1-------------------------------------------");

            if (AppBase.isNetworkConnected(getContext())) {
                if (isCheckNull()) {
                    sData = initRequestConnection(vtcModelConnect.getAPI(), String.valueOf(vtcModelConnect.getParameter()), vtcModelConnect.isPost(), vtcModelConnect.getAuthorization());
                }
            } else {
                // No Internet Connection
                setErrorConnection(TypeErrorConnection.TYPE_CONNECTION_NO_INTERNET);
            }

            return sData;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            AppBase.showLog("Log : " + "-------------------2------------------------------");
            AppBase.showLog("String Response Vgift:" + "-----: " + s);


            if (getRequestConnection() != null) {
                AppBase.showLog("----------TEST 1---------");
                if (getErrorConnection() == TypeErrorConnection.TYPE_CONNECTION && !s.equals("") && !s.equals(null)) {
                    AppBase.showLog("----------TEST 2---------: " + ParserJson.getResponse(s));
                    String msg = ParserJson.getStatusMsg(s);
                    String error = ParserJson.getStatusErrorCode(s);
                    String info = ParserJson.getResponse(s);
                    if ((ParserJson.getResponse(s) == "null")) {
                        if (ParserJson.getStatusSuccess(s)) {
                            AppBase.showLog("----------TEST 3---------fullresponse:" + s);
                            getRequestConnection().onPostExecuteSuccess(vtcModelConnect.getActionConnection(), msg, "", s);
                        } else {
                            setErrorConnection(TypeErrorConnection.TYPE_CONNECTION);
                            initError(vtcModelConnect.getActionConnection(), getErrorConnection(), error, msg, info, s);
                        }

                    } else if ((vtcModelConnect.getActionConnection() == TypeActionConnection.TYPE_ACTION_GET_GAME_LIST_NEWS)) {
                        getRequestConnection().onPostExecuteSuccess(vtcModelConnect.getActionConnection(), msg, "", s);
                    } else {
                        if (ParserJson.getStatusSuccess(s)) {

//                        Common.getPreferenceUtil(getContext()).setValueString(PreferenceUtil.AUTH_TOKEN, ParserJson.getAuthToken(s));
                            AppBase.showLog("Info" + "-----" + info);
                            getRequestConnection().onPostExecuteSuccess(vtcModelConnect.getActionConnection(), msg, info, s);
                        } else {

                            setErrorConnection(TypeErrorConnection.TYPE_CONNECTION);
                            initError(vtcModelConnect.getActionConnection(), getErrorConnection(), error, msg, info, s);

                            //getRequestConnection().onPostExecuteError(getErrorConnection(), ParserJson.getStatusMsg(s));
                        }
                    }

                } else {
                    initError(vtcModelConnect.getActionConnection(), getErrorConnection(), "", "", "", s);
                    //getRequestConnection().onPostExecuteError(getErrorConnection(), "");
                }
            }
            //initCloseDialogProcess();
            if (vtcModelConnect.isShowProcess()) {
                initCloseDialogProcess();
            }
            if (getApiQueueSize() > 0) {

                String sApi = getApiQueue();

                onExcute(getVtcModelConnect(sApi));
            }
        }
    }

    private void initError(TypeActionConnection keyTypeConect, TypeErrorConnection keyType, String errorcode, String msg, String info, String responseFullData) {
        switch (keyType) {

            case TYPE_CONNECTION_NOT_CONNECT_SERVER:
                /*Common.initShowDialogOption(getContext(), TypeShowDialog.TYPE_SHOW_MESSAGE_INFO, "",
                        VTCString.confirm_server_not_found);*/
                Toast.makeText(getContext(), VtcString.CONFIRM_SERVER_NOT_FOUND, Toast.LENGTH_LONG).show();
                break;
            case TYPE_CONNECTION_NO_INTERNET:
                /*Common.initShowDialogOption(getContext(), TypeShowDialog.TYPE_SHOW_MESSAGE_INFO, "",
                        VTCString.confirm_no_internet_connect);*/
                Toast.makeText(getContext(), VtcString.CONFIRM_NO_INTERNET_CONNECTION, Toast.LENGTH_LONG).show();
                break;
            case TYPE_CONNECTION_TIMEOUT:
//                Common.initShowDialogOption(getContext(), TypeShowDialog.TYPE_SHOW_MESSAGE_INFO, "",
//                        VTCString.confirm_server_timeout);
                Toast.makeText(getContext(), VtcString.CONFIRM_SERVER_TIMEOUT, Toast.LENGTH_LONG).show();
                break;
            case TYPE_CONNECTION_ERROR:
//                Common.initShowDialogOption(getContext(), TypeShowDialog.TYPE_SHOW_MESSAGE_INFO, "",
//                       VTCString.confirm_server_error_connect);
                Toast.makeText(getContext(), VtcString.CONFIRM_SERVER_ERROR_CONNECTION, Toast.LENGTH_LONG).show();
                break;
            case TYPE_CONNECTION_ERROR_FROM_SERVER:
//                if (!msg.equals("")) {
//                    initShowDialogOption(getmActivity(), TypeShowDialog.TYPE_SHOW_MESSAGE_INFO, "", msg);
//                }
                break;
            default:
                getRequestConnection().onPostExecuteError(keyTypeConect, errorcode, msg, info, responseFullData);
                break;
        }

    }
}
