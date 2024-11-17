package org.example.fx_forint.models;

import javafx.scene.control.Alert;

import java.sql.SQLException;

import static org.example.fx_forint.helper.Utils.showMessage;
import static org.example.fx_forint.helper.databaseHelper.executeSql;

public class Params {

    private String param_name;
    private String param_value;

    public Params(String param_name, String param_value) {
        this.param_name = param_name;
        this.param_value = param_value;
    }

    public String getParam_name() {
        return param_name;
    }

    public void setParam_name(String param_name) {
        this.param_name = param_name;
    }

    public String getParam_value() {
        return param_value;
    }

    public void setParam_value(String param_value) {
        this.param_value = param_value;
    }

    public static void saveParam(Params param) {
        String sql="Update params set param_value= '"+param.getParam_value()+"' where param_name='"+param.getParam_name()+"'";
        executeSql(sql);

        sql = "Insert into Params (param_name, param_value) select '"+param.getParam_name()+"', '"+param.getParam_value()+"'"+
                " where not exists (select 1 from params where param_name = '"+param.getParam_name()+"')";
        executeSql(sql);
    }
}
