package info.androidhive.materialdesign.JsonModel;

/**
 * Created by sunry on 8/12/2015.
 */
public class ModelHomeFragment {
    private String car_year;
    private String title;
    private String ImageUrl,PhotoUrl,SortPhoto;
    private String carNo;
    private String CarFob;
    private String IdexID;
    private String CityCar;
    private String StatusNew;
    private String StatusReserved;
    private String CarFobCurrency;
    public ModelHomeFragment(){}

    public ModelHomeFragment(
            String title,String car_year,String imageUrls,String PhotoUrls,String SortPhotos,
            String carNo,String carFobs,String IdexIDcar,
            String CityCara,String StatusNews,String StatusReserveds,String CurrencyCar
    ) {
        this.title = title;
        this.car_year = car_year;
        this.ImageUrl=imageUrls;
        this.PhotoUrl=PhotoUrls;
        this.SortPhoto=SortPhotos;
        this.carNo = carNo;
        this.CarFob =carFobs;
        this.IdexID = IdexIDcar;
        this.CityCar = CityCara;
        this.StatusNew = StatusNews;
        this.StatusReserved=StatusReserveds;
        this.CarFobCurrency=CurrencyCar;
    }
    // get getPhotoUrl***************************
    public String getSortPhoto(){return SortPhoto;}
    // get setPhotoUrl***************************
    public void setSortPhoto(String SortPhotoss){this.SortPhoto=SortPhotoss;}


    // get getPhotoUrl***************************
    public String getPhotoUrl(){return PhotoUrl;}
    // get setPhotoUrl***************************
    public void setPhotoUrl(String PhotoUrls){this.PhotoUrl=PhotoUrls;}

    // get getCarFobCurrency***************************
    public String getCarFobCurrency(){return CarFobCurrency;}
    // get setCarFobCurrency***************************
    public void setCarFobCurrency(String CurrencyCars){this.CarFobCurrency=CurrencyCars;}

    // get getStatusReserved***************************
    public String getStatusReserved(){return StatusReserved;}
    // get getStatusNew***************************
    public void setStatusReserved(String statusReserveds){this.StatusReserved=statusReserveds;}

    // get getStatusNew***************************
    public String getStatusNew(){return StatusNew;}
    // get setStatusNew***************************
    public void  setStatusNew(String statusNew){this.StatusNew=statusNew;}
    // get getCityCar***************************
    public String getCityCar(){return CityCar;}
    // get getCityCar***************************
    public void setCityCar(String cityCars){this.CityCar=cityCars;}

    // get getIdexID***************************
    public void setIdexID(String Index){this.IdexID=Index;}
    // get getIdexID***************************
    public String getIdexID(){return IdexID;}

    // get getCarFob***************************
    public String getCarFob(){return CarFob;}
    // get getCarFob***************************
    public void setCarFob(String carNoe){this.CarFob=carNoe;}

    // get car No***************************
    public String getCarNo(){return carNo;}
    //set Car No************************(
    public void setCarNo(String carNos){this.carNo = carNos;};

    //*****get ImageUrl************************
    public String getImageUrl(){return ImageUrl;}
    //*****set ImageUrl************************
    public void setImageUrl(String CarUrl){this.ImageUrl=CarUrl;}

    //*****get CarTitle************************
    public String getTitle() {
        return title;
    }
    //*****set Cartitile************************
    public void setTitle(String carTitle){this.title=carTitle;}

    //*****get CarYear*****************************
    public String getCarYear() {
        return car_year;
    }
    //*****set CarYear*****************************
    public void setCarYear(String CarYear){this.car_year=CarYear;}
}
