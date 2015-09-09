package all_action.iblaudas.json_url;

/**
 * Created by sunry on 8/19/2015.
 */
public class SchemaTable {
    public  static final String TABLE_CAR = "TBL_CAR";
    //*************************LIST VIEW CAR ***********************************
   final String StrOrder = " ORDER BY  CASE CAR_STATUS  WHEN  'sale' THEN 1 WHEN 'shipok' THEN 2 WHEN 'reserved' THEN 3  WHEN 'order' THEN 4 WHEN 'soldout' THEN 5 WHEN 'releaseeok' THEN 6 END";
    public  static final String SqlQueryDataListView="SELECT *  FROM TBL_CAR WHERE CAR_STATUS!='releaseok' ORDER BY  CASE CAR_STATUS  WHEN  'sale' THEN 1 WHEN 'shipok' THEN 2 WHEN 'reserved' THEN 3  WHEN 'order' THEN 4 WHEN 'soldout' THEN 5 WHEN 'releaseeok' THEN 6 END";
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
    public static final String CAR_CHASSIS_NO = "CAR_CHASSIS_NO";
    //*******************Country Table**********************************
    public static final String QueryLocation="SELECT DISTINCT CAR_COUNTRY FROM TBL_CAR WHERE CAR_COUNTRY=\"KOREA\" OR CAR_COUNTRY=\"JAPAN\" AND CAR_COUNTRY!=\"\"";

    //*******************Make Table**********************************
    public static final String QueryMake="SELECT DISTINCT CAR_MAKE  FROM TBL_CAR";
    public static final String QueryModel="SELECT DISTINCT CAR_MODEL  FROM TBL_CAR  WHERE CAR_MAKE=\"++\" GROUP BY CAR_MODEL";

}
