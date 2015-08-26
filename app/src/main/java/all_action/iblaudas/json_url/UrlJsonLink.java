package all_action.iblaudas.json_url;

/**
 * Created by sunry on 8/12/2015.
 */
public class UrlJsonLink {
    public static final String DATABASE_NAME = "iblauda.sqlite";
    public static final String DomainWebsite = "http://iblauda.com/";

    public static final String URL_HOME_MOST_VIEW = DomainWebsite+"?c=json&m=getCarListpageWithKey";

    // **** URL get data from server detail data
    public static final String URL_DETAIL_LINKE = DomainWebsite+"?c=json&m=getCarDetails&idx=";

    // **** URL get data from server detail data galler
    public static final String URL_PHOTO_GALLERY = DomainWebsite+"?c=json&m=getCarImages&idx=";

    // **** URL get data from server country
    public static final String URL_COUNTRY = DomainWebsite+"?c=json&m=getCarCountryList";

    // **** URL get data from server search data
    public static final String URL_SEARCH = DomainWebsite + "?c=json&m=getCarSearchListpageWithKey&keyword=";


    // **** URL get data from server data make
    public static final String URL_MAKE = DomainWebsite + "?c=json&m=getMake";

    // **** URL get data from server data model
    public static final String URL_MODEL = DomainWebsite + "?c=json&m=getModel&car_make=";

    //***schema share data


    public static final String ShareCarManafacture = "ShareCarManafacture";

    public static final String ShareCarYear = "ShareCarYear";

    public static final String ShareCarYearTransmission = "ShareCarYearTransmission";

    public static final String ShareCountry = "ShareCountry";
    public static final String ShareCarCity = "ShareCarCity";
    public static final String ShareCaStockNo = "ShareCaStockNo";
    public static final String ShareChasnisNo= "ShareChasnisNo";
    public static final String ShareIconStatus = "ShareIconStatus";
    public static final String ShareCarMake = "ShareCarMake";
    public static final String ShareCarModel = "ShareCarModel";
    public static final String ShareCarGrand = "ShareCarGrand";
    public static final String ShareCarYearStart = "ShareCarYearStart";
    public static final String ShareCarFirstRag = "ShareCarFirstRag";
    public static final String ShareCarColor = "ShareCarColor";
    public static final String ShareCarCC = "ShareCarCC";
    public static final String ShareCarMilleag = "ShareCarMilleag";
    public static final String ShareCarFuel = "ShareCarFuel";

    public static final String ShareCarSeat = "ShareCarSeat";
    public static final String ShareCarBodyType = "ShareCarBodyType";
    public static final String ShareCarDriveType = "ShareCarDriveType";
    public static final String ShareCarCost = "ShareCarCost";
    public static final String ShareCarFOB= "ShareCarFOB";
    public static final String ShareCarBuyingPrin= "ShareCarBuyingPrin";
    public static final String ShareCarBuyingCurr= "ShareCarBuyingCurr";



}
