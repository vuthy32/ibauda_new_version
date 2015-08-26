package all_action.iblaudas.JsonModel;

/**
 * Created by softbloom on 4/23/2015.
 */
public class ModelGallery {
    private String thumbImage,PhotoImage;
    public ModelGallery(){}
    public ModelGallery(
            String thumbImageData, String PhotoImageData){
            this.thumbImage=thumbImageData;
            this.PhotoImage=PhotoImageData;
                    }

    public String getThumbImage(){
        return this.thumbImage;
    }
    public void setThumbImage(String thumbImages){
        this.thumbImage = thumbImages;
    }


}
