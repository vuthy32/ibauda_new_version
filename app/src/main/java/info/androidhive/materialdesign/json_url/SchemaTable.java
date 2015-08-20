package info.androidhive.materialdesign.json_url;

/**
 * Created by sunry on 8/19/2015.
 */
public class SchemaTable {
    //*************************LIST VIEW CAR ***********************************
    public  static final String SqlQueryDataListView
            = "select zc.*,zp.ZPHOTO_URL,zp.ZPHOTO_ID " +
            "FROM ZCAR zc INNER JOIN ZPHOTOCAR  zp ON zc.ZCAR_ID=zp.ZCAR_ID GROUP BY zc.ZCAR_ID";
    public static final String IMAGE_URL = "ZPHOTO_URL";
    public static final String CAR_NO = "ZCAR_ID";

    public static final String CAR_MAKE = "ZMAKE";

    public static final String CAR_MODEL = "ZMODEL";
    public static final String CAR_YEAR = "ZYEAR";

    public static final String CAR_COUTRY = "ZCITY";
    public static final String CAR_CITY = "ZCITY";

    public static final String CAR_INDEX = "ZCAR_ID";

    public static final String CAR_FOB = "ZPRICE";
}
