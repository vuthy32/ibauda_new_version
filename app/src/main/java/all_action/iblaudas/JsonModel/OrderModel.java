package all_action.iblaudas.JsonModel;

/**
 * Created by sunry on 8/13/2015.
 */
public class OrderModel {
    private String thumbImage,inDexID,TitleCar,CarNoStr,CarPrice,CountCity;
    public OrderModel(){}
    public OrderModel(
            String thumbImageData, String inDexID,String TitleCars,String CarNoStrs,String CarPrices,String CountCitys){
        this.thumbImage=thumbImageData;
        this.inDexID=inDexID;
        this.TitleCar = TitleCars;
        this.CarNoStr = CarNoStrs;
        this.CarPrice = CarPrices;
        this.CountCity=CountCitys;
    }
    //******************CAR Car Price*****************************************
    public void setCountCity(String CountCityd){this.CountCity=CountCityd;}
    public String getCountCity(){return this.CountCity;}

    //******************CAR Car Price*****************************************
    public void setCarPrice(String CarNoStrsd){this.CarNoStr=CarNoStrsd;}
    public String getCarPrice(){return this.CarNoStr;}


    //******************CAR NO*****************************************
    public void setCarNoStr(String carPrices){this.CarPrice=carPrices;}
    public String getCarNoStr(){return this.CarPrice;}


    //******************CAR INDEX*****************************************
    public void setInDexID(String inDexID){this.inDexID=inDexID;}
    public String getInDexID(){return this.inDexID;}

    //******************CAR TITLE*****************************************
    public void setTitleCar(String titleCar){this.TitleCar=titleCar;}
    public String getTitleCar(){return this.TitleCar;}
    //******************CAR IMAGE*****************************************
    public String getThumbImage(){
        return this.thumbImage;
    }
    public void setThumbImage(String thumbImages){
        this.thumbImage = thumbImages;
    }



}
