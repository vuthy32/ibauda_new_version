package info.androidhive.materialdesign.JsonModel;

/**
 * Created by sunry on 8/20/2015.
 */
public class ModelCarGet {

    public ModelCarGet(){}
private String car_id,car_mak,car_model,car_year,carStatYear,
        carTransmission,carCountry,carCity,carStockNo,carChassNo,
        carStatus,carGrade,firstRegisterMonth,carMileage,carFuel,
        carColor,carSeat,carBodyType,carDriverType,carFobCost,carFobCurrent,
        carCreateStatus,carCreateDate,carCCStr;
    public ModelCarGet(
            String _car_id,
            String _car_make,        String _car_model,
            String _car_year,        String _carStatYear,
            String _carTransmission, String _carCountry,
            String _carCity,         String _carStockNo,
            String _carChassNo,      String _carStatus,
            String _carGrade,        String _firstRegisterMonth,
            String _carMileage,      String _carFuel,
            String _carColor,        String _carSeat,
            String _carBodyType,     String _carDriverType,
            String _carFobCost,      String _carFobCurrent,
            String _carCreateStatus, String _carCreateDate,
            String _carCCStr
    ) {
        this.car_id=_car_id;
        this.car_mak=_car_make;
        this.car_model=_car_model;
        this.car_year=_car_year;
        this.carStatYear=_carStatYear;
        this.carTransmission=_carTransmission;
        this.carCountry=_carCountry;
        this.carCity=_carCity;
        this.carStockNo=_carStockNo;
        this.carChassNo=_carChassNo;
        this.carStatus=_carStatus;
        this.carGrade=_carGrade;
        this.firstRegisterMonth=_firstRegisterMonth;
        this.carMileage=_carMileage;
        this.carFuel=_carFuel;
        this.carColor=_carColor;
        this.carSeat=_carSeat;
        this.carBodyType=_carBodyType;
        this.carDriverType=_carDriverType;
        this.carFobCost=_carFobCost;
        this.carFobCurrent=_carFobCurrent;
        this.carCreateStatus=_carCreateStatus;
        this.carCreateDate=_carCreateDate;
        this.carCCStr=_carCCStr;
    }

    public String getCarID(){return car_id;}
    public String getCarMake(){return car_mak;}
    public String getCarModel(){return car_model;}
    public String getCarYear(){return car_year;}
    public String getCarStatYear(){return carStatYear;}
    public String getCarTransmission(){return carTransmission;}

    public String getCarCountry(){return carCountry;}
    public String getCarCity(){return carCity;}
    public String getCarStockNo(){return carStockNo;}
    public String getCarChassNo(){return carChassNo;}
    public String getCarStatus(){return carStatus;}
    public String getCarGrade(){return carGrade;}

    public String getFirstRegisterMonth(){return firstRegisterMonth;}
    public String getCarMileage(){return carMileage;}
    public String getCarFuel(){return carFuel;}
    public String getCarColor(){return carColor;}
    public String getCarSeat(){return carSeat;}
    public String getCarBodyType(){return carBodyType;}

    public String getCarDriverType(){return carDriverType;}
    public String getCarFobCost(){return carFobCost;}
    public String getCarFobCurrent(){return carFobCurrent;}
    public String getCarCreateStatus(){return carCreateStatus;}
    public String getCarCreateDate(){return carCreateDate;}
    public String getCarCCStr(){return carCCStr;}





    //*****set Model*************
    public void setCarID(String carIDss){this.car_id=carIDss;}
    public void setCarMake(String carMaks){this.car_mak=carMaks;}
    public void setCarModel(String carmodel){this.car_model=carmodel;}
    public void setCarYear(String carYears){this.car_year=carYears;}
    public void setCarStartYear(String carStatYears){this.carStatYear=carStatYears;}
    public void setCarTransmission(String carTransmissionss){this.carTransmission=carTransmissionss;}


    public void setCarCountry(String carCountryzz){this.carCountry=carCountryzz;}
    public void setCarCity(String carCityssd){this.carCity=carCityssd;}
    public void setCarStockNo(String carStockNosd){this.carStockNo=carStockNosd;}
    public void setCarChassNo(String carChassNosd){this.carChassNo=carChassNosd;}
    public void setCarStatus(String carStatussd){this.carStatus=carStatussd;}
    public void setCarGrade(String carGrsdade){this.carGrade=carGrsdade;}

    public void setFirstRegisterMonth(String firstRegisterMonthsd){this.firstRegisterMonth=firstRegisterMonthsd;}
    public void setCarMileage(String carMileagesd){this.carMileage=carMileagesd;}
    public void setCarFuel(String carFuelsd){this.carFuel=carFuelsd;}
    public void setCarColor(String carColorsd){this.carColor=carColorsd;}
    public void setCarSeat(String carSeatsd){this.carSeat=carSeatsd;}
    public void setCarBodyType(String carBodyTypesd){this.carBodyType=carBodyTypesd;}

    public void setCarDriverType(String carDriverTypesd){this.carDriverType=carDriverTypesd;}
    public void setCarFobCost(String carFobCostsd){this.carFobCost=carFobCostsd;}
    public void setCarFobCurrent(String carFobCursdrent){this.carFobCurrent=carFobCursdrent;}
    public void setCarCreateStatus(String carCreateStatussd){this.carCreateStatus=carCreateStatussd;}
    public void setCarCreateDate(String carCreateDatesd){this.carCreateDate=carCreateDatesd;}
    public void setCarCCStr(String carCCStrSS){this.carCCStr=carCCStrSS;}


}
