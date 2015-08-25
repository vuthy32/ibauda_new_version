package info.androidhive.materialdesign.json_url;

/**
 * Created by sunry on 8/19/2015.
 */
public class SchemaTable {
    public  static final String TABLE_CAR = "TBL_CAR";
    //*************************LIST VIEW CAR ***********************************

    public  static final String SqlQueryDataListView="SELECT *  FROM TBL_CAR WHERE CAR_STATUS!='releaseok' ORDER BY  CASE CAR_STATUS  WHEN  'sale' THEN 1 WHEN 'reserved' THEN 2   WHEN 'order' THEN 3 WHEN 'soldout' THEN 4 WHEN 'releaseeok' THEN 5 END";
    public  static final String SqlQueryDataDetail
           = "SELECT * FROM TBL_CAR WHERE CAR_ID=";
    public static final String CAR_INDEX = "CAR_ID";
    public static final String IMAGE_URL_THUMB = "CAR_THUMB_URL";
    public static final String CAR_NO = "CAR_STOCK_NO";
    public static final String CAR_MAKE = "CAR_MAKE";
    public static final String CAR_MODEL = "CAR_MODEL";
    public static final String CAR_YEAR = "CAR_YEAR";
    public static final String CAR_COUTRY = "CAR_COUNTRY";
    public static final String CAR_CITY = "CAR_CITY";
    public static final String CAR_FOB = "CAR_FOB_COST";
    public static final String CAR_FOB_CURRENCY = "CAR_FOB_CURRENT";

    public static final String CAR_CREATE_STATUS = "CAR_CREATE_STATUS";
    public static final String CAR_STATUS = "CAR_STATUS";

}
