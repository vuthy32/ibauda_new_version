package info.androidhive.materialdesign.JsonModel;

/**
 * Created by sunry on 8/12/2015.
 */
public class ModelHomeFragment {
    private String car_year;
    private String title;
    private String ImageUrl;
    private String carNo;
    private String CarFob;
    private String IdexID;
    private String CityCar;
    private String StatusNew;
    private String StatusReserved;
    public ModelHomeFragment(
            String title,String car_year,String imageUrls,
            String carNo,String carFobs,String IdexIDcar,
            String CityCara,String StatusNews,String StatusReserveds
            ) {
        this.title = title;
        this.car_year = car_year;
        this.ImageUrl=imageUrls;
        this.carNo = carNo;
        this.CarFob =carFobs;
        this.IdexID = IdexIDcar;
        this.CityCar = CityCara;
        this.StatusNew = StatusNews;
        this.StatusReserved=StatusReserveds;
    }
    public String getStatusReserved(){return StatusReserved;}
    public String getStatusNew(){return StatusNew;}
    public String getCityCar(){return CityCar;}
    public String getIdexID(){return IdexID;}
    public String getCarFob(){return CarFob;}
    public String getCarNo(){return carNo;}
    public String getImageUrl(){return ImageUrl;}
    public String getTitle() {
        return title;
    }

    public String getCarYear() {
        return car_year;
    }
}
